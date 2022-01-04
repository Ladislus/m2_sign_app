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
                "    <head>\n" +
                "        <meta charset=\"UTF-8\">\n" +
                "        <link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/gh/openlayers/openlayers.github.io@master/en/v6.9.0/css/ol.css\" type=\"text/css\">\n" +
                "        <style>\n" +
                "            .map {\n" +
                "                height: 25em;\n" +
                "                width: 100%;\n" +
                "            }\n" +
                "            .dot {\n" +
                "                height: 10px;\n" +
                "                width: 10px;\n" +
                "                background-color: #bbb;\n" +
                "                border-radius: 50%;\n" +
                "                display: inline-block;\n" +
                "            }\n" +
                "        </style>\n" +
                "        <script src=\"https://cdn.jsdelivr.net/gh/openlayers/openlayers.github.io@master/en/v6.9.0/build/ol.js\"></script>\n" +
                "        <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>\n" +
                "        <title>OpenLayers example</title>\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <div id=\"map\" class=\"map\"></div>\n" +
                "        <div>\n" +
                "            <input type=\"checkbox\" id=\"velo\" name=\"velo\" checked>\n" +
                "            <label for=\"velo\">Vélo</label>\n" +
                "            <input type=\"checkbox\" id=\"tramBus\" name=\"tramBus\" checked>\n" +
                "            <label for=\"tramBus\">Tram/Bus</label>\n" +
                "        </div>\n" +
                "        <form id=\"filter\">\n" +
                "            <div>\n" +
                "                <label for=\"\">Filtre sur les lignes et arrets de bus/trams</label><br>\n" +
                "                <input id=\"input_filter\" type=\"text\">\n" +
                "            </div>\n" +
                "            <div>\n" +
                "                <input id=\"filter_btn\" type=\"button\" value=\"Filter\">\n" +
                "            </div>\n" +
                "        </form>\n" +
                "        <div>\n" +
                "            <div id=\"legendVelo\">\n" +
                "                <table>\n" +
                "                    <thead>\n" +
                "                        <tr>\n" +
                "                            <th>Legende</th>\n" +
                "                            <th>Type de parc</th>\n" +
                "                        </tr>\n" +
                "                    </thead>\n" +
                "                    <tbody id=\"legendVeloTable\">\n" +
                "                    </tbody>\n" +
                "                </table>\n" +
                "            </div>\n" +
                "            <div id=\"legendTramBus\">\n" +
                "                <table>\n" +
                "                    <thead>\n" +
                "                        <tr>\n" +
                "                            <th>Legende</th>\n" +
                "                            <th>Type d'arret</th>\n" +
                "                        </tr>\n" +
                "                    </thead>\n" +
                "                    <tbody id=\"legendTramBusStopTable\">\n" +
                "                    </tbody>\n" +
                "                </table>\n" +
                "                <table>\n" +
                "                    <thead>\n" +
                "                        <tr>\n" +
                "                            <th>Legende</th>\n" +
                "                            <th>Ligne</th>\n" +
                "                        </tr>\n" +
                "                    </thead>\n" +
                "                    <tbody id=\"legendTramBusLigneTable\">\n" +
                "                    </tbody>\n" +
                "                </table>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <script type=\"text/javascript\">\n" +
                "            const IP = '192.168.0.16';\n" +
                "            const PORT = '8600';\n" +
                "            const URL = 'http://' + IP + ':' + PORT + '/geoserver/wms';\n" +
                "\n" +
                "            const WORKSPACE = 'SIGN';\n" +
                "            const STATIONNEMENT_VELO = WORKSPACE + ':stationnementVelo';\n" +
                "            const PISTES_CYCLABLES = WORKSPACE + ':pisteCyclable';\n" +
                "            const ARRETS_TRAM_BUS = WORKSPACE + ':arretsTramBus';\n" +
                "            const LIGNE_BUS = WORKSPACE + ':ligneBus';\n" +
                "            const LIGNE_TRAM = WORKSPACE + ':ligneTram';\n" +
                "\n" +
                "            // ########################## Layers ##########################\n" +
                "\n" +
                "            const layer_tram = new ol.layer.Vector({\n" +
                "                title: 'Lignes Tram',\n" +
                "                source: new ol.source.Vector({\n" +
                "                    url: URL + \"?service=WFS&version=1.1.0&request=GetFeature&typeName=\" + LIGNE_TRAM + \"&outputFormat=application/json\",\n" +
                "                    format: new ol.format.GeoJSON(),\n" +
                "                    serverType: 'geoserver',\n" +
                "                }),\n" +
                "            });\n" +
                "\n" +
                "            const layer_bus = new ol.layer.Vector({\n" +
                "                title: 'Lignes Bus',\n" +
                "                source: new ol.source.Vector({\n" +
                "                    url: URL + \"?service=WFS&version=1.1.0&request=GetFeature&typeName=\" + LIGNE_BUS + \"&outputFormat=application/json\",\n" +
                "                    format: new ol.format.GeoJSON(),\n" +
                "                    serverType: 'geoserver',\n" +
                "                }),\n" +
                "            });\n" +
                "\n" +
                "            const layer_trambus_stop = new ol.layer.Vector({\n" +
                "                title: 'Arrets Tram/Bus',\n" +
                "                source: new ol.source.Vector({\n" +
                "                    url: URL + \"?service=WFS&version=1.1.0&request=GetFeature&typeName=\" + ARRETS_TRAM_BUS + \"&outputFormat=application/json\",\n" +
                "                    format: new ol.format.GeoJSON(),\n" +
                "                    serverType: 'geoserver',\n" +
                "                }),\n" +
                "            });\n" +
                "\n" +
                "            const layer_velo = new ol.layer.Vector({\n" +
                "                title: 'Stationnement Vélo',\n" +
                "                source: new ol.source.Vector({\n" +
                "                    url: URL + \"?service=WFS&version=1.1.0&request=GetFeature&typeName=\" + PISTES_CYCLABLES + \"&outputFormat=application/json\",\n" +
                "                    format: new ol.format.GeoJSON(),\n" +
                "                    serverType: 'geoserver',\n" +
                "                }),\n" +
                "            });\n" +
                "\n" +
                "            const layer_velo_stop = new ol.layer.Vector({\n" +
                "                title: 'Stationnement Vélo',\n" +
                "                source: new ol.source.Vector({\n" +
                "                    url: URL + \"?service=WFS&version=1.1.0&request=GetFeature&typeName=\" + STATIONNEMENT_VELO + \"&outputFormat=application/json\",\n" +
                "                    format: new ol.format.GeoJSON(),\n" +
                "                    serverType: 'geoserver',\n" +
                "                }),\n" +
                "            });\n" +
                "\n" +
                "            // ########################## Style ##########################\n" +
                "\n" +
                "            const styles = {\n" +
                "                tram: {\n" +
                "                    \"A\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#0000FF',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"B\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#00FF00',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    })\n" +
                "                },\n" +
                "                bus: {\n" +
                "                    \"L01\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#FFFF00',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"L02\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#FF00FF',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"L03\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#000000',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"L04\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#6C4DAC',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"L05\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#A7F042',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"L06\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#0A4F43',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"L07\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#CA6751',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"L08\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#82454A',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"L09\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#A6E6DC',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"L10\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#430706',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"L11\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#40254B',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"L12\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#375011',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"L13\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#9D3C1F',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"L15\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#B363C7',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"L16\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#B5D78B',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"L17\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#2D2191',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"L18\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#8F4C72',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"L19\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#FF84B6',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"L20\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#46B050',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"L21\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#F54B73',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"L22\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#D606A9',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"L23\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#C2D0BD',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"L25\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#DC4502',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"L26\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#0F7290',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"L27\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#514B74',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"L28\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#CB54FA',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"L33\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#E8DF9B',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"L34\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#87965D',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"L35\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#8E4928',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"L36\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#06AF72',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"L37\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#0A04AE',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"L60\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#56093B',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"L61\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#C9A976',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"L62\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#1C4352',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"L63\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#ABC598',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"L64\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#D4B504',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"L65\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#26B004',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"L70\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#AE8B9D',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"L71\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#F7F037',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"L72\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#2FB1AA',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"L73\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#8CE01B',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"LA\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#424242',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"LB\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#DD92A6',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"LIC\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#5336F5',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"LL\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#8F25EF',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"LO\": new ol.style.Style({\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#41DAAD',\n" +
                "                            width: 2\n" +
                "                        })\n" +
                "                    })\n" +
                "                },\n" +
                "                trambus_stop: {\n" +
                "                    \"BUS\": new ol.style.Style({\n" +
                "                        image: new ol.style.Circle({\n" +
                "                            fill: new ol.style.Fill({\n" +
                "                                color: '#DB7A98'\n" +
                "                            }),\n" +
                "                            radius: 3\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"TRAM\": new ol.style.Style({\n" +
                "                        image: new ol.style.Circle({\n" +
                "                            fill: new ol.style.Fill({\n" +
                "                                color: '#86489B'\n" +
                "                            }),\n" +
                "                            radius: 3\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"RESA\": new ol.style.Style({\n" +
                "                        image: new ol.style.Circle({\n" +
                "                            fill: new ol.style.Fill({\n" +
                "                                color: '#807580'\n" +
                "                            }),\n" +
                "                            radius: 3\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"TER BUS\": new ol.style.Style({\n" +
                "                        image: new ol.style.Circle({\n" +
                "                            fill: new ol.style.Fill({\n" +
                "                                color: '#C93F2A'\n" +
                "                            }),\n" +
                "                            radius: 3\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"REMI\": new ol.style.Style({\n" +
                "                        image: new ol.style.Circle({\n" +
                "                            fill: new ol.style.Fill({\n" +
                "                                color: '#00FB5B'\n" +
                "                            }),\n" +
                "                            radius: 3\n" +
                "                        })\n" +
                "                    }),\n" +
                "                },\n" +
                "                velo_stop: {\n" +
                "                    \"Arceaux\": new ol.style.Style({\n" +
                "                        image: new ol.style.Circle({\n" +
                "                            fill: new ol.style.Fill({\n" +
                "                                color: '#00679E'\n" +
                "                            }),\n" +
                "                            radius: 3\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"Abri\": new ol.style.Style({\n" +
                "                        image: new ol.style.Circle({\n" +
                "                            fill: new ol.style.Fill({\n" +
                "                                color: '#8D0FF5'\n" +
                "                            }),\n" +
                "                            radius: 3\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"Parc Relais Vé\": new ol.style.Style({\n" +
                "                        image: new ol.style.Circle({\n" +
                "                            fill: new ol.style.Fill({\n" +
                "                                color: '#D77E0B'\n" +
                "                            }),\n" +
                "                            radius: 3\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"Rateaux\": new ol.style.Style({\n" +
                "                        image: new ol.style.Circle({\n" +
                "                            fill: new ol.style.Fill({\n" +
                "                                color: '#5B6EE9'\n" +
                "                            }),\n" +
                "                            radius: 3\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"Véloparc\": new ol.style.Style({\n" +
                "                        image: new ol.style.Circle({\n" +
                "                            fill: new ol.style.Fill({\n" +
                "                                color: '#1E18E3'\n" +
                "                            }),\n" +
                "                            radius: 3\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"Ouvrage\": new ol.style.Style({\n" +
                "                        image: new ol.style.Circle({\n" +
                "                            fill: new ol.style.Fill({\n" +
                "                                color: '#E72A9A'\n" +
                "                            }),\n" +
                "                            radius: 3\n" +
                "                        })\n" +
                "                    }),\n" +
                "                    \"Porte Vélo\": new ol.style.Style({\n" +
                "                        image: new ol.style.Circle({\n" +
                "                            fill: new ol.style.Fill({\n" +
                "                                color: '#0FF1E4'\n" +
                "                            }),\n" +
                "                            radius: 3\n" +
                "                        })\n" +
                "                    })\n" +
                "                },\n" +
                "                velo: new ol.style.Style({\n" +
                "                    stroke: new ol.style.Stroke({\n" +
                "                        color: '#FF0000',\n" +
                "                        lineDash: [10, 10],\n" +
                "                        width: 2\n" +
                "                    })\n" +
                "                })\n" +
                "            }\n" +
                "\n" +
                "            layer_tram.getSource().on(\"featuresloadend\", (e) => e.features.forEach(element => {\n" +
                "                element.setStyle(styles.tram[element.get('num_ligne')]);\n" +
                "            }));\n" +
                "\n" +
                "            layer_bus.getSource().on(\"featuresloadend\", (e) => e.features.forEach(element => {\n" +
                "                element.setStyle(styles.bus[element.get('num_ligne')]);\n" +
                "            }));\n" +
                "\n" +
                "            layer_velo.getSource().on(\"featuresloadend\", (e) => e.features.forEach(element => {\n" +
                "                element.setStyle(styles.velo);\n" +
                "            }));\n" +
                "\n" +
                "            layer_trambus_stop.getSource().on(\"featuresloadend\", (e) => e.features.forEach(element => {\n" +
                "                element.setStyle(styles.trambus_stop[element.get('categ_arret')]);\n" +
                "            }));\n" +
                "\n" +
                "            layer_velo_stop.getSource().on(\"featuresloadend\", (e) => e.features.forEach(element => {\n" +
                "                element.setStyle(styles.velo_stop[element.get('type')]);\n" +
                "            }));\n" +
                "\n" +
                "            // ########################## Map ##########################\n" +
                "\n" +
                "            const layers = [\n" +
                "                new ol.layer.Tile({\n" +
                "                    source: new ol.source.OSM(),\n" +
                "                }),\n" +
                "                layer_tram,\n" +
                "                layer_bus,\n" +
                "                layer_trambus_stop,\n" +
                "                layer_velo,\n" +
                "                layer_velo_stop,\n" +
                "            ]\n" +
                "\n" +
                "            const view = new ol.View({\n" +
                "                center: ol.proj.fromLonLat([1.909251, 47.902964]),\n" +
                "                zoom: 13,\n" +
                "            });\n" +
                "\n" +
                "            const map = new ol.Map({\n" +
                "                layers: layers,\n" +
                "                target: 'map',\n" +
                "                view: view,\n" +
                "            });\n" +
                "\n" +
                "\n" +
                "            // ########################## Geolocalisation ##########################\n" +
                "\n" +
                "            const geolocation = new ol.Geolocation({\n" +
                "                trackingOptions: {\n" +
                "                    enableHighAccuracy: true,\n" +
                "                },\n" +
                "                projection: view.getProjection(),\n" +
                "                tracking: true,\n" +
                "            });\n" +
                "\n" +
                "            const accuracyFeature = new ol.Feature();\n" +
                "            geolocation.on('change:accuracyGeometry', function () {\n" +
                "                accuracyFeature.setGeometry(geolocation.getAccuracyGeometry());\n" +
                "            });\n" +
                "\n" +
                "            const positionFeature = new ol.Feature();\n" +
                "            positionFeature.setStyle(\n" +
                "                new ol.style.Style({\n" +
                "                    image: new ol.style.Circle({\n" +
                "                        radius: 6,\n" +
                "                        fill: new ol.style.Fill({\n" +
                "                            color: '#3399CC',\n" +
                "                            width: 10,\n" +
                "                        }),\n" +
                "                        stroke: new ol.style.Stroke({\n" +
                "                            color: '#fff',\n" +
                "                            width: 5,\n" +
                "                        }),\n" +
                "                    }),\n" +
                "                })\n" +
                "            );\n" +
                "\n" +
                "            geolocation.on('change:position', function () {\n" +
                "                const coordinates = geolocation.getPosition();\n" +
                "                positionFeature.setGeometry(coordinates ? new ol.geom.Point(coordinates) : null);\n" +
                "            });\n" +
                "\n" +
                "            new ol.layer.Vector({\n" +
                "                map: map,\n" +
                "                source: new ol.source.Vector({\n" +
                "                    features: [accuracyFeature, positionFeature],\n" +
                "                }),\n" +
                "            });\n" +
                "\n" +
                "            // ########################## Filters ##########################\n" +
                "\n" +
                "            document.getElementById('velo').addEventListener('click', function () {\n" +
                "                let visible = document.getElementById('velo').checked;\n" +
                "                layer_velo.setVisible(visible);\n" +
                "                layer_velo_stop.setVisible(visible);\n" +
                "                $(\"#legendVelo\").toggle(visible);\n" +
                "            });\n" +
                "\n" +
                "            document.getElementById('tramBus').addEventListener('click', function () {\n" +
                "                let visible = document.getElementById('tramBus').checked;\n" +
                "                layer_tram.setVisible(visible);\n" +
                "                layer_bus.setVisible(visible);\n" +
                "                layer_trambus_stop.setVisible(visible);\n" +
                "                $(\"#legendTramBus\").toggle(visible);\n" +
                "                $(\"#filter\").toggle(visible);\n" +
                "            });\n" +
                "\n" +
                "            document.getElementById('input_filter').addEventListener('keyup', function (event) {\n" +
                "                if (event.key == \"Enter\") {\n" +
                "                    let filter = document.getElementById('input_filter').value;\n" +
                "                    if (filter !== '') {\n" +
                "\n" +
                "                        const elements = filter.split(',').map(e => e.trim());\n" +
                "                        const formattedParams = \"('\" + elements.join(\"', '\") + \"')\";\n" +
                "\n" +
                "                        // Add the filter to the source\n" +
                "                        layer_tram.getSource().setUrl(URL + \"?service=WFS&version=1.1.0&request=GetFeature&typeName=\" + LIGNE_TRAM + \"&outputFormat=application/json&CQL_FILTER=num_ligne IN \" + formattedParams);\n" +
                "                        layer_bus.getSource().setUrl(URL + \"?service=WFS&version=1.1.0&request=GetFeature&typeName=\" + LIGNE_BUS + \"&outputFormat=application/json&CQL_FILTER=num_ligne IN \" + formattedParams);\n" +
                "                    } else {\n" +
                "                        // Reset the source\n" +
                "                        layer_tram.getSource().setUrl(URL + \"?service=WFS&version=1.1.0&request=GetFeature&typeName=\" + LIGNE_TRAM + \"&outputFormat=application/json\");\n" +
                "                        layer_bus.getSource().setUrl(URL + \"?service=WFS&version=1.1.0&request=GetFeature&typeName=\" + LIGNE_BUS + \"&outputFormat=application/json\");\n" +
                "                    }\n" +
                "                    // Emmit the event to reload the features\n" +
                "                    layer_bus.getSource().refresh();\n" +
                "                    layer_tram.getSource().refresh();\n" +
                "                }\n" +
                "            });\n" +
                "\n" +
                "            document.getElementById('filter_btn').addEventListener('click', function () {\n" +
                "                let filter = document.getElementById('input_filter').value;\n" +
                "                if (filter !== '') {\n" +
                "\n" +
                "                    const elements = filter.split(',').map(e => e.trim());\n" +
                "                    const formattedParams = \"('\" + elements.join(\"', '\") + \"')\";\n" +
                "\n" +
                "                    // Add the filter to the source\n" +
                "                    layer_tram.getSource().setUrl(URL + \"?service=WFS&version=1.1.0&request=GetFeature&typeName=\" + LIGNE_TRAM + \"&outputFormat=application/json&CQL_FILTER=num_ligne IN \" + formattedParams);\n" +
                "                    layer_bus.getSource().setUrl(URL + \"?service=WFS&version=1.1.0&request=GetFeature&typeName=\" + LIGNE_BUS + \"&outputFormat=application/json&CQL_FILTER=num_ligne IN \" + formattedParams);\n" +
                "                } else {\n" +
                "                    // Reset the source\n" +
                "                    layer_tram.getSource().setUrl(URL + \"?service=WFS&version=1.1.0&request=GetFeature&typeName=\" + LIGNE_TRAM + \"&outputFormat=application/json\");\n" +
                "                    layer_bus.getSource().setUrl(URL + \"?service=WFS&version=1.1.0&request=GetFeature&typeName=\" + LIGNE_BUS + \"&outputFormat=application/json\");\n" +
                "                }\n" +
                "                // Emmit the event to reload the features\n" +
                "                layer_bus.getSource().refresh();\n" +
                "                layer_tram.getSource().refresh();\n" +
                "            });\n" +
                "\n" +
                "            $(document).ready(function() {\n" +
                "                // Dynamic creation of the legend (For bikes)\n" +
                "                let veloBody = $(\"#legendVeloTable\");\n" +
                "                veloBody.append(\"<tr><td><div style=\\\"border-bottom: 5px dashed \" + styles.velo.getStroke().getColor() + \"; width: 50px;\\\"></div></td><td><p>Piste cyclables</p></td></tr>\")\n" +
                "                for (const [name, style] of Object.entries(styles.velo_stop)) {\n" +
                "                    veloBody.append(\"<tr id=\" + name + \"><td><span class=\\\"dot\\\" style=\\\"background-color: \" + style.getImage().getFill().getColor() + \"\\\"></span></td><td><p>\" + name + \"</p></td></tr>\")\n" +
                "                }\n" +
                "\n" +
                "                // Dynamic creation of the legend (For Tram & buses)\n" +
                "                let tramBusStopBody = $(\"#legendTramBusStopTable\");\n" +
                "                for (const [name, style] of Object.entries(styles.trambus_stop)) {\n" +
                "                    tramBusStopBody.append(\"<tr id=\" + name + \"><td><span class=\\\"dot\\\" style=\\\"background-color: \" + style.getImage().getFill().getColor() + \"\\\"></span></td><td><p>\" + name + \"</p></td></tr>\")\n" +
                "                }\n" +
                "                let tramBusLigneBody = $(\"#legendTramBusLigneTable\");\n" +
                "                for (const [name, style] of Object.entries(styles.tram)) {\n" +
                "                    tramBusLigneBody.append(\"<tr id=\" + name + \"><td><div style=\\\"border-bottom: 5px solid \" + style.getStroke().getColor() + \"; width: 50px;\\\"></div></td><td><p>\" + name + \"</p></td></tr>\")\n" +
                "                }\n" +
                "                for (const [name, style] of Object.entries(styles.bus)) {\n" +
                "                    tramBusLigneBody.append(\"<tr id=\" + name + \"><td><div style=\\\"border-bottom: 5px solid \" + style.getStroke().getColor() + \"; width: 50px;\\\"></div></td><td><p>\" + name + \"</p></td></tr>\")\n" +
                "                }\n" +
                "            });\n" +
                "        </script>\n" +
                "    </body>\n" +
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


        settings.setDomStorageEnabled(true);
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        settings.setGeolocationEnabled(true);
        settings.setAppCachePath(getApplicationContext().getCacheDir().getPath());
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.setWebViewClient(new WebViewClient());
        webView.loadDataWithBaseURL("https://www.perdu.com", htmlString, "text/html", "UTF-8", null);
    }
}