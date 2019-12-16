package me.spryn.noded.screens.wikipedia


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import me.spryn.noded.MainActivity
import me.spryn.noded.R
import me.spryn.noded.databinding.FragmentWikipediaBinding
import me.spryn.noded.ui.updateToolbar


/**
 * A simple [Fragment] subclass.
 */
class wikipediaFragment : Fragment() {
    lateinit var binding: FragmentWikipediaBinding
    private val args: wikipediaFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_wikipedia, container, false
        )

        loadWiki()

        return binding.root
    }

    private fun loadWiki(){
        var title = args.title
        title = title.replace(" ", "_")
        var url = ("https://en.wikipedia.org/wiki/${title}")

        var webView = binding.webviewWikipedia
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.loadUrl(url)
    }

    override fun onResume() {
        super.onResume()
        val mainActivity = activity as? MainActivity
        mainActivity?.let {
            val primaryColor = ContextCompat.getColor(it, R.color.colorPrimary)
            updateToolbar(
                mainActivity = it,
                toolbarColor = primaryColor,
                statusBarColor = primaryColor,
                toolbarElevation = 0F

            )
        }
    }
}
