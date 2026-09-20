package com.example.ui.components

import android.annotation.SuppressLint
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun LeafletMapView(
    modifier: Modifier = Modifier.fillMaxSize(),
    onLocationSelected: (lat: Double, lng: Double) -> Unit
) {
    val context = LocalContext.current
    
    // JS interface to bridge Javascript to Kotlin
    class WebAppInterface {
        @JavascriptInterface
        fun onLocationSelected(lat: Double, lng: Double) {
            onLocationSelected(lat, lng)
        }
    }

    val webView = remember {
        WebView(context).apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            
            webViewClient = WebViewClient()
            webChromeClient = WebChromeClient()
            
            // Add the bridge named "AndroidInterface"
            addJavascriptInterface(WebAppInterface(), "AndroidInterface")
            
            // Load the local HTML file from assets
            loadUrl("file:///android_asset/leaflet_map.html")
        }
    }

    AndroidView(
        factory = { webView },
        modifier = modifier
    )
}
