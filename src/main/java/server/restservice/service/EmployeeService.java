package server.restservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import server.restservice.models.Assignings;
import server.restservice.models.Bid;
import server.restservice.models.Employee;
import server.restservice.models.Restriction;
import server.restservice.repository.EmployeeRepository;
import server.restservice.repository.EmployeeRepositoryImpl;

import java.security.InvalidParameterException;

@Service
@AllArgsConstructor
public class EmployeeService {


    private EmployeeRepository employeeRepository;

    public Bid[] getBids(String username) {
        Employee emp = employeeRepository.findEmployeeByUsername(username);
        if (emp != null) {
            emp.readlock();
            Bid[] bids = emp.getBids();
            emp.readunlock();
            return bids;
        } else {
            throw new InvalidParameterException("Employee username doesn't exists");
        }
    }

    private boolean checkValidBids(Bid[] bids, String username, Restriction restriction, Integer points) {
        Double sumPoints = 0.0;
        Integer sumPercent = 0;
        for (Bid bid : bids) {
            if (!bid.getUsername().equals(username) || !restriction.get_allowed_days().contains(bid.getDay())) {
                return false;
            }
            sumPoints += bid.getPoints();
            sumPercent += bid.getPercentage();
        }
        if (sumPercent != 100 || Math.abs(sumPoints - points) > 1) {
            return false;
        }
        return true;
    }

    public void updateBids(String username, Bid[] bids) {
        Employee emp = employeeRepository.findEmployeeByUsername(username);
        if (emp != null) {
            emp.writelock();

            if (!checkValidBids(bids, username, emp.getRestriction(), emp.getTotalPoints())) {
                emp.writelock();
                throw new InvalidParameterException("Bids aren't valid");
            } else {
                emp.setBids(bids);
                emp.writeunlock();
            }
        } else {
            throw new InvalidParameterException("Employee username doesn't exists");
        }
    }

    public int getEmployeesPoints(String username) {
        Employee emp = employeeRepository.findEmployeeByUsername(username);
        if (emp != null) {
            emp.readlock();
            int output = emp.getTotalPoints();
            emp.readunlock();
            return output;
        } else {
            throw new InvalidParameterException("Employee username doesn't exists");
        }
    }

    public Assignings getEmployeeAssignings(String username) {
        Employee emp = employeeRepository.findEmployeeByUsername(username);
        if (emp != null) {
            emp.readlock();
            Assignings output = emp.getAssignings();
            emp.readunlock();
            return output;
        } else {
            throw new InvalidParameterException("Employee username doesn't exists");
        }

    }

    public Employee[] getEmployees(String username) {
        return employeeRepository.getAllEmployeeNames();
    }
}
