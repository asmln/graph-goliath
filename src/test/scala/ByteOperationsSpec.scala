import ByteOperations._
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by Anatoly Samoylenko on 10.02.2017.
  */
class ByteOperationsSpec extends FlatSpec with Matchers {

  "Number 1" should "be 00000001 in binary" in {
    toBinaryString(1) should be ("00000001")
  }

  val bs = "01010101"
  val ibs = 85

  "Binary string $bs" should "be $ibs" in {
    toByte(bs) should be(ibs)
  }

  "Number $ibs" should "be $bs in binary" in {
    toBinaryString(ibs.toByte) should be(bs)
  }

  val byte: Byte = toByte(bs)

  "Number of 0 position in $bs" should "be 1" in {
    bitFromByte(byte, 0) should be(1)
  }

  "Number of 3 position in $bs" should "be 0" in {
    bitFromByte(byte, 3) should be(0)
  }

  "Number of 4 position in $bs" should "be 1" in {
    bitFromByte(byte, 4) should be(1)
  }

}
