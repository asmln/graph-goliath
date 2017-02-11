import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar

import gg.graph.Graph

/**
  * Created by Anatoly Samoylenko on 08.02.2017.
  */
object Main {

  type OptionMap = Map[Symbol, Any]

  val usage = """
    Usage:
      gg --create-diagonal graph-size
      gg --create-fl graph-size
      gg filename graph-size start finish
  """

  def generateDiagonal(graphSize: Long): Unit = {
    generateGraph(graphSize, "diagonal.bbb", g => {
      for(i <- 0L until g.length) {
        g.addPath(i, i)
      }
    })
  }

  def generateFirstToLast(graphSize: Long): Unit = {
    generateGraph(graphSize, "first-to-last.bbb", g => {
      for(i <- 0L until g.length - 1) {
        g.addPath(i, i + 1)
      }
    })
  }

  private def generateGraph(graphSize: Long, fileName: String, generator: Graph => Unit): Unit = {
    val tmp = new File("tmp")
    if (!tmp.exists()) tmp.mkdir()
    val file = new File(s"tmp/$fileName")
    if (file.exists()) {
      file.delete()
    }
    val graph = Graph(s"tmp/$fileName", graphSize)
    generator(graph)
    graph.close()
  }

  def main(args: Array[String]) {
    val sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    println(s"Start time: ${sdf.format(Calendar.getInstance().getTime)}")

    if (args.length == 0) {
      println(usage)
    } else {
      args match {
        case Array("--create-diagonal", value, _*) =>
          generateDiagonal(value.toLong)
        case Array("--create-fl", value, _*) =>
          generateFirstToLast(value.toLong)
        case Array(fileName, size, start, finish, _*) =>
          val graph = Graph(fileName, size.toLong)
          if (graph.checkRoute(start.toLong, finish.toLong)) {
            println(s"Route from $start to $finish exists.")
          } else {
            println(s"Route from $start to $finish NOT exists.")
          }
          graph.close()
      }
    }

    println(s"Finish time: ${sdf.format(Calendar.getInstance().getTime)}")
  }

}
