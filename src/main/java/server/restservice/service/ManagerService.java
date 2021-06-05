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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ManagerService {

    @Autowired
    @Qualifier("repositoryImplementation")
    private EmployeeRepository employeeRepository;

    public void addRestriction(String username, Restriction restriction, String employee_username) {
        Employee emp = employeeRepository.findEmployeeByUsername(employee_username);
        if (emp != null) {
            emp.writelock();
            if ((emp.getManager() == null )|| !emp.getManager().equals(username)) {
                emp.writeunlock();
                throw new InvalidParameterException("Can't update employee");
            } else {
                Bid[] empBids = emp.getBids();
                for (Bid bid : empBids) {
                    if (!restriction.get_allowed_days().contains(bid.getDay())) {
                        bid.clearPoints();
                    }
                }
                emp.setRestrictions(restriction);
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
            if ((emp.getManager() == null ) || !emp.getManager().equals(username)) {
                emp.readunlock();
                throw new InvalidParameterException("Can't access employee");
            } else {
                output = new Restriction(emp.getRestriction());
                emp.readunlock();
                return output;
            }
        } else {
            throw new InvalidParameterException("Employee username doesn't exists");
        }
    }

    public List<String> getEmployees(String username) {
        Employee emp = employeeRepository.findEmployeeByUsername(username);
        if (emp != null) {
            emp.readlock();
            List<String> employees = emp.getEmployees();
            emp.readunlock();
            return employees;
        } else {
            throw new InvalidParameterException("Employee username doesn't exists");
        }
    }

    public void setEmployeePoints(String username, String employee_username, Integer points) {
        Employee emp = employeeRepository.findEmployeeByUsername(employee_username);
        if (emp != null) {
            emp.writelock();

            if ((emp.getManager() == null ) || !emp.getManager().equals(username)) {
                emp.writeunlock();
                throw new InvalidParameterException("Can't update employee");
            } else {
                if (emp.isManager()) {
                    double ratio = points / emp.getManagerPoints();
                    emp.setManagerPoints(points);
                    emp.setWeeklyPoints((int) (emp.getWeeklyPoints() * ratio));
                    for (String empUsername : emp.getEmployees()) {
                        // TODO: improve
                        Employee dirEmp = employeeRepository.findEmployeeByUsername(empUsername);
                        dirEmp.readlock();
                        int newPoints = (int) (dirEmp.getWeeklyPoints() * ratio);
                        dirEmp.readunlock();
                        setEmployeePoints(employee_username, empUsername, newPoints);
                    }
                } else {
                    emp.setWeeklyPoints(points);
                }
                employeeRepository.save(emp);
                emp.writeunlock();
            }
        } else {
            throw new InvalidParameterException("Employee username doesn't exists");
        }
    }

    public Assignings getEmployeeAssignings(String username, String employeename) {
        Employee emp = employeeRepository.findEmployeeByUsername(employeename);
        if (emp != null) {
            emp.readlock();

            if ((emp.getManager() == null ) || !emp.getManager().equals(username)) {
                emp.readunlock();
                throw new InvalidParameterException("Can't access employee");
            } else {
                Assignings employee_assignings = emp.getAssignings();
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
            int points = emp.getManagerPoints();
            emp.readunlock();
            return points;
        } else {
            throw new InvalidParameterException("Employee username doesn't exists");
        }
    }

    public Map<String, Integer> getEmployeePoints(String username) {
        HashMap<String, Integer> output = new HashMap<>();
        Employee emp = employeeRepository.findEmployeeByUsername(username);
        if (emp != null) {
            emp.readlock();
            for (String emp_username : emp.getEmployees()) {
                Employee direct = employeeRepository.findEmployeeByUsername(emp_username);
                if (direct != null) {
                    direct.readlock();
                    output.put(direct.getUsername(), direct.getWeeklyPoints());
                    direct.readunlock();
                }
            }
            emp.readunlock();
            return output;
        } else {
            throw new InvalidParameterException("Employee username doesn't exists");
        }
    }

    public Map<String, Restriction> getEmployeeRestrictions(String username) {
        HashMap<String, Restriction> output = new HashMap<>();
        Employee emp = employeeRepository.findEmployeeByUsername(username);
        if (emp != null) {
            emp.readlock();
            for (String emp_username : emp.getEmployees()) {
                Employee direct = employeeRepository.findEmployeeByUsername(emp_username);
                if (direct != null) {
                    direct.readlock();
                    output.put(direct.getUsername(), direct.getRestriction());
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
