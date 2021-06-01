package ghx.simulation.life

import akka.actor.{Actor, Props}
import ghx.simulation.life.UIActor.Draw
import ghx.simulation.life.Wanderer.WandererPosition

import scala.collection.mutable


class World extends Actor {

  import World._

  val list = mutable.Queue.empty[(Int, Int)]

  def receive: Receive = {
    case Start =>
      context.system.actorSelection("user/canvas") ! Draw(list.map(x => WandererPosition(x._1, x._2)).toList)

    case CreateWanderer(x, y) =>
      if (list.lengthCompare(380*580) > 0) list.dequeue()
      list.enqueue((x, y))
      context.system.actorOf(Wanderer.props(x, y, this))
      context.system.actorSelection("user/canvas") ! Draw(list.map(x => WandererPosition(x._1, x._2)).toList)
  }
}

object World {
  def props = Props[World]

  //  ClusterSingletonManager.props(
  //    singletonProps = Props(classOf[World]),
  //    terminationMessage = End,
  //    settings = ClusterSingletonManagerSettings(system).withRole("worker"))

  case object Start

  case class CreateWanderer(x: Int, y: Int)

}