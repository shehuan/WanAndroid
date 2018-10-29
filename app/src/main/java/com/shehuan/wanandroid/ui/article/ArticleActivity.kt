package com.shehuan.wanandroid.ui.article

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.base.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_article.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import android.content.ClipData
import android.content.ClipboardManager
import android.net.Uri
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.shehuan.wanandroid.utils.ToastUtil


class ArticleActivity : BaseActivity() {
    private lateinit var title: String
    private lateinit var link: String

    companion object {
        fun start(context: Context, title: String, link: String) {
            val intent = Intent(context, ArticleActivity::class.java)
            intent.apply {
                putExtra("title", title)
                putExtra("link", link)
            }
            context.startActivity(intent)
        }
    }

    override fun initLayoutResID(): Int {
        return R.layout.activity_article
    }

    override fun initData() {
        intent?.let {
            title = it.getStringExtra("title")
            link = it.getStringExtra("link")
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun initView() {
        toolbar.title = title
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        articleWebView.requestFocusFromTouch()
        val webSettings = articleWebView.settings
        webSettings.javaScriptEnabled = true
        webSettings.setSupportZoom(true)
        webSettings.builtInZoomControls = true
        webSettings.displayZoomControls = false
        articleWebView.webViewClient = object : WebViewClient(){}
        articleWebView.loadUrl(link)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        setIconsVisible(menu!!, true)
        menuInflater.inflate(R.menu.article_menu_layout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_share -> share()
            R.id.menu_copy -> copy()
            R.id.menu_browser -> browser()
        }
        return true
    }

    private fun browser() {
        val intent = Intent()
        intent.apply {
            data = Uri.parse(link)
            action = Intent.ACTION_VIEW
        }
        startActivity(intent)
    }

    private fun copy() {
        val manager = mContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        manager.primaryClip = ClipData.newPlainText(null, link)
        ToastUtil.showToast(mContext, "复制成功")
    }

    private fun share() {
        val intent = Intent()
        intent.apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "$title\n$link")
            type = "text/plain"
        }
        startActivity(Intent.createChooser(intent, "分享到"))
    }

    private fun setIconsVisible(menu: Menu, flag: Boolean) {
        try {
            val method = menu.javaClass.getDeclaredMethod("setOptionalIconsVisible", java.lang.Boolean.TYPE)
            method.isAccessible = true
            method.invoke(menu, flag)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
