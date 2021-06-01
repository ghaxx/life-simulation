package ghx.simulation.life

import akka.actor.{Props, Actor}
import akka.actor.Actor.Receive

class Wanderer(x: Int, y: Int, world: Actor) extends Actor {
  def receive: Receive = {
    case _ =>
  }
}

object Wanderer {
  def props(x: Int, y: Int, world: Actor) = Props(classOf[Wanderer], x, y, world)

  case class WandererPosition(x: Int, y: Int)
}