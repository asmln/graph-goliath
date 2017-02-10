/**
  * Created by Anatoly Samoylenko on 08.02.2017.
  */
object Main {
  def main(args: Array[String]): Unit = {
    val randomGraph = Graph(10)
    randomGraph.randomInit(6)
    println(randomGraph)
    println(randomGraph.checkRoute(0,0))
    println(randomGraph.checkRoute(0,1))
    println(randomGraph.checkRoute(0,2))
    println(randomGraph.checkRoute(0,3))
    println(randomGraph.checkRoute(0,4))
    println(randomGraph.checkRoute(0,9))

    val matrix: Array[Array[Boolean]] = Array(
      Array(false, true, false, false),
      Array(false, false, true, false),
      Array(false, false, false, false),
      Array(false, false, true, false)
    )
    val graph = Graph(matrix)

    println(graph)
    println(graph.checkRoute(0,0))
    println(graph.checkRoute(0,1))
    println(graph.checkRoute(0,2))
    println(graph.checkRoute(2,1))
    println(graph.checkRoute(2,3))
    println(graph.checkRoute(0,3))
  }
}
