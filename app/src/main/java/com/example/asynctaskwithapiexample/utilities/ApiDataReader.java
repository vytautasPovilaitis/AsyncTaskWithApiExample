package com.example.asynctaskwithapiexample.utilities;

import com.example.asynctaskwithapiexample.parsers.FloatRatesXmlParser;
import com.example.asynctaskwithapiexample.parsers.GunfireHtmlParser;
import com.example.asynctaskwithapiexample.parsers.MeteoLtJsonParser;
import com.example.asynctaskwithapiexample.parsers.ECBXmlParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.asynctaskwithapiexample.utilities.Constants.FLOATRATES_API_URL;
import static com.example.asynctaskwithapiexample.utilities.Constants.GUNFIRE_URL;
import static com.example.asynctaskwithapiexample.utilities.Constants.METEOLT_API_URL;
import static com.example.asynctaskwithapiexample.utilities.Constants.ECB_API_URL;

public class ApiDataReader {
    public static List<String> getValuesFromApi(String apiCode) throws IOException {
        InputStream apiContentStream = null;
        List<String> result = new ArrayList<>();
        try {
            switch (apiCode) {
                case GUNFIRE_URL:
                    apiContentStream = downloadUrlContent(GUNFIRE_URL);
                    result.add(GunfireHtmlParser.getAmountAndDiscountFromGunfire(apiContentStream));
                    break;
                case FLOATRATES_API_URL:
                    apiContentStream = downloadUrlContent(FLOATRATES_API_URL);
                    result.addAll(FloatRatesXmlParser.getCurrencyRatesBaseUsd(apiContentStream));
                    break;
                case METEOLT_API_URL:
                    apiContentStream = downloadUrlContent(METEOLT_API_URL);
                    result.addAll(MeteoLtJsonParser.getKaunasWeatherForecast(apiContentStream));
                    break;
                case ECB_API_URL:
                    apiContentStream = downloadUrlContent(ECB_API_URL);
                    result.addAll(ECBXmlParser.getECBCurrencyRates(apiContentStream));
                    break;
                default:
            }
        }
        finally {
            if (apiContentStream != null) {
                apiContentStream.close();
            }
        }
        return result;
    }

    //Routine that creates and calls GET request to web page
    private static InputStream downloadUrlContent(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect();
        return conn.getInputStream();
    }
}
