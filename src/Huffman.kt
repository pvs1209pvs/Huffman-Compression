import java.util.*

private var codeMapping = mutableMapOf<String, Int>()
private var codeMappingInvrs = mutableMapOf<String,String>()
private var numCodes = 0


fun decodeText(encodedText:String):String{

    val ascii = StringBuilder()
    val decoded = StringBuilder()

    for (x in encodedText){

        decoded.append(x)

        if(codeMappingInvrs[decoded.toString()]!=null){
            ascii.append(codeMappingInvrs[decoded.toString()])
            decoded.clear()
        }

    }

    return ascii.toString()

}

fun encodedText(text:String):String{

    symbolCodes(makeHuffmanTree(text),StringBuilder())

    val encodedText = StringBuilder()
    for (x in text)
        encodedText.append(codeMapping[x.toString()])
    return encodedText.toString()

}

private fun symbolCodes(huffmanTree: HuffmanNode, code: StringBuilder) {

    if (huffmanTree.leftChild != null) {
        symbolCodes(huffmanTree.leftChild!!, code)
    }

    if (huffmanTree.leftChild == null && huffmanTree.rightChild == null) {
        if (codeMapping.size == numCodes - 1) {
            val symb:String = huffmanTree.symbol!!.symbol
            val c:Int = "$code".toInt()
            codeMapping[symb] = c
            codeMappingInvrs[c.toString()] = symb
        }
        else {
            val symb:String = huffmanTree.symbol!!.symbol
            val c:Int = "${code}0".toInt()
            codeMapping[symb] = c
            codeMappingInvrs[c.toString()] = symb
        }
    }

    if (huffmanTree.rightChild != null) {
        code.append("1")
        symbolCodes(huffmanTree.rightChild!!, code)
    }

}

/**
 * Returns the final tree which is the last element left in the priority queue.
 */
private fun makeHuffmanTree(input: String): HuffmanNode {

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
private fun symbolFrequency(input: String): Map<Char, Int> {

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