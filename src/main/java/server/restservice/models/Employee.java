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


    public String get_username() {
        return _username;
    }

    public void set_username(String _username) {
        this._username = _username;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_direct_manager() {
        return _direct_manager;
    }

    public void set_direct_manager(String _direct_manager) {
        this._direct_manager = _direct_manager;
    }

    public Restriction get_restrictions() {
        return _restrictions;
    }

    public void set_restrictions(Restriction _restrictions) {
        this._restrictions = _restrictions;
    }

    public Bid[] get_bids_collection() {
        return _bids_collection;
    }

    public void set_bids_collection(Bid[] _bids_collection) {
        this._bids_collection = _bids_collection;
    }

    public Integer get_total_points() {
        return _total_points;
    }

    public void set_total_points(Integer _total_points) {
        this._total_points = _total_points;
    }

    public List<String> get_employees_list() {
        return _employees_list;
    }

    public void set_employees_list(List<String> _employees_list) {
        this._employees_list = _employees_list;
    }

    public Assignings get_assignings() {
        return _assignings;
    }

    public void set_assignings(Assignings _assignings) {
        this._assignings = _assignings;
    }



}
