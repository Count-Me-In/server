package business_logic.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Employee {
    private UUID _id;
    private String _name;
    private UUID _direct_manager;
    private Restriction _restrictions;
    private Bid[] _bids_collection;
    private Integer _total_points;
    private List<UUID> _employees_list;
    private Assignings _assignings;

    public Employee(String name, UUID directed_manager_id, Integer total_points, Boolean is_manager) {
        this._id = UUID.randomUUID();
        this._name = name;
        this._direct_manager = directed_manager_id;
        this._restrictions = new Restriction();
        this._bids_collection = new Bid[5];
        this._total_points = total_points;
        this._employees_list = new ArrayList<>();
        this._assignings = new Assignings(this._id);
    }

    public UUID get_id() {
        return _id;
    }

    public void set_id(UUID id) {
        this._id = id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String name) {
        this._name = name;
    }

    public UUID get_directed_manager_id() {
        return _direct_manager;
    }

    public void set_directed_manager_id(UUID directed_manager_id) {
        this._direct_manager = directed_manager_id;
    }

    public Restriction get_allowed_days() {
        return _restrictions;
    }

    public void set_allowed_days(Restriction allowed_days) {
        this._restrictions = allowed_days;
    }

    public Bid[] get_bid_collection() {
        return _bids_collection;
    }

    public void set_bid_collection(Bid[] bid_collection) {
        this._bids_collection = bid_collection;
    }

    public Integer get_total_points() {
        return _total_points;
    }

    public void set_total_points(Integer total_points) {
        this._total_points = total_points;
    }

    public Boolean is_manager() {
        return !(this._employees_list == null && this._employees_list.isEmpty());
    }

    public List<UUID> get_employees_list() {
        return _employees_list;
    }

    public void set_employees_list(List<UUID> employees_list) {
        this._employees_list = employees_list;
    }

}
