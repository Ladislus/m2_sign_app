package com.example.test_sign;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String htmlString = "<!doctype html>\n" +
                "<html lang=\"fr\">\n" +
                "  <head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/gh/openlayers/openlayers.github.io@master/en/v6.9.0/css/ol.css\" type=\"text/css\">\n" +
                "    <style>\n" +
                "      .map {\n" +
                "        height: 750px;\n" +
                "        width: 100%;\n" +
                "      }\n" +
                "    </style>\n" +
                "    <script src=\"https://cdn.jsdelivr.net/gh/openlayers/openlayers.github.io@master/en/v6.9.0/build/ol.js\"></script>\n" +
                "    <title>OpenLayers example</title>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <h2>My Map</h2>\n" +
                "    <div id=\"map\" class=\"map\"></div>\n" +
                "    <script type=\"text/javascript\">\n" +
                "      var map = new ol.Map({\n" +
                "        target: 'map',\n" +
                "        layers: [\n" +
                "          new ol.layer.Tile({\n" +
                "            source: new ol.source.OSM()\n" +
                "          })\n" +
                "        ],\n" +
                "        view: new ol.View({\n" +
                "          center: ol.proj.fromLonLat([1.909251, 47.902964]),\n" +
                "          zoom: 13\n" +
                "        })\n" +
                "      });\n" +
                "    </script>\n" +
                "  </body>\n" +
                "</html>";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webView);

        WebSettings settings = webView.getSettings();

        settings.setBuiltInZoomControls(true);
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setAppCachePath(getApplicationContext().getCacheDir().getPath());
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.setWebViewClient(new WebViewClient());
        webView.loadDataWithBaseURL("", htmlString, "text/html", "UTF-8", null);
    }
}