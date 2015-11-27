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

        System.out.println("Testing 1 - Send Http GET request");
        http.sendGet();

        System.out.println("\nTesting 2 - Send Http POST request");
      //  http.sendPost();

    }

    // HTTP GET request
    private List<Bar> sendGet() throws Exception {

        String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=bar+rennes&key=AIzaSyD3ZVV7dO2a8cU9UUsC9ubYTky994DWhPo";

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
        System.out.println(response);

        List<Bar> bars = new ArrayList<>();
        JSONObject data = new JSONObject(response.toString());
        JSONArray arr = data.getJSONArray("results");
        for (int i = 0; i < arr.length(); i++)
        {
            String address = arr.getJSONObject(i).getString("formatted_address");
            String name = arr.getJSONObject(i).getString("name");
            String latitude = arr.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getString("lat");
            String longitude = arr.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getString("lng");

            JSONObject jo = new JSONObject();
            jo.put("id", i);
            jo.put("address", address);
            jo.put("name", name);
            jo.put("latitude", latitude);
            jo.put("longitude", longitude);
            jo.put("phone", "");
            Bar bar = new Bar(i, name, address, "", latitude, longitude);
            bars.add(bar);
        }
        in.close();
        return bars;
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
        GoogleBars http = new GoogleBars();
        List<Bar> bars = new ArrayList<>();

        try {
            bars = http.sendGet();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bars;
    }
}
