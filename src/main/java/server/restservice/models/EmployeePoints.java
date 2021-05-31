package server.restservice.models;

public class EmployeePoints {
    private String _name;
    private Integer _points;

    public EmployeePoints(String name, Integer points) {
        this._name = name;
        this._points = points;
    }

    public String getName() {
        return _name;
    }

    public int getPoints() {
        return _points;
    }
}
