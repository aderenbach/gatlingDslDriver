package de.aderenbach.gatlingdsl

import de.aderenbach.gatlingdsl.parser.DslParser
import de.aderenbach.gatlingdsl.parser.simpledsl.{SimpleDslParser, SimpleDslSimlationBuilder}

import scala.io.Source

/**
 * Created by Alexander Derenbach <alexander.derenbach@gmail.com> on 09.10.15.
 */
object LoadTestRunner {

  def main(args: scala.Array[scala.Predef.String]): scala.Unit = {

    val parser = new SimpleDslParser("/Users/kinggrass/Sources/gatling_dsl/gatling_dsl_driver/src/main/resources/simulations","testSim")

    GenericSimulationConfig.builder = SimpleDslSimlationBuilder(parser)
    Engine.runSimulation("desc", "de.aderenbach.gatlingdsl.GenericSimulation")
  }

}
