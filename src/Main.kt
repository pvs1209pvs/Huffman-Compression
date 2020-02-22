fun main() {

    val a :Long = 11111111110

    val myFile = FileEmulator("abcdefgh")

    val asciiToBin = myFile.buildBinText(false)
    val asciiToHuffBin = myFile.buildBinText(true)

    println(asciiToBin)
    println(asciiToHuffBin)

    val binToAscii = myFile.binaryToAscii(asciiToBin, false)
    val huffBinToAscii = myFile.binaryToAscii(asciiToHuffBin, true)

    println(binToAscii)
    println(huffBinToAscii)
}