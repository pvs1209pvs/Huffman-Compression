fun main() {

    val myFile = FileEmulator("o!p##a@p<{vq<e{s9?4uv5\n" +
            "ihfos:|ouid9|(5% d!)mk@\n" +
            "y||\n" +
            "rpn#\n" +
            "f\n" +
            "g\n" +
            "&?^u_\n" +
            "aomb@d^9+<a5v_<df}nvrd i #6u&4ha7 \n" +
            "\n")

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