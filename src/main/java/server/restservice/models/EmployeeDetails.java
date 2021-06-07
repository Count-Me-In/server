package server.restservice.models;

public class EmployeeDetails {
    private String _username;
    private String _name;
    private Restriction _restriction;
    private Integer _points;

    // Constructor
    public EmployeeDetails(String username, String name) {
        this._username = username;
        this._name = name;
        this._restriction = null;
        this._points = null;
    }

    public EmployeeDetails(Employee employee) {
        this._username = employee.getUsername();
        this._name = employee.getName();
    }

    public String getUsername() {
        return _username;
    }

    public String getName() {
        return _name;
    }

    public Restriction getRestriction() {
        return _restriction;
    }

    public void setRestrictions(Restriction restriction) {
        _restriction = restriction;
    }

    public Integer getPoints() {
        return _points;
    }

    public void setPoints(Integer points) {
        _points = points;
    }

}
