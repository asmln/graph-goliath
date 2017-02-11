import java.io.File

import gg.graph.Graph

/**
  * Created by Anatoly Samoylenko on 08.02.2017.
  */
object Main {

  type OptionMap = Map[Symbol, Any]

  val usage = """
    Usage:
     gg [--path-file filename] [--graph-size num] [--start num] [--finish num]
     gg [--graph-file filename] [--graph-size num] [--start num] [--finish num]
     gg [--create-files num]
  """

  def generateDiagonal(graphSize: Long) = {
    new File("tmp").mkdir()
    val graph = Graph("tmp/diagonal.bbb", graphSize)
    for(i <- 0L until graphSize) {
      graph.addPath(i, i)
    }
    graph.close()
  }

  def main(args: Array[String]) {
    if (args.length != 1) {
      println(usage)
    } else {
      val options = parseArgs(args)
      if (options.contains('createfiles)) {
        val graphSize = options('createfiles).asInstanceOf[Long]
        generateDiagonal(graphSize)
        //generateFirstToLast(graphSize)
      } else {
        val start = options('start).asInstanceOf[Long]
        val finish = options('finish).asInstanceOf[Long]
        val size = options('graphsize).asInstanceOf[Long]

        val graph = if (options.contains('graphfile)) {
          Graph(options('graphfile).asInstanceOf[String], size)
        } else {
          Graph(size)
        }
        if (graph.checkRoute(start, finish)) {
          println(s"Route from $start to $finish exists.")
        } else {
          println(s"Route from $start to $finish not exists.")
        }
        graph.close()
      }
    }
  }

  def parseArgs(args: Array[String]): OptionMap = {
    val argList = args(0).split(" ").toList

    def nextOption(map : OptionMap, list: List[String]) : OptionMap = {
      list match {
        case Nil => map
        case "--create-files" :: value :: Nil =>
          Map('createfiles -> value.toLong)
        case "--path-file" :: value :: tail =>
          nextOption(map ++ Map('pathfile -> value.toString), tail)
        case "--graph-file" :: value :: tail =>
          nextOption(map ++ Map('graphfile -> value.toString), tail)
        case "--graph-size" :: value :: tail =>
          nextOption(map ++ Map('graphsize -> value.toLong), tail)
        case "--start" :: value :: tail =>
          nextOption(map ++ Map('start -> value.toLong), tail)
        case "--finish" :: value :: tail =>
          nextOption(map ++ Map('finish -> value.toLong), tail)
        case option :: tail => println("Unknown option " + option)
          nextOption(map, list.tail)
      }
    }
    nextOption(Map(), argList)
  }

}
