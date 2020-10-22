package ru.geekbrains.handmade.ltmbackend.utils.data.enums.task;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Привилегии пользователей, хранится только в коде
 */
public enum TaskUserPrivilege {
    READ(VAL.READ),
    WRITE(VAL.WRITE),
    CREATE_SUBTASK(VAL.CREATE_SUBTASK);

    private final static Map<String, TaskUserPrivilege> NAME_MAP = new HashMap<>(); // name index

    static {

        for(TaskUserPrivilege role : TaskUserPrivilege.values()) {

            // check for already exists name and code
            if (NAME_MAP.containsKey(role.name)){
                throw new RuntimeException("TaskUserPrivilege duplicate name");
            }

            NAME_MAP.put(role.name, role);
        }
    }


    @Getter
    private final String name; // friendly name


    TaskUserPrivilege(String name) {

        if(!name.equals(this.name()))
            throw new IllegalArgumentException(name + " should be equal TaskUserPrivilege." + this.name());
        this.name = name;
    }

    public static TaskUserPrivilege getByName(String name) {

        TaskUserPrivilege result = NAME_MAP.get(name);
        if (result == null) {
            throw new IllegalArgumentException("TaskUserPrivilege - no matching value for [" + name + "]");
        }
        return result;
    }

    public static class VAL {

        // define full names
        public static final String READ           = "READ";
        public static final String WRITE          = "WRITE";
        public static final String CREATE_SUBTASK = "CREATE_SUBTASK";
    }
}
