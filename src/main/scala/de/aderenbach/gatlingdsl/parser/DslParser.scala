package de.aderenbach.gatlingdsl.parser

import java.net.URI

import scala.io.BufferedSource

/**
 * Created by Alexander Derenbach <alexander.derenbach@gmail.com> on 09.10.15.
 */
trait DslParser {

    def parse:Simulation

}
