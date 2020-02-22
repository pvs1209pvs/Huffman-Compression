import java.math.BigInteger

class Symbol(val symbol: String, val freq: BigInteger) : Comparable<Symbol> {

    override fun compareTo(other: Symbol): Int {
        return freq.compareTo(other.freq)
    }

    override fun toString():String{
        return "$symbol,$freq"
    }

}