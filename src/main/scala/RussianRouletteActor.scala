import akka.actor.SupervisorStrategy._
import akka.actor._

import scala.concurrent.duration._

// Message objects
case object InitRussianRoulette
case object ResetRussianRoulette
case object PullTrigger

/**
 * Exception that is thrown when gun fires
 */
class DiedException extends Exception("You died!")

/**
 * Actor that manages the simulation
 */
class RussianRouletteActor extends Actor with ActorLogging {
  // Number of trials
  var numberOfTrials: Int = 0
  // Gun actor that represents the gun
  val gunActor: ActorRef = context.actorOf(Props[GunActor], name = "gun")

  override def receive = {
    case InitRussianRoulette =>
      // Initialize the game, schedule pulling the trigger once a second
      import scala.concurrent.ExecutionContext.Implicits.global
      context.system.scheduler.schedule(1.seconds, 1.seconds, self, PullTrigger)

    case ResetRussianRoulette =>
      // Reset number of trials
      numberOfTrials = 0

    case PullTrigger =>
      // Increase trial number and pull the trigger
      numberOfTrials += 1
      gunActor ! Click(numberOfTrials)
  }

  override val supervisorStrategy = OneForOneStrategy() {
    case _: DiedException =>
      // If gun actor fails with DiedException restart the gun actor
      log.info("Bang!")
      Restart
  }
}
