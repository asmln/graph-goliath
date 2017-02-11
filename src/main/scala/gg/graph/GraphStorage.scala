package gg.graph

import java.io.{File, RandomAccessFile}

import gg.ByteOperations._

import scala.collection.immutable.NumericRange.Exclusive

/**
  * Created by Anatoly Samoylenko on 10.02.2017.
  */

//хранилище для матрицы смежности графа
//файл. строки идут друг за другом, значения (0 - нет пути, 1 - есть путь) элементов строки хранятся в битах.
//байт заполняется справа налево.
//это уменьшает размер файла
private[graph] class GraphStorage (file: Option[File], nodesCount: Long) extends AutoCloseable {

  private val lengthBytesCount = 8 //первые 8 байт для хранения длины файла
  private val bytesCount = nodesCount / BYTE_SIZE + 1
  private val uuid: String = java.util.UUID.randomUUID.toString

  private val matrix: RandomAccessFile = file match {
    case Some(fl) if fl.exists =>
      new RandomAccessFile(fl, "rwd")
    case Some(fl) if !fl.exists =>
      createFile(fl)
    case _ =>
      createFile(new File(s"tmp/graph-$uuid.bbb"))
  }

  private def createFile(file: File) = {
    val rf = new RandomAccessFile(file, "rwd")
    rf.setLength(lengthBytesCount + bytesCount * nodesCount)
    rf.writeLong(nodesCount)
    rf
  }

  val length: Long = nodesCount
  def indices: Exclusive[Long] = 0L until length

  def apply(a: Long, b: Long): Boolean = {
    matrix.seek(lengthBytesCount + positionToByteNumber(a, b, bytesCount))
    1 == bitFromByte(matrix.readByte(), positionToShift(b))
  }

  def update(a: Long, b: Long, path: Boolean): Unit = {
    val byteNumber = lengthBytesCount + positionToByteNumber(a, b, bytesCount)
    matrix.seek(byteNumber)
    val newByte = setBitInByte(matrix.readByte(), positionToShift(b), path)
    matrix.seek(byteNumber)
    matrix.writeByte(newByte)
  }

  override def toString: String = {
    s"File size = $nodesCount/$nodesCount"
  }

  def createNodes(nodesCount: Long): NodesStorage = new NodesStorage(nodesCount)

  override def close(): Unit = {
    matrix.close()
  }

}

//хранилище для "подкрашенных" вершин
//файл. значения (0 - не посещали или 1 - посещали) хранятся в битах.
private[graph] class NodesStorage (nodesCount: Long) extends AutoCloseable {

  private val bytesCount = nodesCount / BYTE_SIZE + 1

  private val file: File = GraphStorage.createTempFile("nodes")

  private val nodes: RandomAccessFile = new RandomAccessFile(file, "rwd")
  nodes.setLength(bytesCount)

  val length: Long = nodesCount

  def apply(position: Long): Boolean = {
    nodes.seek(positionToByteNumber(position))
    1 == bitFromByte(nodes.readByte(), positionToShift(position))
  }

  def update(position: Long, value: Boolean): Unit = {
    val byteNumber = positionToByteNumber(position)
    nodes.seek(byteNumber)
    val newByte = setBitInByte(nodes.readByte(), positionToShift(position), value)
    nodes.seek(byteNumber)
    nodes.writeByte(newByte)
  }

  override def close(): Unit = {
    nodes.close()
    file.delete()
  }

}

//стэк для хранения вершин. для нерекурсивной версии алгоритма поиска в глубину.
//файл. значения хранятся в Long. т.к. тут надо хранить номер вершины
private[graph] class NodesStack(nodesCount: Long) extends AutoCloseable {

  private val file: File = GraphStorage.createTempFile("stack")
  private val stack: RandomAccessFile = new RandomAccessFile(file, "rwd")
  stack.setLength(nodesCount * 8)
  private var position = 0L

  def put(i: Long): Unit = {
    stack.seek(position)
    stack.writeLong(i)
    position += 8
  }

  def peek(): Option[Long] = {
    position -= 8
    if (position >= 0) {
      stack.seek(position)
      val result = Some(stack.readLong())
      result
    } else {
      None
    }
  }

  def empty: Boolean = position <= 0

  override def close(): Unit = {
    stack.close()
    file.delete()
  }
}

object GraphStorage {

  def apply(file: Option[File], nodesCount: Long) = new GraphStorage(file, nodesCount)

  private[graph] def createTempFile(fileName: String): File = {
    val uuid: String = java.util.UUID.randomUUID.toString
    val tmp = new File("tmp")
    if (!tmp.exists()) tmp.mkdir()
    new File(s"tmp/$fileName-$uuid.bbb")
  }

}
