package scalarduino.actors

import akka.actor.{Actor, Props}
import play.api.Play
import play.api.Play.current
import play.api.libs.concurrent.Akka

import scalarduino.models.COMPort


/**
 * Created by caio on 12/11/15.
 */

object ArduinoActor {
  val actor = Akka.system.actorOf(Props[ArduinoActor])
}

case object PowerOn
case object PowerOff

class ArduinoActor extends Actor {

  def receive = {
    case PowerOn => sendToArduino(1)
    case PowerOff => sendToArduino(0)
  }


  private def sendToArduino(data: Int): Unit = {
    val port = Play.application.configuration.getString("arduino.serial.port").get

    val comm = COMPort(port)
    comm.initialize
    comm.sendData(data)
    comm.close
  }

}
