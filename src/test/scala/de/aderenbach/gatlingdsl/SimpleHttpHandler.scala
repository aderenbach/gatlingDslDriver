package de.aderenbach.gatlingdsl

import akka.actor.Actor
import spray.can.Http
import spray.http.{HttpResponse, HttpRequest}

/**
 * Created by Alexander Derenbach <alexander.derenbach@gmail.com> on 20.10.15.
 */
class SimpleHttpHandler extends Actor {
  def receive = {
    //register myself as a connection handler for the new connection
    case _: Http.Connected => sender ! Http.Register(self)
    //respond any http request with "Hello, world!"
    case request: HttpRequest => sender ! {
      println (request)
      HttpResponse(entity="Hello, World!")
    }
  }
}
