import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by Anatoly Samoylenko on 10.02.2017.
  */
class Graph1Spec extends FlatSpec with Matchers {

  val matrix: Array[Array[Boolean]] = Array(
    Array(false, true, false, false),
    Array(false, false, true, false),
    Array(false, false, false, false),
    Array(false, false, true, false)
  )
  val graph = GraphHDD(matrix)

  "Path 0 to 1" should "be exist" in {
    graph.checkRoute(0, 1) should be(true)
  }

  "Path 0 to 2" should "be exist" in {
    graph.checkRoute(0, 2) should be(true)
  }

  "Path 3 to 2" should "be exist" in {
    graph.checkRoute(3, 2) should be(true)
  }

  "Path 0 to 0" should "be NOT exist" in {
    graph.checkRoute(0, 0) should be(false)
  }

  "Path 0 to 3" should "be NOT exist" in {
    graph.checkRoute(0, 3) should be(false)
  }

  "Path 1 to 0" should "be NOT exist" in {
    graph.checkRoute(1, 0) should be(false)
  }

}

