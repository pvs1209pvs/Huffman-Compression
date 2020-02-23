fun main() {

    val myFile = FileEmulator("huffman compression")

    val huffmanNode = makeHuffmanTree(myFile.userText)
    symbolCodes(huffmanNode, "")

    val asciiToBin = myFile.buildBinText(false)
    val asciiToHuffBin = myFile.buildBinText(true)

    val binToAscii = myFile.binaryToAscii(asciiToBin, false)
    val huffBinToAscii = myFile.binaryToAscii(asciiToHuffBin, true)

    println("bin $asciiToBin")
    println("huffBin $asciiToHuffBin")

    println(binToAscii)
    println(huffBinToAscii)


}