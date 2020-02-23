class FileEmulator(var userText: String = "") {

    /**
     * Converts binary stream into ascii text.
     * @param binText binary stream to be converted into ascii.
     * @param isEncoded whether it's a huffman binary stream or just a simple binary
     * stream. true is returned if it's huffman binary stream and false if it's a
     * simple binary stream.
     * @return ascii text converted from the binary stream.
     */
    fun binaryToAscii(binText: String, isEncoded: Boolean): String {
        return if (isEncoded) huffBinToAscii(binText)
        else binToAscii(binText)
    }

    /**
     * Converts huffman binary stream to ascii text.
     * @param binText huffman binary binary to be converted into ascii text.
     * @return ascii text converted from huffman binary stream.
     */
    private fun huffBinToAscii(binText: String): String {
        return decodeText(binText)
    }

    /**
     * Converts binary stream to ascii text.
     * @param binText binary stream to be converted to ascii text.
     * @return ascii text converted from binary stream.
     */
    private fun binToAscii(binText: String): String {

        val ascii = StringBuilder()
        val b = StringBuilder()

        for (x in binText) {
            b.append(x)
            if (b.length == 7) {
                ascii.append(Integer.parseInt(b.toString(), 2).toChar())
                b.clear()
            }
        }

        return ascii.toString()

    }

    /**
     * Binary representation of ascii text.
     * @param isEncoded represents which type binary stream to be returned. true
     * for huffman binary stream and false for simple binary stream.
     * @return the binary form of the user text.
     */
    fun asciiToBinStream(isEncoded: Boolean): String {
        return if (isEncoded) {
            asciiToHuffBin()
        } else {
            asciiToBin()
        }
    }

    /**
     * Converts ascii character into huffman binary stream.
     * @return huffman binary stream converted of ascii text.
     */
    private fun asciiToHuffBin(): String {
        return encodedText(userText)
    }

    /**
     * Converts ascii text into simple binary stream.
     * @return binary stream of ascii text.
     */
    private fun asciiToBin(): String {

        val text = StringBuilder()

        for (x in userText) {

            val v = Integer.toBinaryString(x.toByte().toInt()).toString()
            var zeros = ""

            for (i in v.length until 7) {
                zeros += "0"
            }

            text.append(zeros + v)

        }

        return text.toString()

    }

}