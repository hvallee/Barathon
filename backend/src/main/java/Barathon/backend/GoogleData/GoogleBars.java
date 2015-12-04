package Barathon.backend.GoogleData;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import Barathon.backend.Model.Bar;


/**
 * Created by hvallee on 19/10/2015.
 */
public class GoogleBars {

    private final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) throws Exception {

        GoogleBars http = new GoogleBars();

    }

    // HTTP GET request
    private String sendGet(String url) throws Exception {


        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {

            response.append(inputLine);
        }

        in.close();
        return response.toString();
    }


    // HTTP POST request
    private void sendPost() throws Exception {

        String url = "https://selfsolve.apple.com/wcResults.do";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
       // System.out.println(response.toString());

    }

    public List<Bar> getBars() {
        GoogleBars googleBars = new GoogleBars();
        ArrayList bars = new ArrayList();
        String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=bar+rennes&key=AIzaSyD3ZVV7dO2a8cU9UUsC9ubYTky994DWhPo";

        try {
            String e = googleBars.sendGet(url);
            JSONObject data = new JSONObject(e);
            JSONArray arr = data.getJSONArray("results");

            String result2;
            String page3;
            for(int page2 = 0; page2 < arr.length(); ++page2) {
                result2 = arr.getJSONObject(page2).getString("formatted_address");
                String data2 = arr.getJSONObject(page2).getString("name");
                String arr2 = arr.getJSONObject(page2).getJSONObject("geometry").getJSONObject("location").getString("lat");
                page3 = arr.getJSONObject(page2).getJSONObject("geometry").getJSONObject("location").getString("lng");
                Bar result3 = new Bar((long)page2, data2, result2, "", arr2, page3);
                bars.add(result3);
            }

            String var22 = data.getString("next_page_token");
            Thread.sleep(2000L);
            result2 = googleBars.sendGet(url + "&pagetoken=" + var22);
            JSONObject var23 = new JSONObject(result2);
            JSONArray var25 = var23.getJSONArray("results");

            String var26;
            for(int var24 = 0; var24 < var25.length(); ++var24) {
                var26 = var25.getJSONObject(var24).getString("formatted_address");
                String data3 = var25.getJSONObject(var24).getString("name");
                String arr3 = var25.getJSONObject(var24).getJSONObject("geometry").getJSONObject("location").getString("lat");
                String i = var25.getJSONObject(var24).getJSONObject("geometry").getJSONObject("location").getString("lng");
                Bar address = new Bar((long)var24, data3, var26, "", arr3, i);
                bars.add(address);
            }

            page3 = var23.getString("next_page_token");
            System.out.println(page3);
            Thread.sleep(2000L);
            var26 = googleBars.sendGet(url + "&pagetoken=" + page3);
            JSONObject var27 = new JSONObject(var26);
            JSONArray var29 = var27.getJSONArray("results");

            for(int var28 = 0; var28 < var29.length(); ++var28) {
                String var30 = var29.getJSONObject(var28).getString("formatted_address");
                String name = var29.getJSONObject(var28).getString("name");
                String latitude = var29.getJSONObject(var28).getJSONObject("geometry").getJSONObject("location").getString("lat");
                String longitude = var29.getJSONObject(var28).getJSONObject("geometry").getJSONObject("location").getString("lng");
                Bar bar = new Bar((long)var28, name, var30, "", latitude, longitude);
                bars.add(bar);
            }

            System.out.println(bars);
        } catch (Exception var21) {
            var21.printStackTrace();
        }

        return bars;
    }
}
