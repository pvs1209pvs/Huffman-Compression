package main.java

import java.util.*

class Huffman(private val plainText: String) {

    private val codeMapping by lazy { mutableMapOf<String, String>() }
    private val codeMappingInvr by lazy { mutableMapOf<String, String>() }

    val encodedText by lazy { encodedText() }
    val decodedText by lazy { decodeText() }

    init {
        symbolCodes(makeHuffmanTree(plainText), "")
    }

    /**
     * Converts the continuous stream of huffman code to ascii text.
     * @return original uncompressed ascii text.
     */
    private fun decodeText(): String {

        val ascii = StringBuilder()
        val d = StringBuilder()

        encodedText.forEach {

            d.append(it)

            if (codeInterpreter(d.toString()) == 1) {
                ascii.append(codeMappingInvr[d.toString()])
                d.clear()
            }

        }

        return ascii.toString()

    }

    /**
     * Converts ascii text to huffman coding.
     * @return huffman binary stream of the asciiText.
     */
    private fun encodedText(): String {

        val encodedText = StringBuilder()

        plainText.forEach {
            encodedText.append(codeMapping[it.toString()])
        }



        return encodedText.toString()

    }

    /**
     * Count of how many codes starts with crntCode.
     * @param crntCode huffman code whose associated character needs to be returned.
     * @return non-negative number representing how many codes out of all the codes start
     * with crntCode.
     */
    private fun codeInterpreter(crntCode: String) = codeMappingInvr.keys.count { it.startsWith(crntCode) }

    /**
     * Create prefix unique codes for every character present in the file. Stores all
     * the characters and frequencies in main.java.getCodeMapping.
     * @param huffmanTree huffman tree.
     * @param code huffman codes for each character
     */
    private fun symbolCodes(huffmanTree: HuffmanNode, code: String) {

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
     * @return map of count for each ascii character.
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

}