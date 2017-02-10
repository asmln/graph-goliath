import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by Anatoly Samoylenko on 10.02.2017.
  */
class GraphSpec extends FlatSpec with Matchers {

  val matrix: Array[Array[Byte]] = Array(
    Array(0.toByte, 1.toByte, 0.toByte, 0.toByte),
    Array(0.toByte, 0.toByte, 1.toByte, 0.toByte),
    Array(0.toByte, 0.toByte, 0.toByte, 0.toByte),
    Array(0.toByte, 0.toByte, 1.toByte, 0.toByte)
  )
  val graph = Graph(matrix)

  "Path 0 to 1" should "be exist" in {
    graph.checkPath(0, 1)
  }

  "Path 0 to 2" should "be exist" in {
    graph.checkPath(0, 2)
  }

  "Path 3 to 2" should "be exist" in {
    graph.checkPath(3, 2)
  }

  "Path 0 to 0" should "be NOT exist" in {
    graph.checkPath(0, 0)
  }

  "Path 0 to 3" should "be NOT exist" in {
    !graph.checkPath(0, 3)
  }

  "Path 1 to 0" should "be NOT exist" in {
    !graph.checkPath(1, 0)
  }

}

