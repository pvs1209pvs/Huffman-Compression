package test.java

import main.java.Huffman

import org.junit.Assert
import org.junit.Test

class HuffmanTest {

    @Test
    fun `plain text encoded`() {

        val plainTexts = listOf("~!@#\$%^&*()_+{}|:\"<>?[];'./\\", "hello world")

        plainTexts.forEach {
            val huffman = Huffman(it)
            Assert.assertEquals(it, huffman.decodedText)
        }

    }
}