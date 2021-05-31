package server.restservice.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Assignings {

    public String _employeeUsername;
    public List<Date> _assignedDays;

    public Assignings(String employeeUsername) {
        this._employeeUsername = employeeUsername;
        this._assignedDays = new ArrayList<>();
    }

    public void addAssinedDays(List<Date> dates){
        this._assignedDays.addAll(dates);
    }


    public List<Date> getAssignedDays(){
        return this._assignedDays;
    }
}
