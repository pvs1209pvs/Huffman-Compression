import java.util.*

var codeMapping = mutableMapOf<String, Int>()
var codeMappingInvrs = mutableMapOf<String, String>()


fun decodeText(encodedText: String): String {

    //{0=a, 10=r, 110=p, 111=m}

    println(codeMappingInvrs)
    println(encodedText)

    val ascii = StringBuilder()
    val d = StringBuilder()


    for (element in encodedText) {

        d.append(element)

        println(d)

        if(codeInterpreter(d.toString())==1){
            ascii.append(codeMappingInvrs[d.toString()])
            d.clear()
        }

    }



    return ascii.toString()

}

private fun codeInterpreter(crntCode: String): Int {

    var freq: Int = 0

    for (c in codeMappingInvrs.keys) {
        if (c.startsWith(crntCode)) freq++
    }

    return freq

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
        myCode += "0"
        symbolCodes(huffmanTree.leftChild!!, myCode)
    }


    if (huffmanTree.leftChild == null && huffmanTree.rightChild == null) {
        codeMapping[huffmanTree.symbol!!.symbol] = myCode.toInt()
        codeMappingInvrs[myCode.toInt().toString()] = huffmanTree.symbol!!.symbol
    }

    /* cut the zero added by left branch */
    myCode = (myCode.substring(0, myCode.length - 1))

    if (huffmanTree.rightChild != null) {
        myCode += "1"
        symbolCodes(huffmanTree.rightChild!!, myCode)
    }

}

/**
 * Returns the final tree which is the last element left in the priority queue.
 */
fun makeHuffmanTree(input: String): HuffmanNode {

    val symbPQ = symbolPriorityQueue(input)

    while (symbPQ.size > 1) {

        val l: HuffmanNode = symbPQ.remove()
        val r: HuffmanNode = symbPQ.remove()

        val hmNode = HuffmanNode(Symbol(l.symbol!!.symbol + r.symbol!!.symbol, l.symbol!!.freq + r.symbol!!.freq), l, r)

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