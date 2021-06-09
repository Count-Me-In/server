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
        this._username = employee.get_username();
        this._name = employee.get_name();
        this._points = employee.get_total_points();
        this._restriction = employee.get_restrictions();
    }

    public String get_username() {
        return _username;
    }

    public void set_username(String _username) {
        this._username = _username;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public Restriction get_restriction() {
        return _restriction;
    }

    public void set_restrictions(Restriction restriction) {
        _restriction = restriction;
    }

    public Integer get_points() {
        return _points;
    }

    public void set_points(Integer points) {
        _points = points;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmployeeDetails ed = (EmployeeDetails) o;

        return (this._name.equals(ed._name) && (this._points == ed._points)
                && (this._username.equals(ed._username)));
    }
}
