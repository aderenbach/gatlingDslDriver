package de.aderenbach.gatlingdsl

import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
 * Created by Alexander Derenbach <alexander.derenbach@gmail.com> on 09.10.15.
 */
object Http {

  def get(route: String) =  exec(http(route).get(route))

}
