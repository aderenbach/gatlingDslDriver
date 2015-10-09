package de.aderenbach.gatlingdsl.parser

import scala.io.BufferedSource

/**
 * Created by Alexander Derenbach <alexander.derenbach@gmail.com> on 09.10.15.
 */
class DslParser(dslSource: BufferedSource) {

      for (line <- dslSource.getLines()) yield {
        line match {
          case "Simulation: " :: simulationName => "sim"
          case "BaseUrl: " :: baseUrl => "base"
        }
      }


}
