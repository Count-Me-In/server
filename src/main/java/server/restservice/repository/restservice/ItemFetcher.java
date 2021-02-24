package server.restservice.repository.restservice;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ItemFetcher {

    public static Item[] getAllItems() {
        try {
            URL url = new URL("https://f6ezbrrlgb.execute-api.us-east-2.amazonaws.com/internal/biddings");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String full = "";
            String output;
            while ((output = br.readLine()) != null) {
                full += output;
            }

            Gson g = new Gson();
            JsonObject convertedObject = new Gson().fromJson(full, JsonObject.class);
            Item[] items = g.fromJson(convertedObject.get("Items"), Item[].class);

            conn.disconnect();

            return items;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Item getItemById(int id) {
        try {
            URL url = new URL("https://f6ezbrrlgb.execute-api.us-east-2.amazonaws.com/internal/biddings/" + id);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String full = "";
            String output;
            while ((output = br.readLine()) != null) {
                full += output;
            }

            Gson g = new Gson();
            Item it = g.fromJson(full, Item.class);

            conn.disconnect();

            return it;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
