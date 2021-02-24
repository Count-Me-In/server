package server.restservice.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Assignings {

    public UUID _employeeId;
    public List<Date> _assignedDays;

    public Assignings(UUID employeeId) {
        this._employeeId = employeeId;
        this._assignedDays = new ArrayList<>();
    }

    public UUID get_employeeId() {
        return this._employeeId;
    }

    public void set_employeeId(UUID employeeId) {
        this._employeeId = employeeId;
    }

    public List<Date> get_assignedDays() {
        return this._assignedDays;
    }

    public void set_assignedDays(List<Date> assignedDays) {
        this._assignedDays = assignedDays;
    }
}
