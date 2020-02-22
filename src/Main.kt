import java.lang.StringBuilder
import java.math.BigInteger
import kotlin.math.pow

fun main() {


    val myFile = FileEmulator("this is great")

    val huffmanNode = makeHuffmanTree(myFile.userText)
    symbolCodes(huffmanNode, "")

    val asciiToBin = myFile.buildBinText(false)
    val asciiToHuffBin = myFile.buildBinText(true)

    println(asciiToBin)
    println(asciiToHuffBin)

//    val binToAscii = myFile.binaryToAscii(asciiToBin, false)
//    val huffBinToAscii = myFile.binaryToAscii(asciiToHuffBin, true)
//
//    println(binToAscii)
//    println(huffBinToAscii)
}