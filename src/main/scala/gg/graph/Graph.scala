package gg.graph

import java.io.File

import scala.annotation.tailrec
import scala.util.Random

/**
  * Created by Anatoly Samoylenko on 10.02.2017.
  */
//граф
//содержит методы для заполения, проверки пути и т.д.
class Graph private (storage: GraphStorage) extends AutoCloseable {

  private def this(storage: GraphStorage, matrix: Array[Array[Boolean]]) {
    this(storage)
    for(i <- matrix.indices; j <- matrix.indices) {
        storage(i, j) = matrix(i)(j)
    }
  }

  val length: Long = storage.length

  //добавить путь между a и b
  def addPath(a: Long, b: Long): Unit = {
    storage(a, b) = true
  }

  //случайным образом заполняет файл
  def randomInit(n: Int): Unit = {
    for(i <- storage.indices; j <- storage.indices) {
        storage(i, j) = Random.nextInt(n) == 0
    }
  }

  //проверка маршрута
  def checkRoute(a: Long, b: Long): Boolean = {
    if (Math.max(a, b) > storage.length) throw new Exception("Position out of border!")
    // если ищем путь в саму себя - то проверяем очевидный вариант,
    // прежде чем запускать поиск длинного пути
    if (a == b && storage(a, b)) {
      true
    } else {
      val nodes = storage.createNodes(storage.length)
      val result = checkRoute(a, b, nodes) //checkRouteRec(a, b, nodes, firstStep = true)
      nodes.close()
      result
    }
  }

  //поиск в глубину. рекурсивный алгоритм не подойдёт, т.к. большие расходы.
  //тут большие расходы по времени для больших графов, т.к. перебирается каждая строка матрицы смежности
  private def checkRoute(a: Long, b: Long, nodes: NodesStorage): Boolean = {
    val stack = new NodesStack(nodes.length)
    stack.put(a)
    var found = false
    while (!stack.empty && !found) {
      val v = stack.peek()
      nodes(v.get) = true
      for (i <- storage.indices; if !found) {
        if (storage(v.get, i) && i == b) {
          found = true
        } else {
          if (storage(v.get, i) && !nodes(i)) {
            stack.put(i)
          }
        }
      }
    }
    stack.close()
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
      new Graph(GraphStorage(Some(file), nodesCount))
    }
  }

}
