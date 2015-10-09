package de.aderenbach.gatlingdsl


import io.gatling.core.structure.ScenarioBuilder


/**
 * Created by Alexander Derenbach <alexander.derenbach@gmail.com> on 09.10.15.
 */
object LoadScenarioConfig {

  var builder: LoadScenarioBuilder = null

  lazy val config = buildScenario()

  def buildScenario(): List[(ScenarioBuilder, Int)] = {
    builder buildScenarios
  }

}
