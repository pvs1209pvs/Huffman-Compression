import java.util.*

var codeMapping = mutableMapOf<String, String>()
var codeMappingInvr = mutableMapOf<String, String>()

/**
 * Converts the continuous stream of huffman code to ascii text.
 * @param encodedText huffman binary stream.
 * @return original uncompressed ascii text.
 */
fun decodeText(encodedText: String): String {

    val ascii = StringBuilder()
    val d = StringBuilder()

    for (element in encodedText) {

        d.append(element)

        if(codeInterpreter(d.toString())==1){
            ascii.append(codeMappingInvr[d.toString()])
            d.clear()
        }

    }

    return ascii.toString()

}

/**
 * Count of how many codes starts with crntCode.
 * @param crntCode huffman code whose associated character needs to be returned.
 * @return non-negative number repsenting how many codes out of all the codes start
 * with crntCode.
 */
private fun codeInterpreter(crntCode: String): Int {

    var freq = 0

    for (c in codeMappingInvr.keys) {
        if (c.startsWith(crntCode)) freq++
    }

    return freq

}

/**
 * Converts ascii text to huffman coding.
 * @param asciiText ascii text from the file.
 * @return huffman binary stream of the asciiText.
 */
fun encodedText(asciiText: String): String {

    symbolCodes(makeHuffmanTree(asciiText), "")

    val encodedText = StringBuilder()

    for (x in asciiText)
        encodedText.append(codeMapping[x.toString()])

    return encodedText.toString()

}

/**
 * Create prefix unique codes for every character present in the file. Stores all
 * the characters and frequencies in codeMapping.
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

        val l: HuffmanNode = symbPQ.remove()
        val r: HuffmanNode = symbPQ.remove()

        val hmNode = HuffmanNode(Symbol(l.symbol!!.symbol + r.symbol!!.symbol, l.symbol!!.freq + r.symbol!!.freq), l, r)

        symbPQ.add(hmNode)

    }

    return symbPQ.remove()

}


/**
 * Prepares a priority queue of all the symbol in ascending order of their
 * frequencies
 * @param asciiText ascii text from the file.
 * @return priority queue with all the symbols in it.
 */
private fun symbolPriorityQueue(asciiText: String): PriorityQueue<HuffmanNode> {

    val symbPQ = PriorityQueue<HuffmanNode>()

    val symbFreq = symbolFrequency(asciiText)

    for (x in symbFreq.entries) {
        symbPQ.add(HuffmanNode(Symbol(x.key.toString(), x.value)))
    }

    return symbPQ

}

/**
 * Returns the frequency of every symbol present in the file.
 * @param asciiText ascii text from the file.
 * @return map of count for each ascii character..
 */
private fun symbolFrequency(asciiText: String): Map<Char, Int> {

    val symbFreq = mutableMapOf<Char, Int>()

    for (x in asciiText) {
        if (!symbFreq.containsKey(x)) {
            symbFreq[x] = 1
        } else {
            symbFreq[x] = symbFreq.getValue(x) + 1
        }
    }

    return symbFreq

}