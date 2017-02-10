import java.io.{File, RandomAccessFile}

/**
  * Created by Anatoly Samoylenko on 10.02.17.
  */
trait GraphStorage {

  def setPath(a: Int, b: Int, path: Boolean)

  def path(a: Int, b: Int): Boolean

  def addPath(a: Int, b: Int): Unit = {
    setPath(a, b, path = true)
  }

  def removePath(a: Int, b: Int): Unit = {
    setPath(a, b, path = false)
  }

  def createNodes(nodesCount: Int): NodesStorage

  val size: Int

}

trait NodesStorage {

  def apply(position: Int): Boolean

  def update(i: Int, value: Boolean): Unit

}

class MemoryGraphStorage(nodesCount: Int) extends GraphStorage {

  val matrix: Array[Array[Boolean]] = Array.ofDim[Boolean](nodesCount, nodesCount)

  val size: Int = nodesCount

  override def path(a: Int, b: Int): Boolean = matrix(a)(b)

  override def setPath(a: Int, b: Int, path: Boolean): Unit = {
    matrix(a)(b) = path
  }

  override def toString: String = {
    matrix.deep.mkString("\n")
  }

  override def createNodes(nodesCount: Int): NodesStorage = new MemoryNodesStorage(nodesCount)
}

class MemoryNodesStorage(nodesCount: Int) extends NodesStorage {
  private val storage: Array[Boolean] = Array.ofDim[Boolean](nodesCount)

  override def apply(position: Int): Boolean = storage(position)

  override def update(i: Int, value: Boolean): Unit = {
    storage(i) = value
  }
}

class HDDGraphStorage(nodesCount: Int) extends GraphStorage {

  val bytesCount: Int = nodesCount / 8 + 1
  val file: File = new File("/home/samoylenko/temp/graph.bbb")
  val matrix: RandomAccessFile = new RandomAccessFile(file, "rw")
  matrix.setLength(bytesCount * nodesCount)

  val size: Int = nodesCount

  override def path(a: Int, b: Int): Boolean = {
    val byteNumber = a * bytesCount + b / 8
    matrix.seek(byteNumber)
    val byte: Byte = matrix.readByte()
    val shift = b % 8
    val bit = ByteOperations.bitFromByte(byte, shift)
    bit == 1
  }

  override def setPath(a: Int, b: Int, path: Boolean): Unit = {
    val byteNumber = a * bytesCount + b / 8
    matrix.seek(byteNumber)
    val byte: Byte = matrix.readByte()
    val shift = b % 8
    val newByte = ByteOperations.setBitInByte(byte, shift, path)
    matrix.seek(byteNumber)
    matrix.writeByte(newByte)
  }

  override def toString: String = {
    "File size = $nodesCount/$nodesCount"
  }

  override def createNodes(nodesCount: Int): NodesStorage = new HDDNodesStorage(nodesCount)
}

class HDDNodesStorage(nodesCount: Int) extends NodesStorage {
  private val storage: Array[Boolean] = Array.ofDim[Boolean](nodesCount)

  override def apply(position: Int): Boolean = storage(position)

  override def update(i: Int, value: Boolean): Unit = {
    storage(i) = value
  }
}
