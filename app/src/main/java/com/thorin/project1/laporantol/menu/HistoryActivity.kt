package com.thorin.project1.laporantol.menu

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AlertDialog
import com.thorin.project1.laporantol.DashboardActivity
import com.thorin.project1.laporantol.R
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        val actionbar = supportActionBar
        actionbar?.title= "History Laporan"
        actionbar?.setDisplayHomeAsUpEnabled(true)
        webView.settings.javaScriptEnabled = true

        webView.webViewClient = object : WebViewClient() {


            override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
                try {
                    webView.stopLoading()
                } catch (e: Exception) {
                }
                try {
                    webView.clearView()
                } catch (e: Exception) {
                }
                if (webView.canGoBack()) {
                    webView.goBack()
                }
                webView.loadUrl("about:blank")
                val alertDialog = AlertDialog.Builder(this@HistoryActivity).create()
                alertDialog.setTitle("Gagal Membuka")
                alertDialog.setMessage("""
                    Pastikan android anda terhubung ke internet lalu coba lagi !      
                """.trimIndent())
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ok", DialogInterface.OnClickListener { _: DialogInterface, _: Int ->
                    moveBack()
                })
                alertDialog.show()
                super.onReceivedError(webView, errorCode, description, failingUrl)
            }

        }

        webView.webChromeClient = WebChromeClient()
        if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState)
        } else {
            webView.loadUrl("https://databaseprojectlaportol.000webhostapp.com/tablelaporan.php")
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        webView.saveState(outState) // output would be a WebBackForwardList
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.action === KeyEvent.ACTION_DOWN) {
            when (keyCode) {
                KeyEvent.KEYCODE_BACK -> {
                    if (webView.canGoBack()) {
                        webView.goBack()
                    } else {
                        finish()
                    }
                    return true
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }
    fun moveBack() {
        val moveIntent = Intent(this, DashboardActivity::class.java)
        startActivity(moveIntent)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}