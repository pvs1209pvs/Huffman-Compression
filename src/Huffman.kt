import java.math.BigInteger
import java.util.*

var codeMapping = mutableMapOf<String, BigInteger>()
private var codeMappingInvrs = mutableMapOf<String, String>()
private var numCodes = 0


fun decodeText(encodedText: String): String {

    val ascii = StringBuilder()
    val decoded = StringBuilder()

    for (x in encodedText) {

        decoded.append(x)

        if (codeMappingInvrs[decoded.toString()] != null) {
            ascii.append(codeMappingInvrs[decoded.toString()])
            decoded.clear()
        }

    }

    return ascii.toString()

}

fun encodedText(text: String): String {

    symbolCodes(makeHuffmanTree(text), "")

    val encodedText = StringBuilder()
    for (x in text)
        encodedText.append(codeMapping[x.toString()])
    return encodedText.toString()

}

fun symbolCodes(huffmanTree: HuffmanNode, code: String) {

    var myCode = code

    if (huffmanTree.leftChild != null) {
        myCode+="0"
        symbolCodes(huffmanTree.leftChild!!, myCode)
    }


    if (huffmanTree.leftChild == null && huffmanTree.rightChild == null) {
        codeMapping[huffmanTree.symbol!!.symbol] = BigInteger(myCode.toInt().toString())
        codeMappingInvrs[myCode.toInt().toString()] = huffmanTree.symbol!!.symbol
        // println("${huffmanTree.symbol!!.symbol} ${myCode.toInt()}")
    }

    /* cut the zero added by left branch */
    myCode = (myCode.substring(0,myCode.length-1))

    if (huffmanTree.rightChild != null) {
        myCode+="1"
        symbolCodes(huffmanTree.rightChild!!, myCode)
    }

}

/**
 * Returns the final tree which is the last element left in the priority queue.
 */
fun makeHuffmanTree(input: String): HuffmanNode {

    val symbPQ = symbolPriorityQueue(input)
    numCodes = symbPQ.size



    while (symbPQ.size > 1) {

        val l: HuffmanNode = symbPQ.remove()
        val r: HuffmanNode = symbPQ.remove()

        val hmNode = HuffmanNode(Symbol(l.symbol!!.symbol + r.symbol!!.symbol, l.symbol!!.freq + r.symbol!!.freq), l,r)

        symbPQ.add(hmNode)

    }


    return symbPQ.remove()

}


/**
 * Returns the priority queue of with every symbol in it.
 */
private fun symbolPriorityQueue(input: String): PriorityQueue<HuffmanNode> {

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
private fun symbolFrequency(input: String): Map<Char, BigInteger> {

    val symbFreq = mutableMapOf<Char, BigInteger>()

    for (x in input) {
        if (!symbFreq.containsKey(x)) {
            symbFreq[x] = BigInteger("1")
        } else {
            symbFreq[x] = symbFreq.getValue(x) + BigInteger("1")
        }
    }


    return symbFreq

}