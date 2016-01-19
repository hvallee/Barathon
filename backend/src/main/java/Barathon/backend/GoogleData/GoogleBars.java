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
    private final String URL_GOOGLE_API = "https://maps.googleapis.com/maps/api/";
    private final String KEY_API = "AIzaSyD3ZVV7dO2a8cU9UUsC9ubYTky994DWhPo";
    private final String DEBUT_URL_PHOTO_BAR = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=";
    private final String FIN_URL_PHOTO_BAR = "&key=" + KEY_API;
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
        String url = URL_GOOGLE_API + "place/textsearch/json?query=bar+rennes&key="+KEY_API;

        try {
            String eget = googleBars.sendGet(url);
            JSONObject data = new JSONObject(eget);
            JSONArray arr = data.getJSONArray("results");
            Boolean continuer = true;
            while(continuer){
                bars = buildBarFromArray(arr, bars);
                try{
                    /**
                     * Permet de récuperer un token pour refaire une demande à l'api
                     * Si next_page_token renvoie une exception c'est qu'on a atteind la fin
                     */
                    String token = data.getString("next_page_token");
                    System.out.println("token : " + token);
                    eget = googleBars.sendGet(url + "&pagetoken=" + token);
                    data = new JSONObject(eget);
                    arr = data.getJSONArray("results");
                } catch (Exception e1){
                    //e1.printStackTrace();
                    continuer = false;
                }
            }
            System.out.println(bars);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bars;
    }

    public ArrayList<Bar> buildBarFromArray( JSONArray arr, ArrayList bars ) throws Exception {
        System.out.println("Taille de la liste avant ajout : " + bars.size());
        System.out.println(arr.toString());
        Bar bar;
        String address;
        String name;
        String latitude;
        String longitude;
        for(int i = 0; i < arr.length(); i++) {
            address = arr.getJSONObject(i).getString("formatted_address");
            name = arr.getJSONObject(i).getString("name");
            latitude = arr.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude = arr.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getString("lng");
            bar = new Bar((long)i, name, address, "", latitude, longitude);
            try{
                String photoReference =  arr.getJSONObject(i).getJSONArray("photos").getJSONObject(0).getString("photo_reference");
                bar.setUrl(DEBUT_URL_PHOTO_BAR + photoReference + FIN_URL_PHOTO_BAR);
            }catch (Exception e1){
            }
            bars.add(bar);
        }
        System.out.println("Taille de la liste après ajout : "  + bars.size());

        return bars;

    }


}
