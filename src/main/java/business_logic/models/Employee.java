package business_logic.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Employee {
    private UUID _id;
    private String _name;
    private UUID _directed_manager_id;
    private Restriction _allowed_days;
    private List<Bid> _bid_collection;
    private Integer _total_points;
    private Boolean _is_manager;
    private List<UUID> _employees_list;

    public Employee(String name, UUID directed_manager_id, Integer total_points, Boolean is_manager) {
        this._id = UUID.randomUUID();
        this._name = name;
        this._directed_manager_id = directed_manager_id;
        this._allowed_days = new Restriction();
        this._bid_collection = new ArrayList<>();
        this._total_points = total_points;
        this._is_manager = is_manager;
        this._employees_list = new ArrayList<>();
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
        return _directed_manager_id;
    }

    public void set_directed_manager_id(UUID directed_manager_id) {
        this._directed_manager_id = directed_manager_id;
    }

    public Restriction get_allowed_days() {
        return _allowed_days;
    }

    public void set_allowed_days(Restriction allowed_days) {
        this._allowed_days = allowed_days;
    }

    public List<Bid> get_bid_collection() {
        return _bid_collection;
    }

    public void set_bid_collection(List<Bid> bid_collection) {
        this._bid_collection = bid_collection;
    }

    public Integer get_total_points() {
        return _total_points;
    }

    public void set_total_points(Integer total_points) {
        this._total_points = total_points;
    }

    public Boolean get_is_manager() {
        return _is_manager;
    }

    public void set_is_manager(Boolean is_manager) {
        this._is_manager = is_manager;
    }

    public List<UUID> get_employees_list() {
        return _employees_list;
    }

    public void set_employees_list(List<UUID> employees_list) {
        this._employees_list = employees_list;
    }

}
