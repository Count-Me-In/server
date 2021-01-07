package business_logic.models;

public class Bid {
    private final String _employee_id;
    private final Integer _day;
    private Integer _percentage;
    private Double _invested_points;

    public Bid(String employee_id, Integer day) {
        this._employee_id = employee_id;
        this._day = day;
        this._percentage = 0;
        this._invested_points = 0.0;
    }

    public String get_employee_id() {
        return _employee_id;
    }

    public Integer get_percentage() {
        return _percentage;
    }

    public void set_percentage(Integer _percentage) {
        this._percentage = _percentage;
    }

    public Integer get_day() {
        return _day;
    }

    public Double get_invested_points() {
        return _invested_points;
    }

    public void set_invested_points(Double _invested_points) {
        this._invested_points = _invested_points;
    }
}
