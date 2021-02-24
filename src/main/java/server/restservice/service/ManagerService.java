package server.restservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import server.restservice.models.Employee;
import server.restservice.models.Restriction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ManagerService {
    public List<String> getAssignedEmployees(Date date) {
        //ToDo: Check if user is manager --> fetch all employee's names from engine
        return new ArrayList<>();
    }

    public void addRestriction(UUID employee_id, Restriction restriction) {
        //TODO: check if the user is the manager of this employee, set employee restriction
    }

    public List<Employee> getEmployees(UUID manager_id) {
        //TODO: return a list of the manager's employees
        return new ArrayList<>();
    }

    public void setEmployeePoints(UUID employee_id, Integer points) {
        //TODO
    }

    public void planArrival(UUID employee_id, Integer day) {
        //TODO
    }
}
