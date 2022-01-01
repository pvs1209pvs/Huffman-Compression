package main.java

fun main() {

    val myFile = FileEmulator("param")

    symbolCodes(makeHuffmanTree(myFile.userText), "")

    val asciiToBin = myFile.asciiToBinStream(false)
    val asciiToHuffBin = myFile.asciiToBinStream(true)

    val binToAscii = myFile.binaryToAscii(asciiToBin, false)
    val huffBinToAscii = myFile.binaryToAscii(asciiToHuffBin, true)

    println("bin $asciiToBin")
    println("huffBin $asciiToHuffBin")

    println(binToAscii)
    println(huffBinToAscii)


}