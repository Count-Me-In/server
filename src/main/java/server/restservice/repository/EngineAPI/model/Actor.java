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

import java.util.List;
import java.util.UUID;

/**
 * Represents an actor that is able to bid on items
 */
public class Actor {
  private UUID _id;

  private Integer _points;

  private Integer _intervalBonus;

  private ActorAdditionalData _additionalInfo;

  public Actor(UUID id, Integer points, Integer intervalBonus, String username, String name, String manager,
      Integer managerPoints, List<Integer> allowed_days, List<String> employees, String password) {
    this._id = id;
    this._points = points;
    this._intervalBonus = intervalBonus;
    this._additionalInfo = new ActorAdditionalData(username, name, manager, managerPoints, allowed_days, employees, password);
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

  public ActorAdditionalData getAdditionalInfo() {
    return _additionalInfo;
  }

  public void setAdditionalInfo(ActorAdditionalData additionalInfo) {
    this._additionalInfo = additionalInfo;
  }

  public class ActorAdditionalData {
    private String _username;
    private String _password;
    private String _name;
    private String _directed_manager;
    private Integer _totalManagerPoints;
    private List<Integer> _allowed_days;
    private List<String> _employees;

    public ActorAdditionalData(String username, String name, String manager, Integer managerPoints,
        List<Integer> allowed_days, List<String> employees, String password) {
      _username = username;
      _name = name;
      _directed_manager = manager;
      _totalManagerPoints = managerPoints;
      _allowed_days = allowed_days;
      _employees = employees;
      _password = password;
    }

    public String getUsername() {
      return _username;
    }

    public String getPassword() {
      return _password;
    }

    public void setPassword(String password) {
      _password = password;
    }

    public String getName() {
      return _name;
    }

    public String getManager() {
      return _directed_manager;
    }

    public int getManagerPoints() {
      return _totalManagerPoints;
    }

    public List<Integer> getAllowedDays() {
      return _allowed_days;
    }

    public List<String> getEmployees() {
      return _employees;
    }
  }

}
