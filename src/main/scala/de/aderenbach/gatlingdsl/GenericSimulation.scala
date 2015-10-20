package de.aderenbach.gatlingdsl


import io.gatling.core.Predef._
import io.gatling.http.Predef._


/**
 * Created by Alexander Derenbach <alexander.derenbach@gmail.com> on 09.10.15.
 */
class GenericSimulation extends Simulation {

  val simulationBuilder = GenericSimulationConfig.builder

  val httpConf = http
    .baseURL(simulationBuilder.baseUrl)
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")

  setUp(
    simulationBuilder.populationBuilder
  ).protocols(httpConf)

}
