package controllers

import play.api._
import play.api.mvc._

import scalarduino.actors.{PowerOff, PowerOn, ArduinoActor}

class Application extends Controller {

  def index = Action {
    Ok(views.html.index())
  }

  def powerOn = Action {
    ArduinoActor.actor ! PowerOn
    Ok
  }

  def powerOff = Action {
    ArduinoActor.actor ! PowerOff
    Ok
  }

}
