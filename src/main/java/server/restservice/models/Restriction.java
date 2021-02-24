package server.restservice.models;

import java.util.ArrayList;
import java.util.List;

public class Restriction {
    private List<Integer> _allowed_days;

    public Restriction() {
        this._allowed_days = new ArrayList<>();
    }

    public List<Integer> get_allowed_days() {
        return new ArrayList<>(this._allowed_days);
    }

    public void add_allowed_day(Integer allowed_day) {
        if (!this._allowed_days.contains(allowed_day)) {
            this._allowed_days.add(allowed_day);
        }
    }

    public void remove_allowed_day(Integer allowed_day) {
        this._allowed_days.remove(allowed_day);
    }
}
