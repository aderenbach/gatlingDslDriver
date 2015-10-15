package de.aderenbach.gatlingdsl


import de.aderenbach.gatlingdsl.parser.Simulation
import de.aderenbach.gatlingdsl.parser.simpledsl.SimpleDslSimlationBuilder
import io.gatling.core.structure.{PopulationBuilder, ScenarioBuilder}


/**
 *
 * Transfer object to inject acutal config in generic created simulations
 * Created by Alexander Derenbach <alexander.derenbach@gmail.com> on 09.10.15.
 */
object GenericSimulationConfig {

  var builder: SimpleDslSimlationBuilder = null

}
