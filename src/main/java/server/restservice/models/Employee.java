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
    public Employee(String username, String name, String directed_manager, Integer total_points, Integer weekly_added_points, Integer totalManagerPoints) {
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

    public Employee(UUID id, String username, String name, String directed_manager, Integer total_points, Integer weekly_added_points, Integer totalManagerPoints) {
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

    // Getters and Setters
    public UUID getID() {
        return _id;
    }
    public String getUsername() {
        return _username;
    }

    public String getName() {
        return _name;
    }

    public String getManager() {
        return _direct_manager;
    }

    public int getTotalPoints() {
        return _total_points;
    }

    public int getWeeklyPoints() {
        return _weekly_added_points;
    }
    public void setWeeklyPoints(int points) {
        _total_points = points;
    }

    public List<String> getEmployees() {
        return _employees_list;
    }

    public Restriction getRestriction() {
        return _restrictions;
    }

    public void setRestrictions(Restriction restriction) {
        _restrictions = restriction;
    }

    public Bid[] getBids() {
        return _bids_collection;
    }

    public void setBids(Bid[] bids) {
        _bids_collection = bids;
    }

    public Assignings getAssignings() {
        return _assignings;
    }

    public void setAssignings(Assignings as) {
        _assignings = as;
    }

    public Integer getManagerPoints() {
        return _totalManagerPoints;
    }

    public void setManagerPoints(Integer points) {
        _totalManagerPoints = points;
    }

    public Boolean isManager(){
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

}
