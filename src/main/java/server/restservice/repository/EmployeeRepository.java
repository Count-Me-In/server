package server.restservice.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.concurrent.ConcurrentHashMap;

import server.restservice.models.Bid;
import server.restservice.models.Employee;
import server.restservice.repository.EngineAPI.api.engineAPIInterface;
import server.restservice.repository.EngineAPI.model.Actor;
import server.restservice.repository.EngineAPI.model.Assignment;
import server.restservice.repository.EngineAPI.model.Item;

import java.time.LocalDate;
import java.time.ZoneId;

import java.time.DayOfWeek;
import static java.time.temporal.TemporalAdjusters.next;

/**
 * EmployeeRepository
 */
@Repository
public class EmployeeRepository {

    @Value("${cacheSize}")
    private int MAX_SIZE;

    @Autowired
    @Qualifier("mockAPI")
    private engineAPIInterface engineAPI;

    private ConcurrentHashMap<String, SimpleEntry<Employee, Long>> _employee_cacheMap = new ConcurrentHashMap<String, SimpleEntry<Employee, Long>>();

    public Employee findEmployeeByUsername(String username) {
        SimpleEntry<Employee, Long> user;
        if ((user = _employee_cacheMap.get(username)) != null) {
            user.setValue(Instant.now().getEpochSecond());
            return user.getKey();
        } else {
            synchronized (this) {
                if (_employee_cacheMap.size() > MAX_SIZE) {
                    SimpleEntry<Employee, Long> userToRemove = Collections.min(_employee_cacheMap.values(),
                            new Comparator<SimpleEntry<Employee, Long>>() {
                                public int compare(SimpleEntry<Employee, Long> e1, SimpleEntry<Employee, Long> e2) {
                                    return (int) (e1.getValue() - e2.getValue());
                                }
                            });
                    _employee_cacheMap.remove(userToRemove.getKey().getUsername());
                }

                Employee empToAdd = _getEmployeeFromSource(username);

                if (empToAdd != null) {
                    SimpleEntry<Employee, Long> userToAdd = new SimpleEntry<Employee, Long>(empToAdd,
                    Instant.now().getEpochSecond());
                    _employee_cacheMap.put(empToAdd.getUsername(), userToAdd);
                }
                return empToAdd;
            }
        }
    }

    public void save(Employee emp) {
        synchronized (emp) {
            ActorAdditionalData data = new ActorAdditionalData(emp.getUsername(), emp.getName(), emp.getManager(), emp.getManagerPoints(), emp.getRestriction().get_allowed_days(), emp.getEmployees());
            Actor actor = new Actor(emp.getID(), emp.getTotalPoints(), emp.getWeeklyPoints(), data);
            engineAPI.editActor(actor.getId(), actor);

            Bid[] bids = emp.getBids();
            for (int i = 0; i < bids.length; i++) {
                server.restservice.repository.EngineAPI.model.Bid bid = engineAPI.getBidByID(bids[i].getID());
                bid.setPercentage(bids[i].getPercentage());
                engineAPI.editBid(bid.getId(), bid);
            }
        }
    }

    public Employee[] getAllEmployeeNames() {
        List<Employee> employees = new ArrayList<Employee>();
        for (Actor actor : engineAPI.getActors()) {
            ActorAdditionalData additionalActorData = (ActorAdditionalData) actor.getAdditionalInfo();
            employees.add(new Employee(additionalActorData.getUsername(), additionalActorData.getName()));
        }
        Employee[] output = new Employee[employees.size()];
        return employees.toArray(output);
    }

    public String getUsernamePass(String username) {
        Actor actor = getActorByUsername(username);
        if (actor == null) {
            return null;
        }
        ActorAdditionalData additionalActorData = (ActorAdditionalData) actor.getAdditionalInfo();
        return additionalActorData.getPassword();
    }

    public void cleanCache() {
        synchronized (this) {
            _employee_cacheMap.clear();
        }
    }

    private Employee _getEmployeeFromSource(String username) {
        Actor actor = getActorByUsername(username);
        if (actor == null) {
            return null;
        }

        ActorAdditionalData additionalActorData = (ActorAdditionalData) actor.getAdditionalInfo();

        Employee emp = new Employee(actor.getId(), username, additionalActorData.getName(), additionalActorData.getManager(), actor.getPoints(), actor.getIntervalBonus(), additionalActorData.getManagerPoints());
        emp.getRestriction().set_allowed_days(additionalActorData.getAllowedDays());
        emp.getEmployees().addAll(additionalActorData.getEmployees());
        List<Date> days = new ArrayList<Date>();
        for (Assignment ass : engineAPI.getActorAssignments(actor.getId())) {
            Item i = engineAPI.getItem(ass.getItemID());
            ItemAdditionalData additionalItemData = (ItemAdditionalData) i.getAdditionalInfo();
            LocalDate bidDate = ass.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate dayOfWeek = bidDate.with(next(dayToDayOfWeek(additionalItemData.getDay())));
            days.add(Date.from(dayOfWeek.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }
        emp.getAssignings().addAssinedDays(days);
        emp.setBids(getActorBids(actor, username));

        return emp;
    }

    private Actor getActorByUsername(String username) {
        for (Actor a : engineAPI.getActors()) {
            ActorAdditionalData additionalData = (ActorAdditionalData) a.getAdditionalInfo();
            if (additionalData.getUsername().equals(username))
                return a;
        }
        return null;
    }

    private Bid[] getActorBids(Actor actor, String username) {
        Bid[] bids = new Bid[5];
        Item[] items = getNextWeekItems();
        for (int i = 0; i < items.length; i++) {
            server.restservice.repository.EngineAPI.model.Bid bid = engineAPI.getBid(actor.getId(), items[i].getId());
            bids[i] = new Bid(bid.getId(), username, i+1);
            bids[i].setPercentage(bid.getPercentage());
        }
        return bids;
    }

    private Item[] getNextWeekItems() {
        Item[] items = new Item[5];
        for (Item i : engineAPI.getItems()) {
            ItemAdditionalData additionalItemData = (ItemAdditionalData) i.getAdditionalInfo();
            items[additionalItemData.getDay()-1] = i;
        }
        return items;
    }

    private DayOfWeek dayToDayOfWeek(Integer day) {
        day = ((day + 5) % 7) + 1;
        return DayOfWeek.of(day);
    }

    private class ActorAdditionalData {
        private String _username;
        private String _password = "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6";
        private String _name;
        private String _directed_manager;
        private Integer _totalManagerPoints;
        private List<Integer> _allowed_days;
        private List<String> _employees;

        public ActorAdditionalData(String username, String name, String manager, Integer managerPoints, List<Integer> allowed_days, List<String> employees) {
            _username = username;
            _name = name;
            _directed_manager = manager;
            _totalManagerPoints = managerPoints;
            _allowed_days = allowed_days;
            _employees = employees;
        }

        public String getUsername() {
            return _username;
        }

        public String getPassword() {
            return _password;
        }

        public String getName() {
            return _name;
        }

        public String getManager() {
            return _directed_manager;
        }

        public int getManagerPoints() {
            return _totalManagerPoints;
        }

        public List<Integer> getAllowedDays() {
            return _allowed_days;
        }

        public List<String> getEmployees() {
            return _employees;
        }
    }

    private class ItemAdditionalData {
        private Integer _day;

        public Integer getDay() {
            return _day;
        }
    }

}