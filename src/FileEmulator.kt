
class FileEmulator(var userText: String = "") {

    /**
     * converts huffman binary codes into ascii
     */
    fun binaryToAscii(binText: String, isEncoded: Boolean): String {
        return if (isEncoded) huffBinToAscii(binText)
        else binToAscii(binText)
    }

    private fun huffBinToAscii(binText:String):String{
        return decodeText(binText)
    }

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

    /* --- */

    /**
     * binary representation of user text.
     */
    fun buildBinText(isEncoded: Boolean): String {
        return if (isEncoded) {
            asciiToHuffBin()
        } else {
            asciiToBin()
        }
    }

    /**
     * converts ascii character into huffman binary codes.
     */
    private fun asciiToHuffBin(): String {
        return encodedText(userText)
    }

    /**
     * converts ascii text into simple universal binary text.
     */
    private fun asciiToBin(): String {

        val text = StringBuilder()

        for (x in userText) {

            val v = Integer.toBinaryString(x.toByte().toInt()).toString()
            var zeros= ""

            for (i in  v.length until 7){
                zeros += "0"
            }

            text.append(zeros+v)

        }

        return text.toString()

    }


}