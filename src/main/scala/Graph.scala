import scala.util.Random

/**
  * Created by Anatoly Samoylenko on 08.02.2017.
  */
class Graph(nodesCount: Int) {

  val matrix: Array[Array[Byte]] = Array.ofDim[Byte](nodesCount, nodesCount)

  def this(matrix: Array[Array[Byte]]) {
    this(matrix.length)
    for(i <- 0 until nodesCount) {
      for(j <- 0 until nodesCount) {
        if (matrix(i)(j) == 1) this.addRoute(i, j)
      }
    }
  }

  def addRoute(a: Int, b:Int): Unit = {
    matrix(a)(b) = 1
  }

  def randomInit(n: Int): Unit = {
    for(i <- 0 until nodesCount) {
      for(j <- 0 until nodesCount) {
        matrix(i)(j) = if (Random.nextInt(n) == 0) 1 else 0
      }
    }
  }

  def checkPath(a: Int, b: Int): Boolean = {
    if (a == b) {
      matrix(a)(b) == 1
    } else {
      checkPath(a, b, Array.ofDim[Byte](nodesCount))
    }
  }

  private def checkPath(a: Int, b: Int, nodes: Array[Byte]): Boolean =
    if (a == b) {
      true
    } else {
      nodes(a) = 1
      var found = false
      var i = 0
      while ((i < nodesCount) && !found) {
        if (matrix(a)(i) == 1 && nodes(i) == 0) {
          found = checkPath(i, b, nodes)
        }
        i += 1
      }
      found
    }


  override def toString: String = {
    matrix.deep.mkString("\n")
  }
}

object Graph {
  def apply(nodesCount: Integer): Graph = new Graph(nodesCount)
  def apply(matrix: Array[Array[Byte]]): Graph = new Graph(matrix)
}
