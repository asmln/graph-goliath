package gg.graph

import java.io.File

import scala.util.Random

/**
  * Created by Anatoly Samoylenko on 10.02.2017.
  */

class Graph private (storage: GraphStorage) extends AutoCloseable {

  private def this(storage: GraphStorage, matrix: Array[Array[Boolean]]) {
    this(storage)
    for(i <- matrix.indices) {
      for(j <- matrix.indices) {
        storage.setPath(i, j, matrix(i)(j))
      }
    }
  }

  def randomInit(n: Int): Unit = {
    for(i <- 0L until storage.size) {
      for(j <- 0L until storage.size) {
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
      val nodes = storage.createNodes(storage.size)
      val result = checkRoute(a, b, storage.createNodes(storage.size), firstStep = true)
      nodes.close()
      result
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

  override def close(): Unit = {
    storage.close()
  }

  override def toString: String = {
    storage.toString
  }
}

object Graph {
  def apply(nodesCount: Long) = new Graph(GraphStorage(None, nodesCount))

  def apply(matrix: Array[Array[Boolean]]) = new Graph(GraphStorage(None, matrix.length), matrix)

  def apply(fileName: String, nodesCount: Long): Graph = {
    val file = new File(fileName)
    if (file.exists()) {
      new Graph(GraphStorage(Some(file), Math.min(file.length(), nodesCount)))
    } else {
      new Graph(GraphStorage(None, nodesCount))
    }
  }
}
