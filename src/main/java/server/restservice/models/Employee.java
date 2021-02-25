package server.restservice.models;

import java.util.ArrayList;
import java.util.List;

public class Employee {
    private String _username;
    private String _name;
    private String _direct_manager;
    private Restriction _restrictions;
    private Bid[] _bids_collection;
    private Integer _total_points;
    private List<String> _employees_list;
    private Assignings _assignings;

    public Employee(String username, String name, String directed_manager, Integer total_points, Boolean is_manager) {
        this._username = username;
        this._name = name;
        this._direct_manager = directed_manager;
        this._restrictions = new Restriction();
        this._bids_collection = new Bid[5];
        this._total_points = total_points;
        this._employees_list = new ArrayList<>();
        this._assignings = new Assignings(this._username);
    }

}
