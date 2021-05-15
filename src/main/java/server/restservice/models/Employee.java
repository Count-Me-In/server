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
        this._name = name;      this._direct_manager = directed_manager;
        this._restrictions = new Restriction();
        this._bids_collection = new Bid[5];
        this._total_points = total_points;
        this._employees_list = new ArrayList<>();
        this._assignings = new Assignings(this._username);
    }


    public String getUsername() {
        return _username;
    }

    public void setUsername(String _username) {
        this._username = _username;
    }

    public String getName() {
        return _name;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    public String getDirectManager() {
        return _direct_manager;
    }

    public void setDirectManager(String _direct_manager) {
        this._direct_manager = _direct_manager;
    }

    public Restriction getRestrictions() {
        return _restrictions;
    }

    public void setRestrictions(Restriction _restrictions) {
        this._restrictions = _restrictions;
    }

    public Bid[] getBidsCollection() {
        return _bids_collection;
    }

    public void setBidsCollection(Bid[] _bids_collection) {
        this._bids_collection = _bids_collection;
    }

    public Integer getTotalPoints() {
        return _total_points;
    }

    public void setTotalPoints(Integer _total_points) {
        this._total_points = _total_points;
    }

    public List<String> getEmployeesList() {
        return _employees_list;
    }

    public void setEmployeesList(List<String> _employees_list) {
        this._employees_list = _employees_list;
    }

    public Assignings getAssignings() {
        return _assignings;
    }

    public void setAssignings(Assignings _assignings) {
        this._assignings = _assignings;
    }



}
