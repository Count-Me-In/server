package server.restservice.service;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import server.restservice.models.Assignings;
import server.restservice.models.Bid;
import server.restservice.models.Employee;
import server.restservice.models.Restriction;
import server.restservice.repository.EmployeeRepository;

import java.security.InvalidParameterException;

@Service
@AllArgsConstructor
public class EmployeeService {

    @Autowired
    @Qualifier("repositoryImplementation")
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

    private boolean checkValidBids(Bid[] bids, String username, Restriction restriction) {
        if (bids.length != 5) {
            return false;
        }
        Integer sumPercent = 0;
        for (Bid bid : bids) {
            if (bid.getPercentage() > 0 && !restriction.get_allowed_days().contains(bid.getDay())) {
                return false;
            }
            sumPercent += bid.getPercentage();
        }
        if (sumPercent > 100) {
            return false;
        }
        return true;
    }

    public void updateBids(String username, Bid[] bids) {
        Employee emp = employeeRepository.findEmployeeByUsername(username);
        if (emp != null) {
            emp.writelock();

            if (!checkValidBids(bids, username, emp.getRestriction())) {
                emp.writeunlock();
                throw new InvalidParameterException("Bids aren't valid");
            } else {
                Bid[] newBids = emp.getBids();
                for (Bid bid : bids) {
                    newBids[bid.getDay() - 1].setPercentage(bid.getPercentage());
                }
                emp.setBids(newBids);
                employeeRepository.save(emp);
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
            Assignings output = new Assignings(emp.getAssignings());
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
