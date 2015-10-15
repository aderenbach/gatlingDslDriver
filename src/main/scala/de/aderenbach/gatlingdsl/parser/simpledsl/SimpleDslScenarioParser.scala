package de.aderenbach.gatlingdsl.parser.simpledsl

import java.io.File
import java.net.URI

import de.aderenbach.gatlingdsl.parser.{Scenario, ScenarioParser}

import scala.io.{Source, BufferedSource}

/**
 * Created by Alexander Derenbach <alexander.derenbach@gmail.com> on 12.10.15.
 */
object SimpleDslScenarioParser extends ScenarioParser {

  implicit class Regex(sc: StringContext) {
    def r = new util.matching.Regex(sc.parts.mkString, sc.parts.tail.map(_ => "x"): _*)
  }

  override def parse(sourceLocationURI: String, sourceName: String): Scenario = {
    val source = Source.fromFile(new File(sourceLocationURI +"/" + sourceName + ".scenario"))
    val (scenarioProperties,actions) = (for (line <- source.getLines()) yield {
      line match {
        case r"\s*Scenario: (.*)$name" => "Name" -> name
        case r"\s*" => null
        case _ => "Action" -> line.trim
      }
    }) filter (e => e != null) partition(e => e._1 != "Actions")

    val scenarioPropertyMap =  scenarioProperties toMap

    new Scenario(scenarioPropertyMap("Name"),actions map (e=> e._2) toList)

  }
}
