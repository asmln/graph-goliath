package gg

/**
  * Created by Anatoly Samoylenko on 10.02.2017.
  */
object ByteOperations {

  val BYTE_SIZE = 8

  def bitFromByte(byte: Byte, position: Int): Byte =
    ((byte >> position) & 1).asInstanceOf[Byte]

  def setBitInByte(byte: Byte, position: Int, on: Boolean): Byte = if (on) {
    (byte | (1 << position)).asInstanceOf[Byte]
  } else {
    (byte & ~(1 << position)).asInstanceOf[Byte]
  }

  def toBinaryString(byte: Byte): String = String.format("%8s", Integer.toBinaryString(byte & 0xFF)).replace(' ', '0')

  def toByte(binaryString: String): Byte = Integer.valueOf(binaryString, 2).toByte

  def positionToByteNumber(a: Long, b: Long, bytesCount: Long): Long = a * bytesCount + b / BYTE_SIZE

  def positionToByteNumber(a: Long): Long = a / BYTE_SIZE

  def positionToShift(a: Long): Int = (a % BYTE_SIZE).toInt

}
