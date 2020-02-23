import java.util.*

var codeMapping = mutableMapOf<String, String>()
var codeMappingInvr = mutableMapOf<String, String>()

/**
 * converts the continuous stream of huffman code into ascii text.
 * @param encodedText continuous stream of huffman encoded ascii text.
 * @return the original ascii text.
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
 * @param crntCode is code whose associated text needs to be returned.
 * @return count of how many codes starts with crntCode. If the return value is greater than
 * one then this is not the unique code and searched needs to continued.
 */
private fun codeInterpreter(crntCode: String): Int {

    var freq = 0

    for (c in codeMappingInvr.keys) {
        if (c.startsWith(crntCode)) freq++
    }

    return freq

}

/**
 * Converts ascii text into huffman coding.
 * @param asciiText is the text from the file.
 * @return continuous huffman code representation of the asciiText.
 */
fun encodedText(asciiText: String): String {

    symbolCodes(makeHuffmanTree(asciiText), "")

    val encodedText = StringBuilder()

    for (x in asciiText)
        encodedText.append(codeMapping[x.toString()])

    return encodedText.toString()

}

/**
 * @param huffmanTree is the final huffman tree.
 * @param code huffman codes for each symbol build while traversing the tree.
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
 * @param asciiText is the text from the file.
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
 * @param asciiText is the text from the file.
 * @return the priority queue of with every symbol in it.
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
 * @return map of count for each ascii symbol.
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