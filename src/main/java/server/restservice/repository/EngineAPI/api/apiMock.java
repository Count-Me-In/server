package server.restservice.repository.EngineAPI.api;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
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

@Component("mockAPI")
public class apiMock implements engineAPIInterface{

    private Gson gson = new Gson();
       @Value("${mockData}")
    private String mockData;

    @Value("${actorsFile}")
    private String actorsFile;

    @Value("${bidsFile}")
    private String bidsFile;
       @Value("${assignmensFile}")
    private String assignmensFile;

    @Value("${itemsFile}")
    private String itemsFile;

    public void addActor(Actor actor) {
        List<Actor> actors = getActors();
        actors.add(actor);
        try {
            // create a writer
            Writer writer = Files.newBufferedWriter(Paths.get(mockData,actorsFile));
                       gson.toJson(actors, new TypeToken<List<Actor>>() {}.getType(), writer);
                   // close reader
            writer.close();
               } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void addBid(Bid bid) {
        try {
            // create a reader
            Reader reader = Files.newBufferedReader(Paths.get(mockData,bidsFile));
                   // convert JSON string to User object
            List<Bid> bids = gson.fromJson(reader, new TypeToken<List<Bid>>() {}.getType());
                   // close reader
            reader.close();

            bids.add(bid);

            // create a writer
            Writer writer = Files.newBufferedWriter(Paths.get(mockData,bidsFile));
                       gson.toJson(bids, new TypeToken<List<Bid>>() {}.getType(), writer);
                   // close reader
            writer.close();
               } catch (Exception ex) {
            ex.printStackTrace();
        }
           }

    public void addItem(Item item) {
        List<Item> items = getItems();
        items.add(item);
        try {
            // create a writer
            Writer writer = Files.newBufferedWriter(Paths.get(mockData,itemsFile));
                       gson.toJson(items, new TypeToken<List<Item>>() {}.getType(), writer);
                   // close reader
            writer.close();
               } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteActor(UUID actorID) {
        List<Actor> actors = getActors();
        actors.removeIf( actor -> actor.getId() == actorID);
        try {
            // create a writer
            Writer writer = Files.newBufferedWriter(Paths.get(mockData,actorsFile));
                       gson.toJson(actors, new TypeToken<List<Actor>>() {}.getType(), writer);
                   // close reader
            writer.close();
               } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteBid(UUID bidID) {
        try {
            // create a reader
            Reader reader = Files.newBufferedReader(Paths.get(mockData,bidsFile));
                   // convert JSON string to User object
            List<Bid> bids = gson.fromJson(reader, new TypeToken<List<Bid>>() {}.getType());
                   // close reader
            reader.close();

            bids.removeIf(bid -> bid.getId() == bidID);

            // create a writer
            Writer writer = Files.newBufferedWriter(Paths.get(mockData,bidsFile));
                       gson.toJson(bids, new TypeToken<List<Bid>>() {}.getType(), writer);
                   // close reader
            writer.close();
               } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteItem(UUID itemId) {
        List<Item> items = getItems();
        items.removeIf( item -> item.getId() == itemId);
        try {
            // create a writer
            Writer writer = Files.newBufferedWriter(Paths.get(mockData,itemsFile));
                       gson.toJson(items, new TypeToken<List<Item>>() {}.getType(), writer);
                   // close reader
            writer.close();
               } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void editActor(UUID actorID, Actor actor) {
        List<Actor> actors = getActors();
        for (int i = 0; i < actors.size(); i++) {
            if (actors.get(i).getId() == actorID) {
                actors.set(i, actor);
                break;
            }
        }
        try {
            // create a writer
            Writer writer = Files.newBufferedWriter(Paths.get(mockData,actorsFile));
                       gson.toJson(actors, new TypeToken<List<Actor>>() {}.getType(), writer);
                   // close reader
            writer.close();
               } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void editBid(UUID bidID, Bid bid) {
        try {
            // create a reader
            Reader reader = Files.newBufferedReader(Paths.get(mockData,bidsFile));
                   // convert JSON string to User object
            List<Bid> bids = gson.fromJson(reader, new TypeToken<List<Bid>>() {}.getType());
                   // close reader
            reader.close();

            for (int i = 0; i < bids.size(); i++) {
                if (bids.get(i).getId() == bidID) {
                    bids.set(i, bid);
                    break;
                }
            }

            // create a writer
            Writer writer = Files.newBufferedWriter(Paths.get(mockData,bidsFile));
                       gson.toJson(bids, new TypeToken<List<Bid>>() {}.getType(), writer);
                   // close reader
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void editItem(UUID itemId, Item item) {
        List<Item> items = getItems();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == itemId) {
                items.set(i, item);
                break;
            }
        }
        try {
            // create a writer
            Writer writer = Files.newBufferedWriter(Paths.get(mockData,itemsFile));
                       gson.toJson(items, new TypeToken<List<Item>>() {}.getType(), writer);
                   // close reader
            writer.close();
               } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void execAutcion() {
        // TODO Auto-generated method stub
    }

    public Actor getActor(UUID actorID) {
        Actor output = null;
        try {
            // create a reader
            Reader reader = Files.newBufferedReader(Paths.get(mockData,actorsFile));
                   // convert JSON string to User object
            List<Actor> actors = gson.fromJson(reader, new TypeToken<List<Actor>>() {}.getType());

            for (Actor actor : actors) {
                if (actor.getId() == actorID){
                    output = actor;
                    break;
                }
            }
                   // close reader
            reader.close();
               } catch (Exception ex) {
            ex.printStackTrace();
        }
        return output;
    }

    public List<Assignment> getActorAssignments(UUID actorID) {
        HashMap<UUID,Assignment> latestItemAssignments = new HashMap<UUID,Assignment>();
        List<Assignment> assignments = getAssignments();
        for (Assignment assignment : assignments) {
            if ((!latestItemAssignments.containsKey(assignment.getItemID())) 
            || latestItemAssignments.get(assignment.getItemID()).getDate().before(assignment.getDate())) {
                latestItemAssignments.put(assignment.getItemID(), assignment);
            }
        }
        return new ArrayList<Assignment>(latestItemAssignments.values());
    }

    public List<Actor> getActors() {
        try {
            // create a reader
            Reader reader = Files.newBufferedReader(Paths.get(mockData,actorsFile));
                   // convert JSON string to User object
            List<Actor> actors = gson.fromJson(reader, new TypeToken<List<Actor>>() {}.getType());
                   // close reader
            reader.close();

            return actors;
               } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<Actor>();
        }
    }

    public List<Assignment> getAssignments() {
        try {
            // create a reader
            Reader reader = Files.newBufferedReader(Paths.get(mockData,assignmensFile));
                   // convert JSON string to User object
            List<Assignment> assignments = gson.fromJson(reader, new TypeToken<List<Assignment>>() {}.getType());
                   // close reader
            reader.close();

            return assignments;
               } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<Assignment>();
        }
    }

    public Bid getBid(UUID actorID, UUID itemID) {
        Bid output = null;
        try {
            // create a reader
            Reader reader = Files.newBufferedReader(Paths.get(mockData,bidsFile));
                   // convert JSON string to User object
            List<Bid> bids = gson.fromJson(reader, new TypeToken<List<Bid>>() {}.getType());

            for (Bid bid : bids) {
                if (bid.getActorID() == actorID && bid.getItemID() == itemID){
                    output = bid;
                    break;
                }
            }
                   // close reader
            reader.close();
               } catch (Exception ex) {
            ex.printStackTrace();
        }
        return output;
    }

    public Item getItem(UUID itemId) {
        Item output = null;
        try {
            // create a reader
            Reader reader = Files.newBufferedReader(Paths.get(mockData,itemsFile));
                   // convert JSON string to User object
            List<Item> items = gson.fromJson(reader, new TypeToken<List<Item>>() {}.getType());

            for (Item item : items) {
                if (item.getId() == itemId){
                    output = item;
                    break;
                }
            }
                   // close reader
            reader.close();
               } catch (Exception ex) {
            ex.printStackTrace();
        }
        return output;
    }

    public List<Item> getItems() {
        try {
            // create a reader
            Reader reader = Files.newBufferedReader(Paths.get(mockData,itemsFile));
                   // convert JSON string to User object
            List<Item> items = gson.fromJson(reader, new TypeToken<List<Item>>() {}.getType());

            // close reader
            reader.close();

            return items;
               } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<Item>();
        }
    }

    public Bid getBidByID(UUID bidID) {
        Bid output = null;
        try {
            // create a reader
            Reader reader = Files.newBufferedReader(Paths.get(mockData,bidsFile));
                   // convert JSON string to User object
            List<Bid> bids = gson.fromJson(reader, new TypeToken<List<Bid>>() {}.getType());

            for (Bid bid : bids) {
                if (bid.getId() == bidID){
                    output = bid;
                    break;
                }
            }
                   // close reader
            reader.close();
               } catch (Exception ex) {
            ex.printStackTrace();
        }
        return output;
    }
   }
