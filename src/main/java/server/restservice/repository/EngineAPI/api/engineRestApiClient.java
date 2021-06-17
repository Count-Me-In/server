package server.restservice.repository.EngineAPI.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import server.restservice.repository.EngineAPI.model.Actor;
import server.restservice.repository.EngineAPI.model.Assignment;
import server.restservice.repository.EngineAPI.model.Bid;
import server.restservice.repository.EngineAPI.model.Item;

public class engineRestApiClient implements engineAPIInterface {

    private Gson g = new Gson();

    @Value("${engineURL}")
    private String URL;

    @Override
    public void addActor(Actor actor) {
        restRequest("/actors", "POST", actor, new TypeToken<Actor>() {
        }.getType());
    }

    @Override
    public void addBid(Bid bid) {
        restRequest("/bids", "POST", bid, new TypeToken<Bid>() {
        }.getType());
    }

    @Override
    public void addItem(Item item) {
        restRequest("/items", "POST", item, new TypeToken<Item>() {
        }.getType());
    }

    @Override
    public void deleteActor(UUID actorID) {
        restRequest("/actors/" + actorID.toString(), "DELETE");
    }

    @Override
    public void deleteBid(UUID bidID) {
        restRequest("/bids/" + bidID.toString(), "DELETE");
    }

    @Override
    public void deleteItem(UUID itemId) {
        restRequest("/items/" + itemId.toString(), "DELETE");
    }

    @Override
    public void editActor(UUID actorID, Actor actor) {
        restRequest("/actors/" + actorID.toString(), "PUT", actor, new TypeToken<Actor>() {
        }.getType());
    }

    @Override
    public void editBid(UUID bidID, Bid bid) {
        restRequest("/bids/" + bidID.toString(), "PUT", bid, new TypeToken<Bid>() {
        }.getType());
    }

    @Override
    public void editItem(UUID itemId, Item item) {
        restRequest("/items/" + itemId.toString(), "PUT", item, new TypeToken<Item>() {
        }.getType());
    }

    @Override
    public void execAutcion() {
        restRequest("/Auction", "POST");
    }

    @Override
    public Actor getActor(UUID actorID) {
        return (Actor) restRequest("/actors/" + actorID.toString(), "GET", new TypeToken<Actor>() {
        }.getType());
    }

    @Override
    public List<Assignment> getActorAssignments(UUID actorID, Long from, Long to) {
        String endpoint = "/assignments/" + actorID.toString() + "?from=" + from;
        if (to != null) {
            endpoint += "&to=" + to;
        }
        return (List<Assignment>) restRequest(endpoint, "GET", new TypeToken<List<Assignment>>() {
        }.getType());
    }

    @Override
    public List<Actor> getActors() {
        return (List<Actor>) restRequest("/actors", "GET", new TypeToken<List<Actor>>() {
        }.getType());
    }

    @Override
    public List<Assignment> getAssignments() {
        return (List<Assignment>) restRequest("/assignments", "GET", new TypeToken<List<Assignment>>() {
        }.getType());
    }

    @Override
    public Bid getBid(UUID actorID, UUID itemID) {
        return (Bid) restRequest("/bids?actorID=" + actorID + "&itemID=" + itemID, "GET", new TypeToken<Bid>() {
        }.getType());
    }

    @Override
    public Bid getBidByID(UUID bidID) {
        return (Bid) restRequest("/bids/" + bidID.toString(), "GET", new TypeToken<Bid>() {
        }.getType());
    }

    @Override
    public Item getItem(UUID itemId) {
        return (Item) restRequest("/items/" + itemId.toString(), "GET", new TypeToken<Item>() {
        }.getType());
    }

    @Override
    public List<Item> getItems() {
        return (List<Item>) restRequest("/items", "GET", new TypeToken<List<Item>>() {
        }.getType());
    }

    /* Private functions */

    private Object restRequest(String endpoint, String restType) {
        return restRequest(endpoint, restType, null, null, null);
    }

    private Object restRequest(String endpoint, String restType, Type outputType) {
        return restRequest(endpoint, restType, outputType, null, null);
    }

    private Object restRequest(String endpoint, String restType, Object input, Type inputType) {
        return restRequest(endpoint, restType, null, input, inputType);
    }

    private Object restRequest(String endpoint, String restType, Type outputType, Object input, Type inputType) {
        try {
            URL url = new URL(URL + endpoint);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(restType);
            conn.setRequestProperty("Accept", "application/json");

            if (input != null) {
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
                g.toJson(input, inputType, bw);
            }

            Object output = null;

            if (outputType == null) {
                conn.connect();
            } else {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                output = g.fromJson(br, outputType);
            }

            conn.disconnect();

            return output;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
