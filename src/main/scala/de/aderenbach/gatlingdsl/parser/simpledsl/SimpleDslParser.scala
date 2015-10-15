package de.aderenbach.gatlingdsl.parser.simpledsl

import java.net.URI

import de.aderenbach.gatlingdsl.parser.{Simulation, DslParser}

import scala.io.Source

/**
 * Created by Alexander Derenbach <alexander.derenbach@gmail.com> on 12.10.15.
 */
class SimpleDslParser(simulationName: String, simLocation: String ) extends DslParser {
  override def parse: Simulation = {
    SimpleDslSimulationParser.parse(simLocation,simulationName)
  }
}
