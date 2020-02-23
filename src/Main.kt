fun main() {

    val myFile = FileEmulator("huffman compression")

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