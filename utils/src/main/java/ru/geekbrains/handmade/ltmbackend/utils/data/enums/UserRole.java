package ru.geekbrains.handmade.ltmbackend.utils.data.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;



// https://stackoverflow.com/questions/13253624/how-to-supply-enum-value-to-an-annotation-from-a-constant-in-java
// All problems in computer science can be solved by another level of indirection,
// except for the problem of too many layers of indirection.


//  Done
//  https://www.baeldung.com/jpa-persisting-enums-in-jpa#converter
//  https://www.w3ma.com/persisting-set-of-enums-in-a-many-to-many-spring-data-relationship/


// Храним роли в коде, а не в БД


// Используется @Converter UserRoleConverter
// - конвертирует enum UserRole при сохранении в базу и обратно при доставании из базы

/**
 * Глобальные роли пользователя
 */
public enum UserRole {

    // ENUM(role,code)
    ADMIN(VAL.ADMIN, VAL.M.get(VAL.ADMIN)),
    MANAGER(VAL.MANAGER, VAL.M.get(VAL.MANAGER)),
    USER(VAL.USER, VAL.M.get(VAL.USER)),
    ANONYMOUS(VAL.ANONYMOUS, VAL.M.get(VAL.ANONYMOUS)),
    RESOURCE(VAL.RESOURCE, VAL.M.get(VAL.RESOURCE)),
    REFRESH(VAL.REFRESH, VAL.M.get(VAL.REFRESH)),
    CONFIRM_REGISTRATION(VAL.CONFIRM_REGISTRATION, VAL.M.get(VAL.CONFIRM_REGISTRATION));

    private final static Map<String, UserRole> NAME_MAP = new HashMap<>(); // name index
    private final static Map<String, UserRole> CODE_MAP = new HashMap<>(); // code index

    static {

        for(UserRole role : UserRole.values()) {

            // check for already exists name and code
            if (NAME_MAP.containsKey(role.name)){
                throw new RuntimeException("UserRole duplicate name");
            }
            if (CODE_MAP.containsKey(role.code)){
                throw new RuntimeException("UserRole duplicate code");
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

    UserRole(String name, String code) {

        if(!name.equals("ROLE_" + this.name()))
            throw new IllegalArgumentException(name + " should be equal UserRole." + this.name());
        this.name = name;
        this.code = code;
    }

    public static UserRole getByName(String name) {

        UserRole result = NAME_MAP.get(name);
        if (result == null) {
            throw new IllegalArgumentException("UserRole - no matching value for [" + name + "]");
        }
        return result;
    }

    public static UserRole getByCode(String code) {

        UserRole result = CODE_MAP.get(code);
        if (result == null) {
            throw new IllegalArgumentException("UserRole - no matching value for [" + code + "]");
        }
        return result;
    }

    public static Set<GrantedAuthority> rolesToGrantedAuthority(Object authorities) {
        //noinspection unchecked
        return ((List<String>)authorities)
            .stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }

    public static Set<GrantedAuthority> rolesToGrantedAuthority(Set<UserRole> roles) {
        return  roles.stream().map(role -> new SimpleGrantedAuthority(role.name)).collect(Collectors.toSet());
    }

    public static Set<UserRole> grantedAuthorityToRoles(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream().map(ga -> UserRole.getByName(ga.toString())).collect(Collectors.toSet());
    }



    // Это имена ролей, как они используются в коде (и в DTO)
    public static class VAL {

        public static final String ADMIN                  = "ROLE_ADMIN";
        public static final String USER                   = "ROLE_USER";
        public static final String ANONYMOUS              = "ROLE_ANONYMOUS";
        public static final String RESOURCE               = "ROLE_RESOURCE";
        public static final String REFRESH                = "ROLE_REFRESH";
        public static final String CONFIRM_REGISTRATION   = "ROLE_CONFIRM_REGISTRATION";

        //public static final String CLIENT                 = "ROLE_CLIENT";
        //public static final String COURIER                = "ROLE_COURIER";
        public static final String MANAGER                = "ROLE_MANAGER";

        private final static Map<String, String> M = new HashMap<>();

        // это коды имен ролей (кодированные имена ролей), в таком виде они хранятся в БД
        static {
            M.put(ADMIN,                "ADMIN");
            M.put(MANAGER,              "MANAGER");
            M.put(USER,                 "USER");
            M.put(ANONYMOUS,            "ANON");
            M.put(RESOURCE,             "RESOURCE");
            M.put(REFRESH,              "REFRESH");
            M.put(CONFIRM_REGISTRATION, "CONFIRM");
        }

    }
}
