package ru.geekbrains.handmade.ltmbackend.core.entities.user;
import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.geekbrains.handmade.ltmbackend.core.entities.Account;
import ru.geekbrains.handmade.ltmbackend.core.entities.task.Task;
import ru.geekbrains.handmade.ltmbackend.core.entities.base.AbstractEntity;
import ru.geekbrains.handmade.ltmbackend.core.entities.oauth2.token.RefreshToken;
import ru.geekbrains.handmade.ltmbackend.core.entities.task.TaskMember;
//import ru.geekbrains.handmade.ltmbackend.utils.configurations.Default;
import ru.geekbrains.handmade.ltmbackend.utils.configurations.Default;
import ru.geekbrains.handmade.ltmbackend.utils.data.enums.UserRole;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;

//@MappedSuperclass
@Entity
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(
        name = "uzer",
        indexes = {
                @Index(name = "user_account_id_idx", columnList = "account_id"),
                @Index(name = "user_username_unq", columnList = "username", unique = true),
                @Index(name = "user_email_unq", columnList = "email", unique = true),
                @Index(name = "user_phone_number_unq", columnList = "phone_number", unique = true),
                @Index(name = "user_first_name_last_name_unq", columnList = "last_name, first_name", unique = true)
        })

@NamedEntityGraph(name = User.FULL_ENTITY_GRAPH,
    attributeNodes = @NamedAttributeNode("refreshTokenList")
)

@EntityListeners(UserToPersistListener.class)
@Data
public class User extends AbstractEntity {

    public static final String FULL_ENTITY_GRAPH = "User.full";

//    @Id
//    @Column(name = "id")
//    @GeneratedValue(generator = "user_id_seq")
//    @EqualsAndHashCode.Exclude
//    private Long id;

    @NotNull
    @Column(name = "username")
    @Setter(AccessLevel.NONE)
    private String username;     // username is approved by dictionary.com //  use email as username ???

    @NotNull
    @Column(name = "password") // bcrypt hash
    private String password;

//    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
//    private Set<UserRole> roles = new HashSet<>();

//    @NotNull
//    @NotEmpty
//    @OneToMany
//    //@ManyToMany(/*cascade = {CascadeType.MERGE},*/ fetch = FetchType.EAGER)
//    @Column(name = "roles")
//    private Set<UserRole> roles = new HashSet<>();

    // Many-To-Many between entity and enum
    // https://www.w3ma.com/persisting-set-of-enums-in-a-many-to-many-spring-data-relationship/

    // Хранит список ролей для каждого пользователя
    // тут используется @Converter для преобразования enum UserRole <-> DB String
    // class UserRoleConverter implements AttributeConverter<UserRole, String>
    @NotNull
    @NotEmpty
    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "uzer_role", joinColumns = @JoinColumn(name = "user_id"))
    //@Enumerated(EnumType.)
    @Column(name = "role")
    private Set<UserRole> roles = new HashSet<>();

    // Это список только refresh_token
    @NotNull
    @OneToMany(mappedBy= "user", orphanRemoval = true, cascade = CascadeType.ALL)
    @OrderBy("id ASC")
    private List<RefreshToken> refreshTokenList = new ArrayList<>();

    @NotNull
    @Column(name = "first_name")
    @Setter(AccessLevel.NONE)
    private String firstName;

    @NotNull
    @Column(name = "last_name")
    @Setter(AccessLevel.NONE)
    private String lastName;

    @NotNull
    @Column(name = "email")
    @Setter(AccessLevel.NONE)
    private String email;     // will we use email as username ??

    @Column(name = "paypal_email")
    private String payPalEmail;     // Paypal withdrawal mailbox

    @NotNull
    @Column(name = "phone_number")  // may change or not?
    @Setter(AccessLevel.NONE)
    private String phoneNumber;

    @OneToOne(/*cascade = CascadeType.ALL,*/ orphanRemoval = true/*, fetch = FetchType.EAGER*/)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;


    @Column(name = "age")
    @Setter(AccessLevel.NONE)
    private Integer age;


    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    Set<TaskMember> taskMembers;/* = new HashSet<>();*/

//    // User tasks (user may have roles of owner, executor or other crew in each task)
//    @ManyToMany
//    @JoinTable(name = "task_member",
//        joinColumns = @JoinColumn(name = "user_id"),
//        inverseJoinColumns = @JoinColumn(name = "task_id"))
//    Set<Task> tasks = new HashSet<>();

//    @OneToOne(mappedBy= "user", orphanRemoval = true)
//    @OrderBy("id ASC")
//    private Client client;
//
//    @OneToOne(mappedBy= "user", orphanRemoval = true)
//    @OrderBy("id ASC")
//    private Courier courier;

    //public User() {}


    protected User() {}



//    public User(@NotNull String username,
//                @NotNull String password,
//                @NotNull String firstName,
//                @NotNull String lastName,
//                @NotNull String email,
//                @NotNull String phoneNumber) {
//
//        this.username = username;
//        this.password = password;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.email = email;
//        this.phoneNumber = phoneNumber;
//
//        this.getRoles().add(UserRole.USER);
//    }

    @Default
    public User(@NotNull String username,
                @NotNull String password,
                @NotNull String firstName,
                @NotNull String lastName,
                         Integer age,
                @NotNull String email,
                @NotNull String phoneNumber) {

        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.phoneNumber = phoneNumber;

        this.getRoles().add(UserRole.USER);
    }



    public void setAccount(Account account) {

        if (account != null) {
            this.account = account;
            account.setUser(this);
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", account=" + account +
                '}';
    }

    @JsonIgnore
    public String getFullName() {
        return lastName + firstName;
    }

//    public void setClient(Client client) {
//        this.client = client;
//        if (client.getUser() != this) {
//            client.setUser(this);
//        }
//    }
//
//    public void setCourier(Courier courier) {
//        this.courier = courier;
//        if (courier.getUser() != this) {
//            courier.setUser(this);
//        }
//    }



    @PrePersist
    private void prePersists() {
    }

    // ToDo: Пользователь может/должен идентифицироваться(principals) не только по username, но и по email, phoneNumber
    //  Одно из предлагаемых решений - взять UserDetailsCustomService
    //  и намутить ему еще кастомных методов по типу
    //  public UserDetails loadUserByEmail(String email)
    //  public UserDetails loadUserByPhone(String phone)
    //  Далее уже везде, где внедряется/используется UserDetailsService заменить на UserDetailsCustomService
    //  Но тогда не понятно, а что тогда использовать вместо username - глобального идентификатора пользователя в системе?
    //  Или что использоваться в качестве этого username(как генерировать username - на основе телефона, ящика GUID, и т.д.) ?
    //
    // Тут пользователь предполагается идентичным если у него одинаковые поля username & email & phone

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
