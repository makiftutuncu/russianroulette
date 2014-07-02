import akka.actor._

/**
 * A simple Russian roulette simulation using Akka actors
 */
object RussianRoulette extends App {
  // Create an actor system
  val system = ActorSystem("russianroulettesystem")
  // Create a roulette actor
  val roulette = system.actorOf(Props[RussianRouletteActor], name = "russianrouletteactor")
  // Initialize
  roulette ! InitRussianRoulette
}
