package server.restservice.models;

import java.util.ArrayList;
import java.util.List;

public class Restriction {
    private List<Integer> _allowed_days;

    public Restriction() {
        this._allowed_days = new ArrayList<>();
    }

    public Restriction(Restriction other) {
        this._allowed_days = new ArrayList<>(other._allowed_days);
    }

    public List<Integer> get_allowed_days() {
        return new ArrayList<>(this._allowed_days);
    }

    public void set_allowed_days(List<Integer> allowed_days) {
        _allowed_days = allowed_days;
    }
}
