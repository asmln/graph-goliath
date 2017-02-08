import scala.util.Random

/**
  * Created by Anatoly Samoylenko on 08.02.2017.
  */
class Graph(nodesCount: Int) {

  val matrix: Array[Array[Byte]] = Array.ofDim[Byte](nodesCount, nodesCount)
  val nodes: Array[Boolean] = Array.ofDim[Boolean](nodesCount)

  def randomInit(): Unit = {
    for(i <- 0 until nodesCount) {
      for(j <- 0 until nodesCount) {
        matrix(i)(j) = Random.nextInt(2).asInstanceOf[Byte]
      }
      nodes(i) = false
    }
  }

  def checkPath(a: Int, b: Int): Boolean = {
    false
  }

  override def toString: String = {
    matrix.deep.mkString("\n")
  }
}

object Graph {
  def apply(nodesCount: Integer): Graph = new Graph(nodesCount)
}
