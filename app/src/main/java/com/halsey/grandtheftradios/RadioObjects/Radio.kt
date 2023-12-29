package com.halsey.grandtheftradios.RadioObjects

class Radio(private val game: String, @JvmField val name: String, @JvmField val url: String) {

    val fileName: String
        get() = game + "-" + name + ".mp3"
}
