package de.aderenbach.gatlingdsl

import de.aderenbach.gatlingdsl.parser.DslParser
import de.aderenbach.gatlingdsl.parser.simpledsl.SimpleDslSimlationBuilder

import scala.io.Source

/**
 * Created by Alexander Derenbach <alexander.derenbach@gmail.com> on 09.10.15.
 */
object LoadTestRunner {

  def main(args: scala.Array[scala.Predef.String]): scala.Unit = {

    val dslSource = Source.fromFile("")

    //val parser = new DslParser(dslSource)

    GenericSimulationConfig.builder = SimpleDslSimlationBuilder(null)
    Engine.runSimulation("desc", "de.mnet.portal.gatling.LoadSimulation")
  }

}
