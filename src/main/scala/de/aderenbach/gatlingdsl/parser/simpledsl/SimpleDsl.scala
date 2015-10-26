package de.aderenbach.gatlingdsl.parser.simpledsl

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder

/**
 *
 * holds the simpleDsl definition.
 *
 * Created by Alexander Derenbach <alexander.derenbach@gmail.com> on 22.10.15.
 */
object SimpleDsl extends scala.AnyRef {

  // TODO how is it done with the http method???
  def simulation(line: String): (String, String) = {
    line match {
      case r"\s*#.*" => null // comments
      case r"\s*Simulation: (.*)$name" => "Name" -> name
      case r"\s*BaseURL: (.*)$url" => "BaseURL" -> url
      case r"\s*Scenarios:.*" => null
      case r"\s*" => null // empty lines
      case _ => "Scenario" -> line.trim
    }
  }

  def scenario(line: String): (String, String) = {
    line match {
      case r"\s*Scenario: (.*)$name" => "Name" -> name
      case r"\s*" => null // empty lines
      case r"\s*#.*" => null // comments
      case _ => "Action" -> line.trim // TODO replace all multiple spaces with one
    }
  }

  def action(scnBuilder: ScenarioBuilder, action: String): ScenarioBuilder = {
    action match {
      case r"\s*Call.*" => call(scnBuilder, action)
      case r"\s*Pause.*" => pause(scnBuilder, action)
      case _ => throw new Error("unkonwn action " + action)
    }
  }


  def call(scnBuilder: ScenarioBuilder, action: String): ScenarioBuilder = {
    action match {
      case r"\s*Call (.*?)$method\s(.*?)$path\s(.*?)$body" => scnBuilder.exec(addHttpMethod(method, path, body))
      case r"\s*Call (.*?)$method\s(.*?)$path" => scnBuilder.exec(addHttpMethod(method, path, null))
      case _ => throw new Error("unknown call: '" + action + "'")
    }
  }

  def addHttpMethodFormData(builder: HttpRequestBuilder, formdataString: String): HttpRequestBuilder = {
    val keys = (for (keyValuePair <- formdataString.trim.split(",")) yield extractToPair(keyValuePair)) toMap;
    addHttpMethodFormData(builder, keys)
  }

  def addHttpMethodFormData(builder: HttpRequestBuilder, formData: Map[String, String]): HttpRequestBuilder = {
    builder.formParamMap(formData);
  }

  def extractToPair(keyValuePair: String): (String, String) = {
    val tmp = keyValuePair.trim.split("=")
    tmp match {
      case Array(k, v) => (k, v)
    }
  }

  def loadFromSource(formdataSourceName: String): Map[String,String] = {
    val formDataSource = SourceLoader.getSource(formdataSourceName + ".formdata")
    (for (line <- formDataSource.getLines()) yield extractToPair(line)) toMap
  }

  def addBodyOrFormData(builder: HttpRequestBuilder, body: String): HttpRequestBuilder = {
    body.trim match {
      case r"Form:\s*?(.*?)$formdata" => addHttpMethodFormData(builder, formdata)
      case r"Formdata:\s*?(.*?)$formdataSourceName" => addHttpMethodFormData(builder, loadFromSource(formdataSourceName))
      case _body => builder.body(StringBody(_body))
    }
  }

  def addHttpMethod(method: String, path: String, body: String): HttpRequestBuilder = {
    method match {
      case "GET" => http(path).get(path)
      case "DELETE" => http(path).delete(path)
      case "PUT" => addBodyOrFormData(http(path).put(path), body)
      case "POST" => addBodyOrFormData(http(path).post(path), body)
      case _ => throw new Error("method unkown: " + method)
    }
  }

  private def pause(scnBuilder: ScenarioBuilder, action: String): ScenarioBuilder = {
    action match {
      case r"\s*Pause (\d*)$duration" => scnBuilder.pause(Integer.valueOf(duration))
      case r"\s*Pause (\d*)$duration second[s]?" => scnBuilder.pause(Integer.valueOf(duration) seconds)
      case r"\s*Pause (\d*)$durationMin second[s]? - (\d*)$durationMax second[s]?" => scnBuilder.pause(Integer.valueOf(durationMin) seconds, Integer.valueOf(durationMax) seconds)
      case r"\s*Pause (\d*)$durationMin minute[s]? - (\d*)$durationMax minute[s]?" => scnBuilder.pause(Integer.valueOf(durationMin) minutes, Integer.valueOf(durationMax) minutes)
      case r"\s*Pause (\d*)$duration minute[s]?" => scnBuilder.pause(Integer.valueOf(duration) minutes)
      case r"\s*Pause (\d*)$durationMin - (\d*)$durationMax" => scnBuilder.pause(Integer.valueOf(durationMin), Integer.valueOf(durationMax))
      case _ => throw new Error("unkonwn action " + action)
    }
  }

  // TODO parsing should be front up and should be independent from the gatling DSL it results in
  def getTime(time: String, unit: String) = {
    val timeInt = Integer.valueOf(time)
    unit match {
      case "seconds" => timeInt seconds
      case "minutes" => timeInt minutes
      case "hours" => timeInt hours
      case _ => throw new Error("unimplemented unit: " + unit)
    }
  }

  def rampUp(userCount: Integer, rampUp: String) = {
    rampUp.trim match {
      case r"^at once" => atOnceUsers(userCount)
      case r"^over\s(\d*)$time\s(.*)$unit" => rampUsers(userCount) over (getTime(time, unit))
      case _ => throw new Error("unknown rampup: '" + rampUp + "'"
      )
    }
  }
}