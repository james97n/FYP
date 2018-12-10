package com.james.fyp;

import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Translator {

    Translator() {
    }


    public static String translate(String langFrom, String langTo, String text) throws IOException {
        // INSERT YOU URL HERE
        StringBuilder response = new StringBuilder();
        String text2 = text;
        try {
            String urlStr = "https://script.google.com/macros/s/AKfycbwMnih9Fdg7USzIQLQMHXkeMaVbgNVVN33kTCAgBZmU7mDImNQ/exec" +
                    "?q=" + URLEncoder.encode(text, "UTF-8") +
                    "&target=" + langTo +
                    "&source=" + langFrom;
            URL url = new URL(urlStr);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

        } catch (IOException e) {
            e.printStackTrace();

        }
        if(text2.equals(response.toString())) {
            return translate(langTo,langFrom,text);
        }
        else
            return response.toString();

    }

}