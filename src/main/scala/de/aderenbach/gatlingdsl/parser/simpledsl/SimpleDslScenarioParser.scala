package de.aderenbach.gatlingdsl.parser.simpledsl

import java.io.File
import java.net.URI

import de.aderenbach.gatlingdsl.parser.{Scenario, ScenarioParser}

import scala.io.{Source, BufferedSource}

/**
 * Created by Alexander Derenbach <alexander.derenbach@gmail.com> on 12.10.15.
 */
object SimpleDslScenarioParser extends ScenarioParser {

  override def parse(sourceName: String): Scenario = {
    val source = SourceLoader.getSource(sourceName + ".scenario")

    val result = for (line <- source.getLines())
      yield {
        SimpleDsl.scenario(line)
      }

    val resultWithoutNull = result filter (e => e != null)

    val (scenarioProperties, actions) = resultWithoutNull partition (e => e._1 != "Action")
    val scenarioPropertyMap = scenarioProperties toMap

    new Scenario(scenarioPropertyMap("Name"), actions map (e => e._2) toList)

  }
}
