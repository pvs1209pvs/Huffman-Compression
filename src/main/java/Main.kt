package main.java

import java.io.File
import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun main() {

    val charPool = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    fun randomString() = (1..100000)
        .map { Random.nextInt(0, charPool.size) }
        .map(charPool::get)
        .joinToString("")

    val ranStr = randomString()

    File("plain.txt").bufferedWriter().write(convertStringToBinary(ranStr))

    val encodingTime = measureTimeMillis {
        encodedText(ranStr)
    }
    println("Encoding time $encodingTime")

    val decodingTime = measureTimeMillis {
        decodeText()
    }
    println("Decoding time $decodingTime")


}

fun convertStringToBinary(input: String): String {
    val result = StringBuilder()

    input.toCharArray().forEach {
        result.append(
            String.format("%8s", Integer.toBinaryString(it.toInt())).replace(" ".toRegex(), "0") // zero pads
        )
    }
    return result.toString()
}