import akka.actor.{Props, ActorSystem}
import akka.testkit._
import org.scalatest._
import scala.concurrent.duration._

class RussianRouletteActorSpec(_system: ActorSystem) extends TestKit(_system) with ImplicitSender with Matchers with FlatSpecLike with BeforeAndAfterAll {
  def this() = this(ActorSystem("RussianRouletteActorSpec"))

  override def afterAll: Unit = {
    system.shutdown()
    system.awaitTermination(10.seconds)
  }

  "A RussianRouletteActor" should "initialize the game" in {
    val russianRouletteActor = TestActorRef(Props[RussianRouletteActor])

    russianRouletteActor ! InitRussianRoulette
  }

  it should "reset the game" in {
    val russianRouletteActor = TestActorRef(Props[RussianRouletteActor])

    russianRouletteActor ! ResetRussianRoulette

    russianRouletteActor.underlyingActor.asInstanceOf[RussianRouletteActor].numberOfTrials shouldBe 0
  }

  it should "pull the trigger and increase trial count" in {
    val russianRouletteActor = TestActorRef(Props[RussianRouletteActor])

    val before = russianRouletteActor.underlyingActor.asInstanceOf[RussianRouletteActor].numberOfTrials
    russianRouletteActor ! PullTrigger
    val after = russianRouletteActor.underlyingActor.asInstanceOf[RussianRouletteActor].numberOfTrials

    before shouldEqual(after - 1)
  }
}
