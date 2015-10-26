package de.aderenbach.gatlingdsl

import de.aderenbach.gatlingdsl.parser.DslParser
import de.aderenbach.gatlingdsl.parser.simpledsl.{SourceLoader, SimpleDslParser, SimpleDslSimlationBuilder}

import scala.io.Source

/**
 * Created by Alexander Derenbach <alexander.derenbach@gmail.com> on 09.10.15.
 */
object LoadTestRunner {

  def main(args: scala.Array[scala.Predef.String]): scala.Unit = {

    // TODO not so nice ;)
    SourceLoader.sourceLocation("/Users/kinggrass/Sources/gatling_dsl/gatling_dsl_driver/src/main/resources/simulations")
    val parser = new SimpleDslParser("testSim")

    GenericSimulationConfig.builder = SimpleDslSimlationBuilder(parser)
    Engine.runSimulation("desc", "de.aderenbach.gatlingdsl.GenericSimulation")
  }

}
