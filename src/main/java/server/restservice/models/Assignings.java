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

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Assignings ass = (Assignings)o;

        return (this._employeeUsername.equals(ass._employeeUsername) && this._assignedDays.equals(ass._assignedDays));

    }
}
