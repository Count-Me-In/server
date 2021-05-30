package server.restservice.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.concurrent.ConcurrentHashMap;

import server.restservice.models.Assignings;
import server.restservice.models.Bid;
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

    public Employee[] getAllEmployeeNames() {
        // TODO: get from lambdas
        ArrayList<Employee> names = new ArrayList<Employee>();
        for (SimpleEntry<Employee, Long> entry : _employee_cacheMap.values()) {
            names.add(entry.getKey());
        }
        return (Employee[]) names.toArray();
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
        if (!Arrays.asList("admin", "shauli", "nufar", "shenhav", "noy", "a").contains(username)) {
            return null;
        }
        Employee emp;
        if (username.equals("admin")) {
            emp = new Employee(username, username, null, 100, 100);
            emp.getEmployees().addAll(Arrays.asList("shauli", "nufar", "shenhav", "noy", "a"));

            Bid[] bids = {new Bid(username, 0), new Bid(username, 1), new Bid(username, 2), new Bid(username, 3), new Bid(username, 4)};
            emp.setBids(bids);

            try {
                Assignings as = new Assignings(username);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH");
                List<Date> lst = Arrays.asList(sdf.parse("2021-05-12T09:45"),
                        sdf.parse("2021-05-19T09:45"),
                        sdf.parse("2021-05-27T09:45"),
                        sdf.parse("2021-05-28T09:45"),
                        sdf.parse("2021-05-29T09:45"));
                as.addAssinedDays(lst);
                emp.setAssignings(as);
            }
            catch(Exception e){
                e.printStackTrace();
            }

        } else {
            emp = new Employee(username, username, "admin", 0, 0);
            if(username.equals("Shauli")){
                Assignings as = new Assignings(username);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH");
                List<Date> lst = null;
                try {
                    lst = Arrays.asList(sdf.parse("2021-05-12T09:45"),
                            sdf.parse("2021-05-11T09:45"),
                            sdf.parse("2021-05-22T09:45"),
                            sdf.parse("2021-05-01T09:45"),
                            sdf.parse("2021-05-09T09:45"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                as.addAssinedDays(lst);
                emp.setAssignings(as);

            }
        }
        return emp;
    }

}