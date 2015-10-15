package de.aderenbach.gatlingdsl.parser

import java.net.URI

import scala.io.BufferedSource

/**
 * Created by Alexander Derenbach <alexander.derenbach@gmail.com> on 12.10.15.
 */
trait Parser[T] {
  def parse(sourceLocationURI: String,sourceName: String):T
}

trait SimulationParser extends Parser[Simulation]
trait ScenarioParser extends Parser [Scenario]