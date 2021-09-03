package com.example.supplement_tracker

import android.app.Dialog
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import com.example.supplement_tracker.databinding.ActivitySearchWebviewBinding
import android.os.Bundle
import android.os.Message
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar

class search_webview : AppCompatActivity() {

    val binding by lazy { ActivitySearchWebviewBinding.inflate(layoutInflater) }
    var realURI: Uri? = null
    lateinit var webView: WebView
    lateinit var mProgressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        /* Uri 받기 */
        realURI = intent.getParcelableExtra("photoURI")
        webView = binding.SearchWebView
        mProgressBar = binding.WebviewProgressbar

    }
}