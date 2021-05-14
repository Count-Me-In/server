package server.restservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import server.restservice.models.Assignings;
import server.restservice.models.Employee;
import server.restservice.models.Restriction;
import server.restservice.repository.EmployeeRepository;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ManagerService {

    private EmployeeRepository employeeRepository;

    public List<String> getAssignedEmployeesPerDay(String username) {
        // TODO: return type??
        return new ArrayList<>();
    }

    public void addRestriction(String username, Restriction restriction, String employee_username) {
        // TODO: what if employee has bid on the day
        Employee emp = employeeRepository.findEmployeeByUsername(employee_username);
        if (emp != null) {
            emp.writelock();

            if (!emp.getManager().equals(username)) {
                emp.writeunlock();
                throw new InvalidParameterException("Can't update employee");
            } else {
                emp.setRestrictions(restriction);
                emp.writeunlock();
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
        // TODO: change manager points?
        Employee emp = employeeRepository.findEmployeeByUsername(employee_username);
        if (emp != null) {
            emp.writelock();

            if (!emp.getManager().equals(username)) {
                emp.writeunlock();
                throw new InvalidParameterException("Can't update employee");
            } else {
                emp.setPoints(points);
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

            if (!emp.getManager().equals(username)) {
                emp.writeunlock();
                throw new InvalidParameterException("Can't update employee");
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
        // TODO: Add field to employee? Manager points?
        Employee emp = employeeRepository.findEmployeeByUsername(username);
        if (emp != null) {
            emp.readlock();
            int points = emp.getPoints();
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
                    output.put(direct.getUsername(), direct.getPoints());
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
