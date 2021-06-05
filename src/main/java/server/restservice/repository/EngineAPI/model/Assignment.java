package server.restservice.repository.EngineAPI.model;

import java.util.UUID;

public class Assignment {
  private UUID _id;

  private Long _date;

  private UUID _itemID;

  private UUID _actorID;

  public Assignment(UUID id, Long date, UUID itemID, UUID actorID) {
    _id = id;
    _date = date;
    _itemID = itemID;
    _actorID = actorID;
  }

  public UUID getId() {
    return _id;
  }

  public void setId(UUID id) {
    this._id = id;
  }

  public Long getDate() {
    return _date;
  }

  public void setDate(Long date) {
    this._date = date;
  }

  public UUID getItemID() {
    return _itemID;
  }

  public void setItemID(UUID itemID) {
    this._itemID = itemID;
  }

  public UUID getActorID() {
    return _actorID;
  }

  public void setActorID(UUID actorID) {
    this._actorID = actorID;
  }

}