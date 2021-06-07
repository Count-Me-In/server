package server.restservice.service;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Qualifier("repositoryImplementation")
    private EmployeeRepository employeeRepository;

    public void addRestriction(String username, Restriction restriction, String employee_username) {
        Employee emp = employeeRepository.findEmployeeByUsername(employee_username);
        if (emp != null) {
            emp.writelock();
            if ((emp.getManager() == null) || !emp.getManager().equals(username)) {
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
            if ((emp.getManager() == null) || !emp.getManager().equals(username)) {
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

    public List<EmployeeDetails> getEmployees(String username) {
        Employee emp = employeeRepository.findEmployeeByUsername(username);
        if (emp != null) {
            emp.readlock();
            List<String> employees = new ArrayList<String>(emp.getEmployees());
            emp.readunlock();
            Employee[] all_employees = employeeRepository.getAllEmployeeNames();
            List<EmployeeDetails> output = new ArrayList<EmployeeDetails>();
            for (Employee employee : all_employees) {
                if (employees.contains(employee.getUsername())) {
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
            double ratio = points / emp.getManagerPoints();
            emp.setManagerPoints(points);
            for (String empUsername : emp.getEmployees()) {
                Employee dirEmp = employeeRepository.findEmployeeByUsername(empUsername);
                dirEmp.writelock();
                int oldPoints = dirEmp.getWeeklyPoints();
                int newPoints = (int) (oldPoints * ratio);
                _setPoints(emp, newPoints);
                dirEmp.writeunlock();
                emp.setWeeklyPoints(oldPoints - points + emp.getWeeklyPoints());
            }
        } else {
            emp.setWeeklyPoints(points);
        }
        employeeRepository.save(emp);
    }

    public void setEmployeePoints(String username, String employee_username, Integer points) {
        Employee manager = employeeRepository.findEmployeeByUsername(username);
        if (manager != null) {
            manager.writelock();
            if (!manager.getEmployees().contains(employee_username)) {
                manager.writeunlock();
                throw new InvalidParameterException("Can't update employee");
            }
            Employee emp = employeeRepository.findEmployeeByUsername(employee_username);
            emp.writelock();
            int old_points = emp.getWeeklyPoints();
            _setPoints(emp, points);
            emp.writeunlock();
            manager.setWeeklyPoints(old_points - points + manager.getWeeklyPoints());
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

            if ((emp.getManager() == null) || !emp.getManager().equals(username)) {
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

    public List<EmployeeDetails> getEmployeePoints(String username) {
        List<EmployeeDetails> output = new ArrayList<>();
        Employee emp = employeeRepository.findEmployeeByUsername(username);
        if (emp != null) {
            emp.readlock();
            for (String emp_username : emp.getEmployees()) {
                Employee direct = employeeRepository.findEmployeeByUsername(emp_username);
                if (direct != null) {
                    direct.readlock();
                    EmployeeDetails details = new EmployeeDetails(direct);
                    details.setPoints(direct.getWeeklyPoints());
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
            for (String emp_username : emp.getEmployees()) {
                Employee direct = employeeRepository.findEmployeeByUsername(emp_username);
                if (direct != null) {
                    direct.readlock();
                    EmployeeDetails details = new EmployeeDetails(direct);
                    details.setRestrictions(new Restriction(direct.getRestriction()));
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
