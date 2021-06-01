package ghx.simulation.life

import akka.actor.{Actor, Props}
import akka.actor.Actor.Receive
import ghx.simulation.life.Wanderer.WandererPosition
import ghx.simulation.life.World.CreateWanderer
import ghx.simulation.life.ui.SimulationCanvas
import javafx.animation.AnimationTimer

import scala.util.Random
import scalafx.scene.paint.Color

class UIActor(window: SimulationCanvas) extends Actor {
  import UIActor._

  var positions: scala.List[Wanderer.WandererPosition] = List.empty

  val timer = new AnimationTimer() {
    def handle(l: Long): Unit = {

      window.graphicsContext2D.clearRect(0, 0, 600, 400)
      window.graphicsContext2D.stroke = Color(0, 0, 0, 1)
      window.graphicsContext2D.lineWidth = 2
      window.graphicsContext2D.strokeRect(5, 5, 600-5, 400-5)

      window.graphicsContext2D.lineWidth = 1

      for (position <- positions) {
//        window.graphicsContext2D.strokeOval(position.x, position.y, 3, 3)
        window.graphicsContext2D.getPixelWriter.setColor(position.x, position.y, Color(0, 0, 0, 1))
      }
    }
  }

  timer.start()

  def receive: Receive = {
    case Draw(positions) =>
      this.positions = positions
      sender ! CreateWanderer(Random.nextInt(580) + 9, Random.nextInt(380) + 9)
  }
}

object UIActor {
  def props(window: SimulationCanvas) = Props(classOf[UIActor], window)

  case class Draw(wandererPositions: List[WandererPosition])
}