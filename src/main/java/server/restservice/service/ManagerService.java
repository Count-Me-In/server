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
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ManagerService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public void addRestriction(String username, Restriction restriction, String employee_username) {
        Employee emp = employeeRepository.findEmployeeByUsername(employee_username);
        if (emp != null) {
            emp.writelock();
            if ((emp.get_manager() == null) || !emp.get_manager().equals(username)) {
                emp.writeunlock();
                throw new InvalidParameterException("Can't update employee");
            } else {
                Bid[] empBids = emp.get_bids();
                for (Bid bid : empBids) {
                    if (!restriction.get_allowed_days().contains(bid.get_day())) {
                        bid.clearPoints();
                    }
                }
                emp.set_restrictions(restriction);
                employeeRepository.save(emp);
                emp.writeunlock();
            }
        } else {
            throw new InvalidParameterException("Employee username doesn't exists");
        }
    }

    public Restriction getRestriction(String username, String employee_username) {
        Restriction output;
        Employee emp = employeeRepository.findEmployeeByUsername(employee_username);
        if (emp != null) {
            emp.readlock();
            if ((emp.get_manager() == null) || !emp.get_manager().equals(username)) {
                emp.readunlock();
                throw new InvalidParameterException("Can't access employee");
            } else {
                output = new Restriction(emp.get_restrictions());
                emp.readunlock();
                return output;
            }
        } else {
            throw new InvalidParameterException("Employee username doesn't exists");
        }
    }

    public List<EmployeeDetails> getEmployees(String username) {
        Employee emp = employeeRepository.findEmployeeByUsername(username);
        if (emp != null) {
            emp.readlock();
            List<String> employees = new ArrayList<String>(emp.get_employees());
            emp.readunlock();
            Employee[] all_employees = employeeRepository.getAllEmployeeNames();
            List<EmployeeDetails> output = new ArrayList<EmployeeDetails>();
            for (Employee employee : all_employees) {
                if (employees.contains(employee.get_username())) {
                    output.add(new EmployeeDetails(employee));
                }
            }
            return output;
        } else {
            throw new InvalidParameterException("Employee username doesn't exists");
        }
    }

    private void _setPoints(Employee emp, Integer points) {
        if (emp.isManager()) {
            double ratio = points / emp.get_manager_points();
            emp.set_manager_points(points);
            for (String empUsername : emp.get_employees()) {
                Employee dirEmp = employeeRepository.findEmployeeByUsername(empUsername);
                dirEmp.writelock();
                int oldPoints = dirEmp.get_weekly_added_points();
                int newPoints = (int) (oldPoints * ratio);
                _setPoints(emp, newPoints);
                dirEmp.writeunlock();
                emp.set_weekly_added_points(oldPoints - points + emp.get_weekly_added_points());
            }
        } else {
            emp.set_weekly_added_points(points);
        }
        employeeRepository.save(emp);
    }

    public void setEmployeePoints(String username, String employee_username, Integer points) {
        Employee manager = employeeRepository.findEmployeeByUsername(username);
        if (manager != null) {
            manager.writelock();
            if (!manager.get_employees().contains(employee_username)) {
                manager.writeunlock();
                throw new InvalidParameterException("Can't update employee");
            }
            Employee emp = employeeRepository.findEmployeeByUsername(employee_username);
            emp.writelock();
            int old_points = emp.get_weekly_added_points();
            _setPoints(emp, points);
            emp.writeunlock();
            manager.set_weekly_added_points(old_points - points + manager.get_weekly_added_points());
            employeeRepository.save(manager);
            manager.writeunlock();
        } else {
            throw new InvalidParameterException("Employee username doesn't exists");
        }
    }

    public Assignings getEmployeeAssignings(String username, String employeename) {
        Employee emp = employeeRepository.findEmployeeByUsername(employeename);
        if (emp != null) {
            emp.readlock();

            if ((emp.get_manager() == null) || !emp.get_manager().equals(username)) {
                emp.readunlock();
                throw new InvalidParameterException("Can't access employee");
            } else {
                Assignings employee_assignings = emp.get_assignings();
                emp.readunlock();
                return employee_assignings;
            }
        } else {
            throw new InvalidParameterException("Employee username doesn't exists");
        }
    }

    public int getTotalPoints(String username) {
        Employee emp = employeeRepository.findEmployeeByUsername(username);
        if (emp != null) {
            emp.readlock();
            int points = emp.get_manager_points();
            emp.readunlock();
            return points;
        } else {
            throw new InvalidParameterException("Employee username doesn't exists");
        }
    }

    public List<EmployeeDetails> getEmployeePoints(String username) {
        List<EmployeeDetails> output = new ArrayList<>();
        Employee emp = employeeRepository.findEmployeeByUsername(username);
        if (emp != null) {
            emp.readlock();
            for (String emp_username : emp.get_employees()) {
                Employee direct = employeeRepository.findEmployeeByUsername(emp_username);
                if (direct != null) {
                    direct.readlock();
                    EmployeeDetails details = new EmployeeDetails(direct);
                    details.set_points(direct.get_weekly_added_points());
                    output.add(details);
                    direct.readunlock();
                }
            }
            emp.readunlock();
            return output;
        } else {
            throw new InvalidParameterException("Employee username doesn't exists");
        }
    }

    public List<EmployeeDetails> getEmployeeRestrictions(String username) {
        List<EmployeeDetails> output = new ArrayList<>();
        Employee emp = employeeRepository.findEmployeeByUsername(username);
        if (emp != null) {
            emp.readlock();
            for (String emp_username : emp.get_employees()) {
                Employee direct = employeeRepository.findEmployeeByUsername(emp_username);
                if (direct != null) {
                    direct.readlock();
                    EmployeeDetails details = new EmployeeDetails(direct);
                    details.set_restrictions(new Restriction(direct.get_restrictions()));
                    output.add(details);
                    direct.readunlock();
                }
            }
            emp.readunlock();
            return output;
        } else {
            throw new InvalidParameterException("Employee username doesn't exists");
        }
    }
}
