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

  def createNodes(nodesCount: Int): NodeStorage

  val size: Int

}

trait NodeStorage {

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

  override def createNodes(nodesCount: Int): NodeStorage = new MemoryNodeStorage(nodesCount)
}

class MemoryNodeStorage(nodesCount: Int) extends NodeStorage {
  private val storage: Array[Boolean] = Array.ofDim[Boolean](nodesCount)

  override def apply(position: Int): Boolean = storage(position)

  override def update(i: Int, value: Boolean): Unit = {
    storage(i) = value
  }
}
