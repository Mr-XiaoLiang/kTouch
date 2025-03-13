package com.lollipop.ktouch.base

interface RiderIconGroup {

    val playerIconCount: Int

    fun selectOnly(index: Int)

    fun selectTo(index: Int)

    fun selectFrom(index: Int)

    fun setSelect(index: Int, isSelected: Boolean)

}