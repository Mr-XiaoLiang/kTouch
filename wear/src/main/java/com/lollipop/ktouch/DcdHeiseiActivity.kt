package com.lollipop.ktouch

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.lollipop.ktouch.base.SubPager
import com.lollipop.ktouch.heisei.DcdHeisei10SubPage
import com.lollipop.ktouch.heisei.DcdHeisei21SubPage

class DcdHeiseiActivity : AppCompatActivity() {

    companion object {
        private const val PARAMS_SUB_PAGE = "subPage"
        fun start(context: Context, subPage: SubPage) {
            context.startActivity(
                Intent(context, DcdHeiseiActivity::class.java).apply {
                    putExtra(PARAMS_SUB_PAGE, subPage.name)
                }
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fragmentGroup = FrameLayout(this)
        fragmentGroup.id = View.generateViewId()
        fragmentGroup.setBackgroundColor(Color.BLACK)
        setContentView(fragmentGroup)
        supportFragmentManager.beginTransaction()
            .add(fragmentGroup.id, findSubPage(), null)
            .commit()
    }

    private fun findSubPage(): Class<out SubPager> {
        val subPage = intent.getStringExtra(PARAMS_SUB_PAGE)
        if (TextUtils.isEmpty(subPage)) {
            return SubPage.FINALLY.fragment
        }
        val page = SubPage.entries.find { it.name == subPage } ?: SubPage.FINALLY
        return page.fragment
    }

    enum class SubPage(val fragment: Class<out SubPager>) {
        FINALLY(DcdHeisei10SubPage::class.java),
        FINALLY21(DcdHeisei21SubPage::class.java)
    }

}