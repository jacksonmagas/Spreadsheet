package Server.api;

import Server.model.User;
import Server.model.Spreadsheet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SubscriptionManager {
    private final Map<Spreadsheet, Set<User>> subscriptions = new HashMap<>();

    public void subscribe(Spreadsheet spreadsheet, User user) {
        subscriptions.computeIfAbsent(spreadsheet, k -> new HashSet<>()).add(user);
    }

    public void unsubscribe(Spreadsheet spreadsheet, User user) {
        Set<User> users = subscriptions.get(spreadsheet);
        if (users != null) {
            users.remove(user);
            if (users.isEmpty()) {
                subscriptions.remove(spreadsheet);
            }
        }
    }

    public Set<User> getSubscribers(Spreadsheet spreadsheet) {
        return subscriptions.getOrDefault(spreadsheet, new HashSet<>());
    }
}
