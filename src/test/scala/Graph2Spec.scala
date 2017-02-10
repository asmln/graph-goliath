import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by Anatoly Samoylenko on 10.02.17.
  */
class Graph2Spec extends FlatSpec with Matchers {

  // 0 -> 2 -> 4 -> 1
  // 4 -> 4
  // 5 -> 6 -> 8 -> 7
  // 6 -> 5
  // 11 -> 10 -> 9 -> 11
  val matrix: Array[Array[Boolean]] = Array(
    Array(false, false, true, false, false, false, false, false, false, false, false, false),
    Array(false, false, false, false, false, false, false, false, false, false, false, false),
    Array(false, false, false, false, true, false, false, false, false, false, false, false),
    Array(false, false, false, false, false, false, false, false, false, false, false, false),
    Array(false, true, false, false, true, false, false, false, false, false, false, false),
    Array(false, false, false, false, false, false, true, false, false, false, false, false),
    Array(false, false, false, false, false, true, true, false, true, false, false, false),
    Array(false, false, false, false, false, false, false, false, false, false, false, false),
    Array(false, false, false, false, false, false, false, true, false, false, false, false),
    Array(false, false, false, false, false, false, false, false, false, false, false, true),
    Array(false, false, false, false, false, false, false, false, false, true, false, false),
    Array(false, false, false, false, false, false, false, false, false, false, true, false)
  )
  val graph = GraphHDD(matrix)

  "Path 0 to 1" should "be exist" in {
    graph.checkRoute(0, 1) should be(true)
  }

  "Path 2 to 4" should "be exist" in {
    graph.checkRoute(2, 4) should be(true)
  }

  "Path 4 to 4" should "be exist" in {
    graph.checkRoute(4, 4) should be(true)
  }

  "Path 5 to 7" should "be exist" in {
    graph.checkRoute(5, 7) should be(true)
  }

  "Path 5 to 5" should "be exist" in {
    graph.checkRoute(5, 5) should be(true)
  }

  "Path 6 to 6" should "be exist" in {
    graph.checkRoute(6, 6) should be(true)
  }

  "Path 6 to 5" should "be exist" in {
    graph.checkRoute(6, 5) should be(true)
  }

  "Path 11 to 9" should "be exist" in {
    graph.checkRoute(11, 9) should be(true)
  }

  "Path 11 to 11" should "be exist" in {
    graph.checkRoute(11, 11) should be(true)
  }

  "Path 0 to 0" should "be NOT exist" in {
    graph.checkRoute(0, 0) should be(false)
  }

  "Path 2 to 0" should "be NOT exist" in {
    graph.checkRoute(2, 0) should be(false)
  }

}
