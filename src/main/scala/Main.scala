/**
  * Created by Anatoly Samoylenko on 08.02.2017.
  */
object Main {
  def main(args: Array[String]): Unit = {
    val graph = Graph(10)
    graph.randomInit()
    println(graph)
  }
}
