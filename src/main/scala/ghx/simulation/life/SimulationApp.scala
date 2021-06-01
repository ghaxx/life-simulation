package ghx.simulation.life

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.StackPane
import javafx.stage.Stage

import akka.actor.ActorSystem
import ghx.simulation.life.UIActor.Draw
import ghx.simulation.life.World.Start
import ghx.simulation.life.ui.SimulationCanvas

class SimulationApp extends Application {

  def start(primaryStage: Stage): Unit = {
    primaryStage.setTitle("Sup!")
    val root = new StackPane
    root.getChildren.add(canvas)
    primaryStage.setScene(new Scene(root))
    primaryStage.show()

    val system = ActorSystem("simulation")
    system.actorOf(UIActor.props(canvas), "canvas")
    val worldActor = system.actorOf(World.props, "world")
    worldActor ! Start
    primaryStage.setOnCloseRequest {e =>
      system.terminate()
    }

  }

  private lazy val canvas = new SimulationCanvas

}

object SimulationApp extends App {
  Application.launch(classOf[SimulationApp], args: _*)
}
