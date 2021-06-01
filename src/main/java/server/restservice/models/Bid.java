package server.restservice.models;

import java.util.UUID;

public class Bid {
    private UUID _id;
    private String _employee_username;
    private Integer _day;
    private Integer _percentage;

    public Bid(String employee_username, Integer day) {
        this._employee_username = employee_username;
        this._day = day;
        this._percentage = 0;
    }

    public Bid(UUID id, String employee_username, Integer day) {
        this._id = id;
        this._employee_username = employee_username;
        this._day = day;
        this._percentage = 0;
    }

    public UUID getID() {
        return _id;
    }

    public String getUsername() {
        return _employee_username;
    }

    public Integer getDay() {
        return _day;
    }

    public Integer getPercentage() {
        return _percentage;
    }

    public void setPercentage(Integer percent) {
        _percentage = percent;
    }

    public void clearPoints() {
        _percentage = 0;
    }

}
