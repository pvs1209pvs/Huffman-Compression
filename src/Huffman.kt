import java.util.*

var codeMapping = mutableMapOf<String, Int>()
var numCodes = 0

fun symbolCodes(huffmanTree: HuffmanNode, code: StringBuilder) {

    if (huffmanTree.leftChild != null) {
        symbolCodes(huffmanTree.leftChild!!, code)
    }

    if (huffmanTree.leftChild == null && huffmanTree.rightChild == null) {
        if (codeMapping.size == numCodes - 1)
            codeMapping[huffmanTree.symbol!!.symbol] = "$code".toInt()
        else
            codeMapping[huffmanTree.symbol!!.symbol] = "${code}0".toInt()
    }

    if (huffmanTree.rightChild != null) {
        code.append("1")
        symbolCodes(huffmanTree.rightChild!!, code)
    }

}

/**
 * Returns the final tree which is the last element left in the priority queue.
 */
fun makeHuffmanTree(input: String): HuffmanNode {

    val symbPQ = symbolPriorityQueue(input)
    numCodes = symbPQ.size

    while (symbPQ.size > 1) {

        val r: HuffmanNode = symbPQ.remove()
        val l: HuffmanNode = symbPQ.remove()
        val hmNode = HuffmanNode(Symbol(l.symbol!!.symbol + r.symbol!!.symbol, l.symbol!!.freq + r.symbol!!.freq), r, l)

        symbPQ.add(hmNode)

    }

    return symbPQ.remove()

}


/**
 * Returns the priority queue of with every symbol in it.
 */
fun symbolPriorityQueue(input: String): PriorityQueue<HuffmanNode> {

    val symbPQ = PriorityQueue<HuffmanNode>()

    val symbFreq = symbolFrequency(input)

    for (x in symbFreq.entries) {
        symbPQ.add(HuffmanNode(Symbol(x.key.toString(), x.value)))
    }

    return symbPQ

}

/**
 * Returns the fequency of every symbol.
 * @param input counts the frequency of every symbol in this text
 */
fun symbolFrequency(input: String): Map<Char, Int> {

    val symbFreq = mutableMapOf<Char, Int>()

    for (x in input) {
        if (!symbFreq.containsKey(x)) {
            symbFreq[x] = 1
        } else {
            symbFreq[x] = symbFreq.getValue(x) + 1
        }
    }


    return symbFreq

}