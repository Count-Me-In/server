package server.restservice.service;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.restservice.models.Assignings;
import server.restservice.models.Bid;
import server.restservice.models.Employee;
import server.restservice.models.EmployeeDetails;
import server.restservice.models.Restriction;
import server.restservice.repository.EmployeeRepository;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Bid[] getBids(String username) {
        Employee emp = employeeRepository.findEmployeeByUsername(username);
        if (emp != null) {
            emp.readlock();
            Bid[] bids = emp.get_bids();
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
            if (bid.get_percentage() < 0
                    || (bid.get_percentage() != 0 && !restriction.get_allowed_days().contains(bid.get_day()))) {
                return false;
            }
            sumPercent += bid.get_percentage();
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

            if (!checkValidBids(bids, username, emp.get_restrictions())) {
                emp.writeunlock();
                throw new InvalidParameterException("Bids aren't valid");
            } else {
                Bid[] newBids = emp.get_bids();
                for (Bid bid : bids) {
                    newBids[bid.get_day() - 1].set_percentage(bid.get_percentage());
                }
                emp.set_bids(newBids);
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
            int output = emp.get_total_points();
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
            Assignings output = new Assignings(emp.get_assignings());
            emp.readunlock();
            return output;
        } else {
            throw new InvalidParameterException("Employee username doesn't exists");
        }

    }

    public EmployeeDetails[] getEmployees(String username) {
        List<EmployeeDetails> details = Arrays.asList(employeeRepository.getAllEmployeeNames()).stream().parallel()
                .map(emp -> new EmployeeDetails(emp)).collect(Collectors.toList());
        EmployeeDetails[] output = new EmployeeDetails[details.size()];
        details.toArray(output);
        return output;
    }

    public void auction() {
        employeeRepository.execAuction();
    }

    public Restriction getEmployeesRestriction(String username) {
        Employee emp = employeeRepository.findEmployeeByUsername(username);
        if (emp != null) {
            emp.readlock();
            Restriction output = new Restriction(emp.get_restrictions());
            emp.readunlock();
            return output;
        } else {
            throw new InvalidParameterException("Employee username doesn't exists");
        }
    }
}
