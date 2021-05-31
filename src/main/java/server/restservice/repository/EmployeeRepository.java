package server.restservice.repository;

import server.restservice.models.Assignings;
import server.restservice.models.Bid;
import server.restservice.models.Employee;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public interface EmployeeRepository {


    Employee findEmployeeByUsername(String username);

    void save(Employee emp);

    Employee[] getAllEmployeeNames();

    String getUsernamePass(String username);


}
