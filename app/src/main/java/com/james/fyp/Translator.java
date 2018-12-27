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


    public static String translate(String langFrom, String langTo, String text,int depth) throws IOException {
        // INSERT YOU URL HERE
        StringBuilder response = new StringBuilder();
        String check = text; // check the translate result of the input text
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
            inputLine = in.readLine(); // convert BufferedReader to String
            for (int i = 0; i < inputLine.length(); i++){
                char c = inputLine.charAt(i);
                //Process char
                if(c == '&')
                {
                    response.append("\'");
                    i = i +4;
                }
                else
                {response.append(c);}
            }
            in.close();

        } catch (IOException e) {
            e.printStackTrace();

        }
        if(check.equals(response.toString())&&depth>0) // if the result is same after translated, exchange the source and destination
        {
            return translate(langTo,langFrom,text,--depth);
        }
        else
            return response.toString();

    }

}