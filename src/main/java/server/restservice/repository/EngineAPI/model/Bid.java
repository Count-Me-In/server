
package server.restservice.repository.EngineAPI.model;

import java.util.Date;
import java.util.UUID;

public class Bid {
  private UUID _id;

  private Date _date;

  private UUID _itemID;

  private UUID _actorID;

  private Integer _percent;

  public Bid(UUID id, Date date, UUID itemID, UUID actorID, Integer percent) {
    _id = id;
    _date = date;
    _itemID = itemID;
    _actorID = actorID;
    _percent = percent;
  }

  public UUID getId() {
    return _id;
  }

  public void setId(UUID id) {
    this._id = id;
  }

  public Date getDate() {
    return _date;
  }

  public void setDate(Date date) {
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

  public Integer getPercentage() {
    return _percent;
  }

  public void setPercentage(Integer percent) {
    this._percent = percent;
  }

}