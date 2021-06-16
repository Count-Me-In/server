package server.restservice;

import server.restservice.models.Assignings;
import server.restservice.models.Bid;
import server.restservice.models.Employee;
import server.restservice.models.Restriction;
import server.restservice.repository.EmployeeRepository;

import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.stereotype.Repository;

@Repository("repositoryMock")
public class EmployeeRepositoryMock implements EmployeeRepository {

    private List<Employee> employees;

    private Integer[] daysCapacity;

    public EmployeeRepositoryMock() {
        employees = new ArrayList<Employee>();

        Employee admin = generateEmployee("admin");
        this.employees.add(admin);
        Employee shauli = generateEmployee("shauli");
        this.employees.add(shauli);
        Employee noy = generateEmployee("noy");
        this.employees.add(noy);
        Employee shenhave = generateEmployee("shenhav");
        this.employees.add(shenhave);
        Employee nufar = generateEmployee("nufar");
        this.employees.add(nufar);
        Employee a = generateEmployee("a");
        this.employees.add(a);
        Employee toya = generateEmployee("toya");
        this.employees.add(toya);

        this.daysCapacity = new Integer[5];
        this.daysCapacity[0] = 10;
        this.daysCapacity[1] = 20;
        this.daysCapacity[2] = 30;
        this.daysCapacity[3] = 20;
        this.daysCapacity[4] = 10;



        Restriction rst = new Restriction();
        List<Integer> allowed = Arrays.asList(1, 2, 3);
        rst.set_allowed_days(allowed);

        noy.set_restrictions(rst);

    }

    private Employee generateEmployee(String username) {
        Employee user;

        if (username.equals("admin")) {
            user = new Employee(username, username, null, 100, 100, 300);
            user.get_employees().addAll(Arrays.asList("shauli", "noy", "shenhav", "nufar"));
        } else if (!username.equals("toya")) {
            user = new Employee(username, username, "admin", 100, 100, null);
        } else {
            user = new Employee(username, username, null, 100, 100, null);
        }
        Bid[] bids = { new Bid(username, 1), new Bid(username, 2), new Bid(username, 3), new Bid(username, 4),
                new Bid(username, 5) };
        user.set_bids(bids);

        try {
            Assignings as = new Assignings(username);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH");
            List<Long> lst = Arrays.asList(sdf.parse("2021-05-12T09:45").toInstant().getEpochSecond(),
                    sdf.parse("2021-05-19T09:45").toInstant().getEpochSecond(),
                    sdf.parse("2021-05-27T09:45").toInstant().getEpochSecond(),
                    sdf.parse("2021-05-28T09:45").toInstant().getEpochSecond(),
                    sdf.parse("2021-05-29T09:45").toInstant().getEpochSecond());
            as.addAssinedDays(lst);
            user.set_assignings(as);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Restriction rst = new Restriction();
        List<Integer> allowed = Arrays.asList(1, 2, 3, 4, 5);
        rst.set_allowed_days(allowed);

        user.set_restrictions(rst);
        return user;
    }

    public Employee findEmployeeByUsername(String username) {
        for (Employee emp : this.employees) {
            if (emp.get_username().equals(username)) {
                return emp;
            }
        }
        return null;

    }

    public void save(Employee emp) {
    }

    public Employee[] getAllEmployeeNames() {
        Employee[] employeeArr = this.employees.toArray(new Employee[this.employees.size()]);
        return employeeArr;
    }

    public String getUsernamePass(String username) {
        if (Arrays.asList("admin", "shauli", "nufar", "shenhav", "noy", "a", "toya").contains(username)) {
            return "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6";
        } else {
            return null;
        }
    }

    public Map<String, List<Long>> execAuction() {
        return null;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return this.employees;
    }

    @Override
    public void addEmployee(Employee emp, String string) {
        this.employees.add(emp);
    }

    @Override
    public void deleteEmployee(String username) {
        Employee toDelete = null;
        for(Employee emp: this.employees){
            if(emp.get_username() == username){
                toDelete = emp;
                break;
            }
        }

        if(toDelete != null)
            this.employees.remove(toDelete);
    }

    @Override
    public Integer[] getDays() {
        return this.daysCapacity;
    }

    @Override
    public void editDays(Integer[] days) {
        this.daysCapacity = days;
    }

}