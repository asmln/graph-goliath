package gg.graph

import java.io.{File, RandomAccessFile}

import gg.ByteOperations._

/**
  * Created by Anatoly Samoylenko on 10.02.2017.
  */

private[graph] class GraphStorage (file: Option[File], nodesCount: Long) extends AutoCloseable {

  private val bytesCount = nodesCount / BYTE_SIZE + 1
  private val uuid: String = java.util.UUID.randomUUID.toString
  private val storageFile: File = file match {
    case Some(fl) if fl.exists() =>
      fl
    case _ =>
      new File(s"tmp/graph-$uuid.bbb")
  }
  private val matrix: RandomAccessFile = new RandomAccessFile(storageFile, "rwd")
  matrix.setLength(bytesCount * nodesCount)

  val size: Long = nodesCount

  def path(a: Long, b: Long): Boolean = {
    matrix.seek(positionToByteNumber(a, b, bytesCount))
    1 == bitFromByte(matrix.readByte(), positionToShift(b))
  }

  def setPath(a: Long, b: Long, path: Boolean): Unit = {
    val byteNumber = positionToByteNumber(a, b, bytesCount)
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

private[graph] class NodesStorage (nodesCount: Long) extends AutoCloseable {

  private val bytesCount = nodesCount / BYTE_SIZE + 1
  private val uuid: String = java.util.UUID.randomUUID.toString
  private val tmp = new File("tmp")
  if (!tmp.exists()) tmp.mkdir()
  private val file: File = new File(s"tmp/nodes-$uuid.bbb")

  private val nodes: RandomAccessFile = new RandomAccessFile(file, "rwd")
  nodes.setLength(bytesCount)

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

object GraphStorage {

  def apply(file: Option[File], nodesCount: Long) = new GraphStorage(file, nodesCount)

}
