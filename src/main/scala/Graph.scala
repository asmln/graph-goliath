import scala.util.Random

/**
  * Created by Anatoly Samoylenko on 08.02.2017.
  */
class Graph[T <: GraphStorage](storage: T) {

  def this(storage: T, matrix: Array[Array[Boolean]]) {
    this(storage)
    for(i <- 0 until storage.size) {
      for(j <- 0 until storage.size) {
        storage.setPath(i, j, matrix(i)(j))
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
    // если ищем путь в саму себя - то проверяем очевидный вариант,
    // прежде чем запускать поиск длинного пути
    if (a == b && storage.path(a, b)) {
        true
    } else {
      checkRoute(a, b, storage.createNodes(storage.size), firstStep = true)
    }
  }

  private def checkRoute(a: Int, b: Int, nodes: NodesStorage, firstStep: Boolean): Boolean =
    // если из a есть прямой путь в саму себя - то мы не попадаем в эту функцию.
    // значит надо искать путь через другие ноды.
    // и выход на нулевом шаге невозможен.
    if (a == b && !firstStep) {
      true
    } else {
      nodes(a) = a != b || !firstStep // не отмечаем ноду, если ищем длинный путь в саму себя
      var found = false
      var i = 0
      while ((i < storage.size) && !found) {
        if (storage.path(a, i) && !nodes(i)) {
          found = checkRoute(i, b, nodes, firstStep = false)
        }
        i += 1
      }
      found
    }

  def clearResources(): Unit = {
    storage.clearResources()
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

object GraphHDD {
  def apply(nodesCount: Integer): Graph[HDDGraphStorage] = {
    new Graph(new HDDGraphStorage(nodesCount))
  }

  def apply(matrix: Array[Array[Boolean]]): Graph[HDDGraphStorage] = {
    new Graph(new HDDGraphStorage(matrix.length), matrix)
  }
}
