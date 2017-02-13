import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.concurrent.ThreadLocalRandom

import gg.graph.Graph

/**
  * Created by Anatoly Samoylenko on 08.02.2017.
  */
object Main {

  type OptionMap = Map[Symbol, Any]

  val usage = """
    Usage:
      gg.jar --create-diagonal graph-size
      gg.jar --create-fl graph-size
      gg.jar --create-r graph-size route-size
      gg.jar filename graph-size start finish
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

  def generateRandom(graphSize: Long, routeSize: Long): Unit = {
    generateGraph(graphSize, "random.bbb", g => {
      var a = ThreadLocalRandom.current().nextLong(g.length)
      val routeStr = new StringBuilder()
      routeStr.append(s"$a")
      if (routeSize == 1) {
        g.addPath(a, a)
      } else {
        for (i <- 0L until routeSize - 1) {
          var b = ThreadLocalRandom.current().nextLong(g.length)
          g.addPath(a, b)
          routeStr.append(s"->$b")
          a = b
        }
      }
      println(s"Route: $routeStr")
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
        case Array("--create-diagonal", size, _*) =>
          generateDiagonal(size.toLong)
        case Array("--create-fl", size, _*) =>
          generateFirstToLast(size.toLong)
        case Array("--create-r", size, rSize, _*) =>
          generateRandom(size.toLong, rSize.toLong)
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
