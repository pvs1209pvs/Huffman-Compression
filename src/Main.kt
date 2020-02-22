import java.lang.StringBuilder

fun main() {

    val myFile = BackgroundFile("abccaad")
    myFile.buildBackgroundText(false)

    symbolCodes(makeHuffmanTree(myFile.userText), StringBuilder())

    println(myFile.buildBackgroundText(false))
}