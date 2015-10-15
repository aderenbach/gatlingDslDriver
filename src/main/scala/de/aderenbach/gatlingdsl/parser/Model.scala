package de.aderenbach.gatlingdsl.parser

/**
 * Created by Alexander Derenbach <alexander.derenbach@gmail.com> on 12.10.15.
 */
class Simulation(val name: String,val baseUrl: String,val scenarioConfigList:List[ScenarioConfig]) {
  override def toString = name + ", Scenarios: [" + (scenarioConfigList mkString(",")) + "]"

}

class RampUp(rampUp:String)

class ScenarioConfig(val scenario: Scenario, val userCount: Int, val rampUp: RampUp) {
  override def toString = scenario.toString
}

class Scenario(val name: String, val actions: List[String]) {
  override def toString = name
}