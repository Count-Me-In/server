package server.restservice.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Employee {
    private UUID _id;
    private String _username;
    private String _name;
    private String _direct_manager;
    private Restriction _restrictions;
    private Bid[] _bids_collection;
    private Integer _total_points;
    private Integer _weekly_added_points;
    private List<String> _employees_list;
    private Assignings _assignings;
    private Integer _totalManagerPoints;

    // Synchronization lock
    private ReentrantReadWriteLock _lock;

    // Constructor
    public Employee(String username, String name, String directed_manager, Integer total_points,
            Integer weekly_added_points, Integer totalManagerPoints) {
        this._username = username;
        this._name = name;
        this._direct_manager = directed_manager;
        this._restrictions = new Restriction();
        this._bids_collection = new Bid[5];
        this._total_points = total_points;
        this._weekly_added_points = weekly_added_points;
        this._employees_list = new ArrayList<>();
        this._assignings = new Assignings(this._username);
        this._totalManagerPoints = totalManagerPoints;
        this._lock = new ReentrantReadWriteLock();
    }

    public Employee(UUID id, String username, String name, String directed_manager, Integer total_points,
            Integer weekly_added_points, Integer totalManagerPoints) {
        _id = id;
        this._username = username;
        this._name = name;
        this._direct_manager = directed_manager;
        this._restrictions = new Restriction();
        this._bids_collection = new Bid[5];
        this._total_points = total_points;
        this._weekly_added_points = weekly_added_points;
        this._employees_list = new ArrayList<>();
        this._assignings = new Assignings(this._username);
        this._totalManagerPoints = totalManagerPoints;
        this._lock = new ReentrantReadWriteLock();
    }

    public Employee(String username, String name) {
        this._username = username;
        this._name = name;
        this._direct_manager = "";
        this._restrictions = new Restriction();
        this._bids_collection = new Bid[5];
        this._total_points = 0;
        this._weekly_added_points = 0;
        this._employees_list = new ArrayList<>();
        this._assignings = new Assignings(this._username);
        this._totalManagerPoints = 0;
        this._lock = new ReentrantReadWriteLock();
    }

    public Employee(){
        this._username = null;
        this._name = null;
        this._direct_manager = "";
        this._restrictions = new Restriction();
        this._bids_collection = new Bid[5];
        this._total_points = 0;
        this._weekly_added_points = 0;
        this._employees_list = new ArrayList<>();
        this._assignings = new Assignings(this._username);
        this._totalManagerPoints = 0;
        this._lock = new ReentrantReadWriteLock();
    }

    // Getters and Setters
    public UUID get_id() {
        return _id;
    }

    public String get_username() {
        return _username;
    }

    public void set_username(String username) {
        this._username = username;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String name) {
        this._name = name;
    }

    public String get_manager() {
        return _direct_manager;
    }

    public void set_manager(String manager) {
        this._direct_manager = manager;
    }

    public int get_total_points() {
        return _total_points;
    }

    public void set_total_points(int points) {
        this._total_points = points;
    }

    public int get_weekly_added_points() {
        return _weekly_added_points;
    }

    public void set_weekly_added_points(int points) {
        _weekly_added_points = points;
    }

    public List<String> get_employees() {
        return _employees_list;
    }

    public void set_employees(List<String> emp) {
        this._employees_list = emp;
    }

    public Restriction get_restriction() {
        return _restrictions;
    }

    public void set_restrictions(Restriction restriction) {
        _restrictions = restriction;
    }

    public Bid[] get_bids() {
        return _bids_collection;
    }

    public void set_bids(Bid[] bids) {
        _bids_collection = bids;
    }

    public Assignings get_assignings() {
        return _assignings;
    }

    public void set_assignings(Assignings as) {
        _assignings = as;
    }

    public Integer get_managerPoints() {
        return _totalManagerPoints;
    }

    public void set_manager_points(Integer points) {
        _totalManagerPoints = points;
    }

    public Boolean is_manager() {
        return _employees_list.size() > 0;
    }


    // Synchronization functions

    public void writelock() {
        this._lock.writeLock().lock();
    }

    public void readlock() {
        this._lock.readLock().lock();
    }

    public void writeunlock() {
        this._lock.writeLock().unlock();
    }

    public void readunlock() {
        this._lock.readLock().unlock();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Employee e = (Employee)o;

        return (this._name.equals(e._name) && this._username.equals(e._username)
                && (this._total_points == e._total_points)
                && (this._weekly_added_points == e._weekly_added_points)
                && this._restrictions.get_allowed_days().equals(e._restrictions.get_allowed_days())
                && ((this._direct_manager == null && e._direct_manager == null) || (this._direct_manager != null) && (e._direct_manager != null) && this._direct_manager.equals(e._direct_manager)));
    }


}
