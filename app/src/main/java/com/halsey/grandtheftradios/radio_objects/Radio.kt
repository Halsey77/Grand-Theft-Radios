package com.halsey.grandtheftradios.radio_objects

class Radio(
    @JvmField val game: String,
    @JvmField val name: String,
    @JvmField val url: String,
    @JvmField val iconDrawable: Int
) {

    val fileName: String
        get() = "$game-$name.mp3"
}
