import akka.actor.{ActorLogging, Actor}

import scala.util.Random

// Click message while pulling the trigger
case class Click(times: Int)

/**
 * Gun actor that represents the gun
 */
class GunActor extends Actor with ActorLogging {
  // Index of the single bullet in the gun
  val bulletIndex: Int = Random.nextInt(6)

  override def receive = {
    case Click(times: Int) =>
      if(times - 1 == bulletIndex)
        // Well, found the bullet, throw exception and die
        throw new DiedException
      else
        // Missed, still alive
        log.info(s"Click! Missed $times times.")
  }

  override def postRestart(reason: Throwable): Unit = {
    // Reset the game before restarting
    log.info("Not so fast! I live AGAIN!")
    context.parent ! ResetRussianRoulette
  }
}

