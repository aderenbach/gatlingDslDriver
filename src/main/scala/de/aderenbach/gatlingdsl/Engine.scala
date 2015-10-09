package de.aderenbach.gatlingdsl


import io.gatling.app.Gatling
import io.gatling.core.config.GatlingPropertiesBuilder


/**
 * Created by Alexander Derenbach <alexander.derenbach@gmail.com> on 09.10.15.
 */

object Engine extends App {

  def runSimulation(description: String, simulationClass: String): Unit = {

    val props = new GatlingPropertiesBuilder

    props.dataDirectory(IDEPathHelper.dataDirectory.toString)
    props.resultsDirectory(IDEPathHelper.resultsDirectory.toString)
    props.bodiesDirectory(IDEPathHelper.bodiesDirectory.toString)
    props.binariesDirectory(IDEPathHelper.mavenBinariesDirectory.toString)

    props.runDescription(description)
    props.simulationClass(simulationClass)

    Gatling.fromMap(props.build)
  }
}

