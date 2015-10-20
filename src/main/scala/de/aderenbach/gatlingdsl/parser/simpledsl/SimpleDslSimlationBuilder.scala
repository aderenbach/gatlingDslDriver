package de.aderenbach.gatlingdsl.parser.simpledsl

import de.aderenbach.gatlingdsl.parser.{Scenario, DslParser}
import io.gatling.core.Predef._
import io.gatling.core.structure.{ScenarioBuilder, PopulationBuilder}
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder


/**
 * Created by Alexander Derenbach <alexander.derenbach@gmail.com> on 09.10.15.
 */
case class SimpleDslSimlationBuilder(dslparser: DslParser) {

  val simulation = dslparser.parse
  lazy val name = simulation.name
  lazy val baseUrl = simulation.baseUrl

  implicit class Regex(sc: StringContext) {
    def r = new util.matching.Regex(sc.parts.mkString, sc.parts.tail.map(_ => "x"): _*)
  }

  def addHttpMethod(method: String, path: String): HttpRequestBuilder = {
    method match {
      case "GET" => http(path).get(path)
      case "DELETE" => http(path).delete(path)
      case "PUT" => http(path).put(path)
      case "POST" => http(path).post(path)
      case _ => throw new Error("method unkown " + method)
    }
  }

  def addAction(scnBuilder: ScenarioBuilder, action: String): ScenarioBuilder = {
    action match {
      case r"\s*Call (.*)$method\s(.*)$path" => scnBuilder.exec(addHttpMethod(method,path))
      case _ => scnBuilder
    }
  }

  def addActions(scnBuilder: ScenarioBuilder, actions: List[String]): ScenarioBuilder = {
    actions match {
      case action :: rest => addActions(addAction(scnBuilder, action), rest)
      case Nil => scnBuilder
    }
  }

  def populationBuilder: List[PopulationBuilder] = {
    for (
      scnConfig <- simulation.scenarioConfigList
    ) yield {
      val scenarioBuilder = scenario(scnConfig.scenario.name)
      addActions(scenarioBuilder, scnConfig.scenario.actions).inject(atOnceUsers(2))
    }
  }
}