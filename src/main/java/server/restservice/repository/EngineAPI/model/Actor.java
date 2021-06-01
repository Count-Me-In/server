/*
 * Bidding Engine API
 * API for the CountMeIn Bidding Engine *
 * OpenAPI spec version: 1.0.0
 * Contact: countMeInTheOffice@gmail.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package server.restservice.repository.EngineAPI.model;

import java.util.UUID;

/**
 * Represents an actor that is able to bid on items */
public class Actor {
  private UUID _id;

  private Integer _points;

  private Integer _intervalBonus;

  private Object _additionalInfo;

  public Actor(UUID id, Integer points, Integer intervalBonus, Object additionalInfo) {
    this._id = id;
    this._points = points;
    this._intervalBonus = intervalBonus;
    this._additionalInfo = additionalInfo;
  }

  public UUID getId() {
    return _id;
  }

  public void setId(UUID id) {
    this._id = id;
  }

  public Integer getPoints() {
    return _points;
  }

  public void setPoints(Integer points) {
    this._points = points;
  }

  public Integer getIntervalBonus() {
    return _intervalBonus;
  }

  public void setIntervalBonus(Integer intervalBonus) {
    this._intervalBonus = intervalBonus;
  }


  public Object getAdditionalInfo() {
    return _additionalInfo;
  }

  public void setAdditionalInfo(Object additionalInfo) {
    this._additionalInfo = additionalInfo;
  }


}
