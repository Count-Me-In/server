package server.restservice.repository.EngineAPI.model;

import java.util.UUID;

public class Item {
   private UUID _id ;

   private Integer _capacity ;

   private ItemAdditionalData _additionalInfo ;

  public Item(UUID id, Integer capacity, ItemAdditionalData additionalInfo) {
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

  public ItemAdditionalData getAdditionalInfo() {
    return _additionalInfo;
  }

  public void setAdditionalInfo(ItemAdditionalData additionalInfo) {
    this._additionalInfo = additionalInfo;
  }

  
  public class ItemAdditionalData {
    private Integer _day;

    public Integer getDay() {
        return _day;
    }
}

}