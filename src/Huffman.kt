import java.util.*

val input: String = "abcaabcd"

fun makeHuffmanTree() : HuffmanNode{

    val symbPQ = symbolPriorityQueue()

    while (symbPQ.size>1) {

        val r: HuffmanNode = symbPQ.remove()
        val l: HuffmanNode = symbPQ.remove()
        val hmNode = HuffmanNode(Symbol(l.symbol!!.symbol+r.symbol!!.symbol, l.symbol!!.freq+r.symbol!!.freq), r, l)

        symbPQ.add(hmNode)

    }

    return symbPQ.remove()

}


/**
 * Returns the priority queue of with every symbol in it.
 */
fun symbolPriorityQueue(): PriorityQueue<HuffmanNode> {

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

