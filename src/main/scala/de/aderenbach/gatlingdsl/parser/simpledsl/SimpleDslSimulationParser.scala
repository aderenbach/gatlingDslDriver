package de.aderenbach.gatlingdsl.parser.simpledsl

import java.io.File
import java.net.URI

import de.aderenbach.gatlingdsl.parser._

import scala.io.{Source, BufferedSource}

/**
 * Created by Alexander Derenbach <alexander.derenbach@gmail.com> on 12.10.15.
 */
object SimpleDslSimulationParser extends SimulationParser {

  //TODO extract somewhere
  implicit class Regex(sc: StringContext) {
    def r = new util.matching.Regex(sc.parts.mkString, sc.parts.tail.map(_ => "x"): _*)
  }

  override def parse( sourceName: String,sourceLocationURI: String): Simulation = {
    val source = Source.fromFile(new File(sourceLocationURI +"/" + sourceName + ".simulation"))
    val (simConfig, scenarios) = (for (line <- source.getLines()) yield {
      line match {
        case r"\s*Simulation: (.*)$name" => "Name" -> name
        case r"\s*BaseURL: (.*)$url" => "BaseURL" -> url
        case r"\s*Scenarios:.*" => null
        case r"\s*" => null
        case _ => "Scenario" -> line.trim
      }
    }) filter (e => e != null) partition (e => e._1 != "Scenario")

    val simConfigMap = simConfig toMap

    val scnList = for ((id, name) <- scenarios) yield name

    val scnTuples = for (scn <- scnList) yield {
      scn match {
        case r"(\w*)$name with (\d+)$userCount users (.*)$rampUp" => new ScenarioConfig(loadScenario(sourceLocationURI,name), userCount.toInt, new RampUp(rampUp))
        case _ => throw new RuntimeException("Can't parse scenario config")
      }
    }

    new Simulation(simConfigMap("Name"), simConfigMap("BaseURL"), scnTuples.toList)

  }

  private def loadScenario(sourceLocationURI: String,name: String): Scenario =  {
    SimpleDslScenarioParser.parse(sourceLocationURI,name)

  }

}
