package main.java

fun main() {

    val huffman = Huffman("hello world!")

    println("Encoded text = ${huffman.encodedText}")
    println("Decoded text = ${huffman.decodedText}")


}