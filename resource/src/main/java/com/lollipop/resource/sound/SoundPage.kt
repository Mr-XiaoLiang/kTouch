package com.lollipop.resource.sound

interface SoundPage {

    fun stopAll()

}

interface SoundPageParent : SoundPage {

    fun addSubPage(subPage: SoundPage)

    fun removeSubPage(subPage: SoundPage)

}
