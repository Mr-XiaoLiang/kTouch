package com.lollipop.ktouch.faiz

import com.lollipop.ktouch.base.PagerActivity
import com.lollipop.ktouch.base.SubPager

class FaizMenuActivity : PagerActivity() {

    override val pageArray: Array<Class<out SubPager>> = arrayOf(
        FaizMenuSubPage.Faiz::class.java,
        FaizMenuSubPage.Kaixa::class.java,
        FaizMenuSubPage.Delta::class.java,
        FaizMenuSubPage.Psyga::class.java,
        FaizMenuSubPage.Orga::class.java,
    )


}