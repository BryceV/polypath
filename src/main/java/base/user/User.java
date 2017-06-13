package base.user;

import base.flowchart.Flowchart;
import base.security.user.RoleType;
import com.fasterxml.jackson.annotation.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="users") // 'user' is a keyword in Postgres
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, resolver = SimpleObjectIdResolver.class, property = "id", scope=User.class)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "First name is required.")
    private String firstName;

    @NotEmpty(message = "Last name is required.")
    private String lastName;

    @Email(message = "Please provide a valid email address.")
    @NotEmpty(message = "Email is required.")
    @Column(unique=true, nullable = false)
    private String email;

    @JsonIgnore
    @NotEmpty(message = "Password is required.")
    private String password;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = RoleType.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "users_id"))
    @Column(name = "roles")
    private Set<RoleType> roles;

    @JsonIgnore
    @OneToMany(targetEntity = Flowchart.class, mappedBy = "user",
            cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
//    @JsonIgnoreProperties("user")
//    @JsonProperty("flowchart_ids")
    private List<Flowchart> flowcharts;

    public User() {}

    public User(User user) {
        this.id = user.id;
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.email = user.email;
        this.password = user.password;
        this.roles = user.roles;
        this.flowcharts = user.flowcharts;
    }

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public User(String firstName, String lastName, String email, String password, Set<RoleType> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    @PrePersist
    void preInsert() {
        if (roles == null || roles.isEmpty()) {
            roles = new HashSet<>();
            roles.add(RoleType.STUDENT);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Flowchart> getFlowcharts() {
        return flowcharts;
    }

    public void setFlowcharts(List<Flowchart> flowcharts) {
        this.flowcharts = flowcharts;
    }

    public Set<RoleType> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleType> roles) {
        this.roles = roles;
    }
}
