package server.restservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import server.restservice.models.Restriction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ManagerService {
    public List<String> getAssignedEmployees(String username, Date date) {
        //ToDo: Check if user is manager --> fetch all employee's names from engine
        return new ArrayList<>();
    }

    public void addRestriction(String username, Restriction restriction, String employee_username) {
        //TODO: check if the user is the manager of this employee, set employee restriction
    }

    public List<String> getEmployees(String username) {
        //TODO: return a list of the manager's employees
        return new ArrayList<>();
    }

    public void setEmployeePoints(String username, String employee_username, Integer points) {
        //TODO
    }

    public void planArrival(String username, Integer day) {
        //TODO
    }
}
