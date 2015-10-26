package de.aderenbach.gatlingdsl

import akka.actor.{Props, ActorSystem}
import akka.io.IO
import spray.can.Http

/**
 * Created by Alexander Derenbach <alexander.derenbach@gmail.com> on 20.10.15.
 */
object TestServerApp extends App {
    // create a new Actor system
    implicit val system = ActorSystem()
    // create a new instance of the handler actor
    val handler = system.actorOf(Props[SimpleHttpHandler], name =
      "handler")
    // start the spray-can server, bind to port 8080, register actor
    //as handler
    IO(Http) ! Http.Bind(handler, interface = "localhost", port = 8080)

}
