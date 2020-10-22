package ru.geekbrains.handmade.ltmbackend.utils.data.enums.task;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.*;

/**
 * Роль пользователя в контексте конкретной Task
 */
public enum TaskUserRole {

    // ENUM(role,code)
    OWNER(VAL.OWNER, VAL.M.get(VAL.OWNER)),
    EXECUTOR(VAL.EXECUTOR, VAL.M.get(VAL.EXECUTOR)),
    REGULAR(VAL.REGULAR, VAL.M.get(VAL.REGULAR));

    private final static Map<String, TaskUserRole> NAME_MAP = new HashMap<>(); // name index
    private final static Map<String, TaskUserRole> CODE_MAP = new HashMap<>(); // code index

    static {

        for(TaskUserRole role : TaskUserRole.values()) {

            // check for already exists name and code
            if (NAME_MAP.containsKey(role.name)){
                throw new RuntimeException("TaskUserRole duplicate name");
            }
            if (CODE_MAP.containsKey(role.code)){
                throw new RuntimeException("TaskUserRole duplicate code");
            }

            NAME_MAP.put(role.name, role);
            CODE_MAP.put(role.code, role);
        }
    }


    @Getter
    private final String name; // friendly name

    @JsonValue
    @Getter
    private final String code; // stored to DB


    TaskUserRole(String name, String code) {

        if(!name.equals(this.name()))
            throw new IllegalArgumentException(name + " should be equal TaskUserRole." + this.name());
        this.name = name;
        this.code = code;
    }

    public static TaskUserRole getByName(String name) {

        TaskUserRole result = NAME_MAP.get(name);
        if (result == null) {
            throw new IllegalArgumentException("TaskUserRole - no matching value for [" + name + "]");
        }
        return result;
    }

    public static TaskUserRole getByCode(String code) {

        TaskUserRole result = CODE_MAP.get(code);
        if (result == null) {
            throw new IllegalArgumentException("TaskUserRole - no matching value for [" + code + "]");
        }
        return result;
    }

    public static class VAL {

        // define full names
        public static final String OWNER    = "OWNER";
        public static final String EXECUTOR = "EXECUTOR";
        public static final String REGULAR    = "REGULAR";

        //                   Full Name, Code
        private final static Map<String, String> M = new HashMap<>();

        // define codes, that been stored in DB
        static {
            M.put(OWNER,    "OWNER");
            M.put(EXECUTOR, "EXEC");
            M.put(REGULAR,  "REGULAR");
        }
    }
}
