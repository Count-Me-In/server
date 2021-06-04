package server.restservice.models;

public class Bid {
    private String _employee_username;
    private Integer _day;
    private Integer _percentage;
    private Double _invested_points;

    public Bid(String employee_username, Integer day) {
        this._employee_username = employee_username;
        this._day = day;
        this._percentage = 0;
        this._invested_points = 0.0;
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

    public Double getPoints() {
        return _invested_points;
    }

    public void clearPoints() {
        _percentage = 0;
        _percentage = 0;
    }

    public void setPercentage(int percentage){
        this._percentage = percentage;
    }

    public void setPoints(double points) {
        this._invested_points = points;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Bid b = (Bid)o;

        return (this._employee_username.equals(b._employee_username) && (this._day == b._day) && (this._percentage == b._percentage));
    }
}
