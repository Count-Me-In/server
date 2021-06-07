package server.restservice.repository;

import java.util.List;
import java.util.Map;

import server.restservice.models.Employee;

public interface EmployeeRepository {

    Employee findEmployeeByUsername(String username);

    void save(Employee emp);

    Employee[] getAllEmployeeNames();

    String getUsernamePass(String username);

    Map<String, List<Long>> execAuction();

}