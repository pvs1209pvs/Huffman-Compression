package test.java

import main.java.Huffman
import org.junit.Assert
import org.junit.Test
import java.io.File
import kotlin.random.Random

class HuffmanTest {

    @Test
    fun `check if encoded string is same as decoded string`() {

        val charPool = ('a'..'z') + ('A'..'Z') + ('0'..'9')

        val randomString = (1..50000)
            .map { Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")

        val encryptedFilePath = "src/test/resources/output.txt"

        Huffman.encodeText(randomString, encryptedFilePath)

        Assert.assertEquals(randomString, Huffman.decodeText(encryptedFilePath))

    }
}