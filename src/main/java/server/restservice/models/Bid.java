package server.restservice.models;

public class Bid {
    public String _employee_username;
    public Integer _day;
    public Integer _percentage;
    public Double _invested_points;

    public Bid(String employee_username, Integer day) {
        this._employee_username = employee_username;
        this._day = day;
        this._percentage = 0;
        this._invested_points = 0.0;
    }

}
