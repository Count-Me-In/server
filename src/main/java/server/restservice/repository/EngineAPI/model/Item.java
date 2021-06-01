package server.restservice.repository.EngineAPI.model;

import java.util.UUID;

public class Item {
   private UUID _id ;

   private Integer _capacity ;

   private Object _additionalInfo ;

  public Item(UUID id, Integer capacity, Object additionalInfo) {
    _id = id;
    _capacity = capacity;
    _additionalInfo = additionalInfo;
  }

  public UUID getId() {
    return _id;
  }

  public void setId(UUID id) {
    this._id = id;
  }

  public Integer getCapacity() {
    return _capacity;
  }

  public void setCapacity(Integer capacity) {
    this._capacity = capacity;
  }

  public Object getAdditionalInfo() {
    return _additionalInfo;
  }

  public void setAdditionalInfo(Object additionalInfo) {
    this._additionalInfo = additionalInfo;
  }

}