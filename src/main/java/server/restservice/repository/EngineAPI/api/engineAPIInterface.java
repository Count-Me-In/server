package server.restservice.repository.EngineAPI.api;

import server.restservice.repository.EngineAPI.model.*;
import java.util.UUID;
import java.util.List;

public interface engineAPIInterface {
    public void addActor(Actor actor);
    public void addBid(Bid bid);
    public void addItem(Item item);
    public void deleteActor(UUID actorID);
    public void deleteBid(UUID bidID);
    public void deleteItem(UUID itemId);
    public void editActor(UUID actorID, Actor actor);
    public void editBid(UUID bidID, Bid bid);
    public void editItem(UUID itemId, Item item);
    public void execAutcion();
    public Actor getActor(UUID actorID);
    public List<Assignment> getActorAssignments(UUID actorID);
    public List<Actor> getActors();
    public List<Assignment> getAssignments();
    public Bid getBid(UUID actorID, UUID itemID);
    public Bid getBidByID(UUID bidID);
    public Item getItem(UUID itemId);
    public List<Item> getItems();
}
