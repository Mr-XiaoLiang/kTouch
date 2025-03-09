package com.lollipop.ktouch

import com.lollipop.ktouch.base.PagerActivity
import com.lollipop.ktouch.base.SubPager
import com.lollipop.ktouch.heisei.DcdHeisei10SubPage
import com.lollipop.ktouch.heisei.DcdHeisei21SubPage

class DcdHeiseiActivity : PagerActivity() {
    override val pageArray: Array<Class<out SubPager>> by lazy {
        arrayOf(
            DcdHeisei10SubPage::class.java,
            DcdHeisei21SubPage::class.java
        )
    }



}