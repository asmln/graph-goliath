/**
  * Created by Anatoly Samoylenko on 08.02.2017.
  */
object Main {
  def main(args: Array[String]): Unit = {
    val randomGraph = Graph(10)
    randomGraph.randomInit(6)
    println(randomGraph)
    println(randomGraph.checkPath(0,0))
    println(randomGraph.checkPath(0,1))
    println(randomGraph.checkPath(0,2))
    println(randomGraph.checkPath(0,3))
    println(randomGraph.checkPath(0,4))
    println(randomGraph.checkPath(0,9))

    val matrix: Array[Array[Byte]] = Array(
      Array(0.toByte, 1.toByte, 0.toByte, 0.toByte),
      Array(0.toByte, 0.toByte, 1.toByte, 0.toByte),
      Array(0.toByte, 0.toByte, 0.toByte, 0.toByte),
      Array(0.toByte, 0.toByte, 1.toByte, 0.toByte)
    )
    val graph = Graph(matrix)

    println(graph)
    println(graph.checkPath(0,0))
    println(graph.checkPath(0,1))
    println(graph.checkPath(0,2))
    println(graph.checkPath(2,1))
    println(graph.checkPath(2,3))
    println(graph.checkPath(0,3))
  }
}
