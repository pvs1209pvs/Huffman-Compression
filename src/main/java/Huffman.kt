package main.java

import java.io.File
import java.util.*

var codeMapping = mutableMapOf<String, String>()
var codeMappingInvr = mutableMapOf<String, String>()

class EncryptedFile(val inversedCodes: Map<String, String>, val encrypted: String)

/**
 * Converts the continuous stream of huffman code to ascii text.
 * @return original uncompressed ascii text.
 */
fun decodeText(): String {

    val encryptedFile = readEncryptedFile()

    val ascii = StringBuilder()
    val d = StringBuilder()

    encryptedFile.encrypted.forEach {

        d.append(it)

        if (codeInterpreter(d.toString()) == 1) {
            ascii.append(encryptedFile.inversedCodes[d.toString()])
            d.clear()
        }

    }

    return ascii.toString()

}

/**
 * Converts ascii text to huffman coding.
 * @param asciiText ascii text from the file.
 * @return huffman binary stream of the asciiText.
 */
fun encodedText(asciiText: String) {

    symbolCodes(makeHuffmanTree(asciiText), "")

    val encodedText = buildString {
        asciiText.forEach { append(codeMapping[it.toString()]) }
    }

    writeEncryptedFile(encodedText)

}

fun readEncryptedFile(): EncryptedFile {

    val inversedCodes = mutableMapOf<String, String>()

    val encryptedFile = File("output.txt").bufferedReader()

    val size = encryptedFile.readLine()!!.toInt()

    for (i in 0 until size) {
        val code = encryptedFile.readLine()!!.split("=".toRegex(), 2)
        inversedCodes[code[0]] = code[1]
    }

    val encodedText = encryptedFile.readLine()!!

    return EncryptedFile(inversedCodes, encodedText)

}

fun writeEncryptedFile(encodedText: String) {

    File("output.txt").bufferedWriter().use {

        it.write("${codeMappingInvr.size}\n")

        codeMappingInvr.forEach { entry ->
            it.write("${entry.key}=${entry.value}\n")
        }

        it.write(encodedText)
        it.write("\n")
    }

}

/**
 * Count of how many codes starts with crntCode.
 * @param crntCode huffman code whose associated character needs to be returned.
 * @return non-negative number repsenting how many codes out of all the codes start
 * with crntCode.
 */
private fun codeInterpreter(crntCode: String) = codeMappingInvr.keys.count { it.startsWith(crntCode) }

/**
 * Create prefix unique codes for every character present in the file. Stores all
 * the characters and frequencies in main.java.getCodeMapping.
 * @param huffmanTree huffman tree.
 * @param code huffman codes for each character
 */
fun symbolCodes(huffmanTree: HuffmanNode, code: String) {

    var myCode = code

    if (huffmanTree.leftChild != null) {
        myCode += "0"
        symbolCodes(huffmanTree.leftChild!!, myCode)
    }

    if (huffmanTree.leftChild == null && huffmanTree.rightChild == null) {
        codeMapping[huffmanTree.symbol!!.symbol] = myCode
        codeMappingInvr[myCode] = huffmanTree.symbol!!.symbol
    }

    /* cut the zero added by left branch */
    myCode = (myCode.substring(0, myCode.length - 1))

    if (huffmanTree.rightChild != null) {
        myCode += "1"
        symbolCodes(huffmanTree.rightChild!!, myCode)
    }

}

/**
 * Builds the complete Huffman Tree.
 * @param asciiText ascii text from the file.
 * @return final Huffman tree which is the last element left in the priority queue.
 */
fun makeHuffmanTree(asciiText: String): HuffmanNode {

    val symbPQ = symbolPriorityQueue(asciiText)

    while (symbPQ.size > 1) {

        val l = symbPQ.remove()
        val r = symbPQ.remove()
        val symbol = Symbol(l.symbol!!.symbol + r.symbol!!.symbol, l.symbol!!.freq + r.symbol!!.freq)

        symbPQ.add(HuffmanNode(symbol, l, r))

    }

    return symbPQ.remove()

}


/**
 * Prepares a priority queue of all the symbol in ascending order of their
 * frequencies
 * @param asciiText ascii text from the file.
 * @return priority queue with all the symbols in it.
 */
private fun symbolPriorityQueue(asciiText: String) =
    symbolFrequency(asciiText).entries
        .map { HuffmanNode(Symbol(it.key.toString(), it.value)) }
        .toCollection(PriorityQueue())


/**
 * Returns the frequency of every symbol present in the file.
 * @param asciiText ascii text from the file.
 * @return map of count for each ascii character..
 */
private fun symbolFrequency(asciiText: String): Map<Char, Int> {

    val symbFreq = mutableMapOf<Char, Int>()

    asciiText.forEach {
        symbFreq[it] =
            if (!symbFreq.containsKey(it)) 1
            else symbFreq.getValue(it) + 1
    }

    return symbFreq

}