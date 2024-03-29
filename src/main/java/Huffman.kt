package main.java

import java.io.File
import java.util.*
import java.util.stream.Collectors

object Huffman {

    class EncryptedFile(val inversedCodes: MutableMap<String, String>, val encrypted: String)

    /**
     * Converts huffman encoded text to plain text.
     * @return original uncompressed ascii text.
     */
    fun decodeText(fname: String): String {

        val encryptedFile = readEncryptedFile(fname)

        val ascii = StringBuilder()
        val d = StringBuilder()

        encryptedFile.encrypted.forEach {

            d.append(it)

            if (codeInterpreter(d.toString(), encryptedFile.inversedCodes) == 1) {
                ascii.append(encryptedFile.inversedCodes[d.toString()])
                d.clear()
            }

        }

        return ascii.toString()

    }

    /**
     * Converts plain text to huffman encoded text.
     * @param asciiText ascii text from the file.
     */
    fun encodeText(asciiText: String, fname: String) {

        val codeMapping = mutableMapOf<String, String>()
        val codeMappingInvr = mutableMapOf<String, String>()

        if(asciiText.length == 1){
            codeMapping[asciiText] = "0"
            codeMappingInvr["0"] = asciiText
        }else{
            makeSymbolCodes(makeHuffmanTree(asciiText), "", codeMapping, codeMappingInvr)
        }

        val encodedText = buildString {
            asciiText.forEach { append(codeMapping[it.toString()]) }
        }

        writeEncryptedFile(encodedText, codeMappingInvr, fname)

    }

    private fun readEncryptedFile(fname: String): EncryptedFile {

        val inversedCodes = mutableMapOf<String, String>()

        val encryptedFile = File(fname).bufferedReader()

        val size = encryptedFile.readLine()!!.toInt()

        for (i in 0 until size) {
            val code = encryptedFile.readLine()!!.split("=".toRegex(), 2)
            inversedCodes[code[0]] = code[1]
        }

        val encodedText = encryptedFile.readLine()!!

        encryptedFile.close()

        return EncryptedFile(inversedCodes, encodedText)

    }

    private fun writeEncryptedFile(encodedText: String, codeMappingInvr: MutableMap<String, String>, fname: String) {

        File(fname).bufferedWriter().use {

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
     * @return non-negative number representing how many codes out of all the codes start
     * with crntCode.
     */
    private fun codeInterpreter(crntCode: String, codeMappingInvr: MutableMap<String, String>) =
        codeMappingInvr.keys.count { it.startsWith(crntCode) }

    /**
     * Create prefix unique codes for every character present in the file. Stores all
     * the characters and frequencies in main.java.getCodeMapping.
     * @param huffmanTree huffman tree.
     * @param code huffman codes for each character
     */
    private fun makeSymbolCodes(
        huffmanTree: HuffmanNode,
        code: String,
        codeMapping: MutableMap<String, String>,
        codeMappingInvr: MutableMap<String, String>
    ) {

        var myCode = code

        if (huffmanTree.leftChild != null) {
            myCode += "0"
            makeSymbolCodes(huffmanTree.leftChild!!, myCode, codeMapping, codeMappingInvr)
        }

        if (huffmanTree.leftChild == null && huffmanTree.rightChild == null) {
            codeMapping[huffmanTree.symbol!!.symbol] = myCode
            codeMappingInvr[myCode] = huffmanTree.symbol!!.symbol
        }

        /* cut the zero added by left branch */
        myCode = myCode.substring(0, myCode.length - 1)

        if (huffmanTree.rightChild != null) {
            myCode += "1"
            makeSymbolCodes(huffmanTree.rightChild!!, myCode, codeMapping, codeMappingInvr)
        }

    }

    /**
     * Builds the complete Huffman Tree.
     * @param asciiText ascii text from the file.
     * @return final Huffman tree which is the last element left in the priority queue.
     */
    private fun makeHuffmanTree(asciiText: String): HuffmanNode {

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
     * @return Frequency map of each ascii character.
     */
    private fun symbolFrequency(asciiText: String) =
        asciiText.chars()
            .mapToObj { it.toChar() }
            .collect(Collectors.groupingBy({ it }, Collectors.counting()))

}