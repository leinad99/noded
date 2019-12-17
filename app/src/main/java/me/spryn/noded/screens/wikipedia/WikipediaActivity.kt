package me.spryn.noded.screens.wikipedia

import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import me.spryn.noded.R

class WikipediaActivity : AppCompatActivity() {

    var title = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wikipedia)

        loadWiki()

        supportActionBar?.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun loadWiki() {
        val bundle = intent.extras?.getString("title")
        title = bundle?.replace(" ", "_") ?: "Wikipedia"

        val url = ("https://en.wikipedia.org/wiki/${title}")

        val webView = findViewById<WebView>(R.id.webview_wikipedia)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.loadUrl(url)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

}
