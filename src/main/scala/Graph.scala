import scala.util.Random

/**
  * Created by Anatoly Samoylenko on 08.02.2017.
  */
class Graph[T <: GraphStorage](storage: T) {

  def this(storage: T, matrix: Array[Array[Boolean]]) {
    this(storage)
    for(i <- 0 until storage.size) {
      for(j <- 0 until storage.size) {
        if (matrix(i)(j)) storage.addPath(i, j)
      }
    }
  }

  def randomInit(n: Int): Unit = {
    for(i <- 0 until storage.size) {
      for(j <- 0 until storage.size) {
        storage.setPath(i, j, Random.nextInt(n) == 0)
      }
    }
  }

  def checkRoute(a: Int, b: Int): Boolean = {
    if (a == b) {
      storage.path(a, b)
    } else {
      checkRoute(a, b, storage.createNodes(storage.size))
    }
  }

  private def checkRoute(a: Int, b: Int, nodes: NodeStorage): Boolean =
    if (a == b) {
      true
    } else {
      nodes(a) = true
      var found = false
      var i = 0
      while ((i < storage.size) && !found) {
        if (storage.path(a, i) && !nodes(i)) {
          found = checkRoute(i, b, nodes)
        }
        i += 1
      }
      found
    }


  override def toString: String = {
    storage.toString
  }
}

object Graph {
  def apply(nodesCount: Integer): Graph[MemoryGraphStorage] = {
    new Graph(new MemoryGraphStorage(nodesCount))
  }
  def apply(matrix: Array[Array[Boolean]]): Graph[MemoryGraphStorage] = {
    new Graph(new MemoryGraphStorage(matrix.length), matrix)
  }
}
