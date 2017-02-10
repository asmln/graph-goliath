import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by Anatoly Samoylenko on 10.02.2017.
  */
class GraphSpec extends FlatSpec with Matchers {

  val matrix: Array[Array[Boolean]] = Array(
    Array(false, true, false, false),
    Array(false, false, true, false),
    Array(false, false, false, false),
    Array(false, false, true, false)
  )
  val graph = Graph(matrix)

  "Path 0 to 1" should "be exist" in {
    graph.checkRoute(0, 1)
  }

  "Path 0 to 2" should "be exist" in {
    graph.checkRoute(0, 2)
  }

  "Path 3 to 2" should "be exist" in {
    graph.checkRoute(3, 2)
  }

  "Path 0 to 0" should "be NOT exist" in {
    graph.checkRoute(0, 0)
  }

  "Path 0 to 3" should "be NOT exist" in {
    !graph.checkRoute(0, 3)
  }

  "Path 1 to 0" should "be NOT exist" in {
    !graph.checkRoute(1, 0)
  }

}

