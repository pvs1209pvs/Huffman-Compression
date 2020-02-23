class Symbol(val symbol: String, val freq: Int) : Comparable<Symbol> {

    override fun compareTo(other: Symbol): Int {
        return freq.compareTo(other.freq)
    }

    override fun toString():String{
        return "$symbol,$freq"
    }

}