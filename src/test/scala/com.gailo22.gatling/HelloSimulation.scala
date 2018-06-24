package com.gailo22.gatling

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class HelloSimulation extends Simulation {

  val httpProtocol = http
    .baseURL("http://localhost:8080")
    .acceptHeader("text/plain")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Gatling")

  val scn = scenario("HelloSimulation")
    .repeat(10) {
      exec(http("GET /api/hello-async").get("/api/hello-async"))
    }

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpProtocol)
}