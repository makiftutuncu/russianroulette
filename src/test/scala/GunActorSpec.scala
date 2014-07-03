import akka.actor.{Props, ActorSystem}
import akka.testkit._
import org.scalatest._
import scala.concurrent.duration._
import scala.util.Random

class GunActorSpec(_system: ActorSystem) extends TestKit(_system) with ImplicitSender with Matchers with FlatSpecLike with BeforeAndAfterAll {
  def this() = this(ActorSystem("GunActorSpec"))

  override def afterAll: Unit = {
    system.shutdown()
    system.awaitTermination(10.seconds)
  }

  "A GunActor" should "be able to click if bullet not found" in {
    val gunActor = TestActorRef(Props[GunActor], "safegun")
    val bulletIndex: Int = gunActor.underlyingActor.asInstanceOf[GunActor].bulletIndex
    val range = 1 to 6
    val safeIndices: List[Int] = range.take(bulletIndex).toList ::: range.drop(bulletIndex + 1).toList
    val safeIndex: Int = safeIndices(Random.nextInt(safeIndices.length))

    gunActor ! Click(safeIndex)
  }

  it should "bang and you kill you" in {
    val gunActor = TestActorRef(Props[GunActor], "dangerousgun")
    val bulletIndex: Int = gunActor.underlyingActor.asInstanceOf[GunActor].bulletIndex

    gunActor ! Click(bulletIndex + 1)
  }
}
