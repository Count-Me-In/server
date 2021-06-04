package server.restservice.models;

import java.util.UUID;

public class Bid {
    private UUID _id;
    private String _employee_username;
    private Integer _day;
    private Integer _percentage;

    public Bid() {
    }

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

    public void setID(UUID id) {
        this._id = id;
    }

    public String getUsername() {
        return _employee_username;
    }

    public void setUsername(String username) {
        this._employee_username = username;
    }

    public Integer getDay() {
        return _day;
    }

    public void setDay(Integer day) {
        this._day = day;
    }

    public Integer getPercentage() {
        return _percentage;
    }

    public void setPercentage(Integer percentage) {
        _percentage = percentage;
    }

    public void clearPoints() {
        _percentage = 0;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Bid b = (Bid) o;

        return (this._employee_username.equals(b._employee_username) && (this._day == b._day)
                && (this._percentage == b._percentage));
    }

}
