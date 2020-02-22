class BackgroundFile(var userText: String = "") {

    fun buildBackgroundText(isEncoded: Boolean) :String{
        return if (isEncoded) {
            buildEncodedText(codeMapping)
        }
        else{
            buildRegularText()
        }
    }

   private fun buildEncodedText(codes: Map<String, Int>): String {
        val text = StringBuilder()
        for (x in userText)
            text.append(codes[x.toString()])
        return text.toString()
    }

    private fun buildRegularText(): String {
        val text = StringBuilder()
        for (x in userText)
            text.append((Integer.toBinaryString(x.toByte().toInt())))
        return text.toString()
    }


}