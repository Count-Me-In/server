package server.restservice.models;

import java.util.ArrayList;
import java.util.List;

public class Assignings {

    public String _employeeUsername;
    public List<Long> _assignedDays;

    public Assignings(String employeeUsername) {
        this._employeeUsername = employeeUsername;
        this._assignedDays = new ArrayList<>();
    }

    public Assignings(Assignings other) {
        this._employeeUsername = other._employeeUsername;
        this._assignedDays = new ArrayList<Long>(other._assignedDays);
    }

    public void addAssinedDays(List<Long> dates) {
        this._assignedDays.addAll(dates);
    }

    public List<Long> getAssignedDays() {
        return this._assignedDays;
    }
}
