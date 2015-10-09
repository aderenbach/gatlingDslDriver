package de.aderenbach.gatlingdsl

import de.aderenbach.gatlingdsl.parser.DslParser
import io.gatling.core.Predef._

/**
 * Created by Alexander Derenbach <alexander.derenbach@gmail.com> on 09.10.15.
 */
case class LoadScenarioBuilder(dslparser: DslParser) {

  def buildScenarios() = {
    (scenario("test").exec(Http.get("/")), 4) :: Nil
  }


}