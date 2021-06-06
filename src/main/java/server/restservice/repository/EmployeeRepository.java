package server.restservice.repository;

import server.restservice.models.Employee;

public interface EmployeeRepository {

    Employee findEmployeeByUsername(String username);

    void save(Employee emp);

    Employee[] getAllEmployeeNames();

    String getUsernamePass(String username);

    void execAuction();

}