package com.example.test_sign;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
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
                "    <meta charset=\"UTF-8\">\n" +
                "    <link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/gh/openlayers/openlayers.github.io@master/en/v6.9.0/css/ol.css\" type=\"text/css\">\n" +
                "    <style>\n" +
                "      .map {\n" +
                "        height: 45em;\n" +
                "        width: 100%;\n" +
                "      }\n" +
                "    </style>\n" +
                "    <script src=\"https://cdn.jsdelivr.net/gh/openlayers/openlayers.github.io@master/en/v6.9.0/build/ol.js\"></script>\n" +
                "    <title>OpenLayers example</title>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <h2>My Map</h2>\n" +
                "    <div id=\"map\" class=\"map\"></div>\n" +
                "\n" +
                "    <script type=\"text/javascript\">\n" +
                "\n" +
                "        const view = new ol.View({\n" +
                "            center : ol.proj.fromLonLat([1.909251, 47.902964]),\n" +
                "            zoom: 12,\n" +
                "        });\n" +
                "\n" +
                "      const map = new ol.Map({\n" +
                "        layers: [\n" +
                "          new ol.layer.Tile({\n" +
                "            source: new ol.source.OSM()\n" +
                "          }),\n" +
                "        ],\n" +
                "        target: 'map',\n" +
                "        view: view,\n" +
                "      });\n" +
                "\n" +
                "      const geolocation = new ol.Geolocation({\n" +
                "        trackingOptions: {\n" +
                "            enableHighAccuracy : true,\n" +
                "        },\n" +
                "        projection: view.getProjection(),\n" +
                "        tracking: true,\n" +
                "      });\n" +
                "\n" +
                "      const accuracyFeature = new ol.Feature();\n" +
                "      geolocation.on('change:accuracyGeometry', function (){\n" +
                "          accuracyFeature.setGeometry(geolocation.getAccuracyGeometry());\n" +
                "      });\n" +
                "\n" +
                "      const positionFeature = new ol.Feature();\n" +
                "      positionFeature.setStyle(\n" +
                "          new ol.style.Style({\n" +
                "              image: new ol.style.Circle({\n" +
                "                  radius : 6,\n" +
                "                  fill: new ol.style.Fill({\n" +
                "                      color: '#3399CC',\n" +
                "                  }),\n" +
                "                  stroke: new ol.style.Stroke({\n" +
                "                      color: '#fff',\n" +
                "                      width: 10,\n" +
                "                  }),\n" +
                "              }),\n" +
                "          })\n" +
                "      );\n" +
                "\n" +
                "      geolocation.on('change:position', function(event){\n" +
                "        const coordinates = geolocation.getPosition();\n" +
                "        console.log('bonjour')\n" +
                "        console.log('coordinates : '+coordinates)\n" +
                "        positionFeature.setGeometry(coordinates ? new ol.geom.Point(coordinates) : null);\n" +
                "      });\n" +
                "\n" +
                "      new ol.layer.Vector({\n" +
                "        map: map,\n" +
                "        source: new ol.source.Vector({\n" +
                "        features: [accuracyFeature, positionFeature],\n" +
                "        }),\n" +
                "      });\n" +
                "    </script>\n" +
                "  </body>\n" +
                "</html>";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webView);

        WebSettings settings = webView.getSettings();

        webView.setWebChromeClient(new WebChromeClient() {
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }
        });


        /*settings.setBuiltInZoomControls(true);*/
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);
        /*settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAppCacheEnabled(true);*/
        settings.setGeolocationEnabled(true);
        settings.setAppCachePath(getApplicationContext().getCacheDir().getPath());
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.setWebViewClient(new WebViewClient());
        webView.loadDataWithBaseURL("https://perdu.com", htmlString, "text/html", "UTF-8", null);
    }
}