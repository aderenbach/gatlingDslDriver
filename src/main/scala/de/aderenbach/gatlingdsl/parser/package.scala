package de.aderenbach.gatlingdsl

/**
 * Created by Alexander Derenbach <alexander.derenbach@gmail.com> on 21.10.15.
 */
package object parser {
  trait Parser[T] {
    def parse(sourceName: String):T
  }

  trait SimulationParser extends Parser[Simulation]
  trait ScenarioParser extends Parser [Scenario]

}
