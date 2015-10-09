package de.aderenbach.gatlingdsl


import io.gatling.core.Predef._
import io.gatling.http.Predef._


/**
 * Created by Alexander Derenbach <alexander.derenbach@gmail.com> on 09.10.15.
 */
class LoadSimulation extends Simulation {


  val httpConf = http
    .baseURL("http://localhost")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")

  val scenarios = LoadScenarioConfig.config

  setUp(
    scenarios map (f => f._1.inject(atOnceUsers(f._2)))
  ).protocols(httpConf)

}
