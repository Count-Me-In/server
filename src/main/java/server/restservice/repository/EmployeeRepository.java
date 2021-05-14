package server.restservice.repository;

import java.time.Instant;
import java.util.Collections;
import java.util.Comparator;
import java.util.AbstractMap.SimpleEntry;
import java.util.concurrent.ConcurrentHashMap;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import server.restservice.models.Employee;

/**
 * EmployeeRepository
 */
@Repository
public class EmployeeRepository {

    // TODO: Get from configurations
    private static final int MAX_SIZE = 1000;

    private ConcurrentHashMap<String, SimpleEntry<Employee, Long>> _employee_cacheMap = new ConcurrentHashMap<String, SimpleEntry<Employee, Long>>();

    public Employee findEmployeeByUsername(String username) {
        SimpleEntry<Employee, Long> user;
        if ((user = _employee_cacheMap.get(username)) != null) {
            user.setValue(Instant.now().getEpochSecond());
            return user.getKey();
        } else {
            synchronized (this) {
                if (_employee_cacheMap.size() > MAX_SIZE) {
                    SimpleEntry<Employee, Long> userToRemove = Collections.min(_employee_cacheMap.values(),
                            new Comparator<SimpleEntry<Employee, Long>>() {
                                public int compare(SimpleEntry<Employee, Long> e1, SimpleEntry<Employee, Long> e2) {
                                    return (int) (e1.getValue() - e2.getValue());
                                }
                            });
                    _employee_cacheMap.remove(userToRemove.getKey().getUsername());
                }

                Employee empToAdd = getEmployeeFromSource(username);

                SimpleEntry<Employee, Long> userToAdd = new SimpleEntry<Employee, Long>(empToAdd,
                        Instant.now().getEpochSecond());
                _employee_cacheMap.put(empToAdd.getUsername(), userToAdd);
                return empToAdd;
            }
        }
    }

    public void save(Employee emp) {
        synchronized (emp) {
            // TODO: save employee in lambdas
        }
    }

    public String[] getAllEmployeeNames() {
        // TODO: get from lambdas
        ArrayList<String> names = new ArrayList<String>();
        for (SimpleEntry<Employee, Long> entry : _employee_cacheMap.values()) {
            names.add(entry.getKey().getName());
        }
        return (String[]) names.toArray();
    }

    private Employee getEmployeeFromSource(String username) {
        // TODO: get from lambdas
        return new Employee(username, username, "admin", 0, username.equals("admin"));
    }

}