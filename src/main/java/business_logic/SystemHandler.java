package business_logic;

import business_logic.models.Assignings;
import business_logic.models.Bid;
import business_logic.models.Employee;
import business_logic.models.Restriction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class SystemHandler {

    public static Bid[] getBids(UUID employee_id) {
        //TODO: get employee id from session
        return new Bid[5];
    }

    public static void updateBids(UUID employee_id, Bid[] bids) {
        //TODO: update employee bids field and save employee to db
    }

    public static List<String> getAssignedEmployees(Date date) {
        //ToDo: Check if user is manager --> fetch all employee's names from engine
        return new ArrayList<>();
    }

    public static int getEmployeesPoints(UUID employee_id) {
        //TODO: call to lambda that retrieves employee's points
        return 0;
    }



    public static List<Assignings> getEmployeeAssignings(UUID employee_id) {
        //TODO: call engine
        return new ArrayList<>();
    }


    public static void addRestriction(UUID employee_id, Restriction restriction) {
        //TODO: check if the user is the manager of this employee, set employee restriction
    }

    public static List<Employee> getEmployees(UUID manager_id) {
        //TODO: return a list of the manager's employees
        return new ArrayList<>();
    }

    public static void setEmployeePoints(UUID employee_id, Integer points) {
        //TODO
    }

    public static void planArrival(UUID employee_id, Integer day) {
        //TODO
    }
}
