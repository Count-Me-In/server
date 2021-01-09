package business_logic;

import business_logic.models.Assignings;
import business_logic.models.Bid;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class SystemHandler {

    public static Bid[] getBids(UUID employee_id) {
        //TODO: get employee id from session
        return new Bid[5];
    }

    public static List<String> getAssignedEmployees(Date date) {
        //ToDo: Check if user is manager --> fetch all employee's names from engine
        return new ArrayList<>();
    }

    public static int getEmployeesPoints(UUID employee_id) {
        //TODO: call to lambda that retrieves employee's points
        return 0;
    }

    public static void updateBids(UUID employee_id, Bid[] bids) {
        //TODO: update employee bids field and save employee to db
    }

    public static List<Assignings> getEmployeeAssignings(UUID employee_id) {
        //TODO: call engine
        return new ArrayList<>();
    }


}
