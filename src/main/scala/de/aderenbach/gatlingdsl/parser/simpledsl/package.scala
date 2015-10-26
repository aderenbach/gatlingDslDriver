package de.aderenbach.gatlingdsl.parser


/**
 * Created by Alexander Derenbach <alexander.derenbach@gmail.com> on 21.10.15.
 */
package object simpledsl {

  implicit class Regex(sc: StringContext) {
    def r = new util.matching.Regex(sc.parts.mkString, sc.parts.tail.map(_ => "x"): _*)
  }

}
