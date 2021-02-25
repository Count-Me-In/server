package server.restservice.models;

public class Bid {
    private final String _employee_username;
    private final Integer _day;
    private Integer _percentage;
    private Double _invested_points;

    public Bid(String employee_username, Integer day) {
        this._employee_username = employee_username;
        this._day = day;
        this._percentage = 0;
        this._invested_points = 0.0;
    }

}
