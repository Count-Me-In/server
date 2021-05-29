package server.restservice.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collections;
import java.util.Comparator;
import java.util.AbstractMap.SimpleEntry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.Arrays;

import server.restservice.models.Employee;

/**
 * EmployeeRepository
 */
@Repository
public class EmployeeRepository {

    @Value("${cacheSize}")
    private int MAX_SIZE;

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

                Employee empToAdd = _getEmployeeFromSource(username);

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

    public String getUsernamePass(String username) {
        // TODO: get from lambdas
        if (Arrays.asList("admin", "shauli", "nufar", "shenhav", "noy", "a").contains(username)) {
            return "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6";
        } else {
            return null;
        }
    }

    public void cleanCache() {
        synchronized (this) {
            _employee_cacheMap.clear();
        }
    }

    private Employee _getEmployeeFromSource(String username) {
        // TODO: get from lambdas
        return new Employee(username, username, "admin", 0, username.equals("admin"), 0);
    }

}