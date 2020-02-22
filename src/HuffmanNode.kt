class HuffmanNode(var symbol: Symbol? = null, var leftChild: HuffmanNode? = null, var rightChild: HuffmanNode? = null) : Comparable<HuffmanNode> {

    override fun compareTo(other: HuffmanNode): Int {
        return symbol!!.compareTo(other.symbol!!)
    }

    override fun toString(): String {
        return "{$symbol $leftChild $rightChild}"
    }
}