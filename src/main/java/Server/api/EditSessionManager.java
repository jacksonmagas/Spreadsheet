package Server.api;

import java.util.HashMap;
import java.util.Map;

public class EditSessionManager {
    private final Map<String, String> editingSessions = new HashMap<>();

    public void startEditing(String spreadsheetName, String userName) {
        editingSessions.put(spreadsheetName, userName);
    }

    public void stopEditing(String spreadsheetName, String userName) {
        if (editingSessions.get(spreadsheetName).equals(userName)) {
            editingSessions.remove(spreadsheetName);
        }
    }

    public String getEditor(String spreadsheetName) {
        return editingSessions.get(spreadsheetName);
    }

    public void clear() {
        editingSessions.clear();
    }
}
