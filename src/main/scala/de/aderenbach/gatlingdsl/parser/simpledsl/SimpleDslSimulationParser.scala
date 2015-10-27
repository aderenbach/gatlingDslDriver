package de.aderenbach.gatlingdsl.parser.simpledsl

import de.aderenbach.gatlingdsl.parser._
import de.aderenbach.gatlingdsl.parser.simpledsl.SimpleDsl._

/**
 * Created by Alexander Derenbach <alexander.derenbach@gmail.com> on 12.10.15.
 */
object SimpleDslSimulationParser extends SimulationParser {

  override def parse(sourceName: String): Simulation = {
    val source = SourceLoader.getSource(sourceName + ".simulation")
    val (simConfig, scenarios) = (for (line <- source.getLines()) yield simulation(line)) filter (e => e != null) partition (e => e._1 != "Scenario")

    val simConfigMap = simConfig toMap
    val scnList = for ((id, name) <- scenarios) yield name
    val scnTuples = for (scn <- scnList) yield {
      scn match {
        case r"(\w*)$name with (\d+)$userCount users (.*)$rampUp" => new ScenarioConfig(loadScenario(name), userCount.toInt, new RampUp(rampUp))
        case _ => throw new RuntimeException("Can't parse scenario config")
      }
    }
    new Simulation(simConfigMap("Name"), simConfigMap("BaseURL"), scnTuples.toList)
  }

  private def loadScenario(name: String): Scenario = {
    SimpleDslScenarioParser.parse(name)

  }

}
