package datum.authenticate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import datum.app.clinic.model.Clinic;
import datum.app.admin.model.Person;
import datum.authenticate.token.Token;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;

import datum.app.clinic.model.Employee;

import java.io.Serializable;
import java.util.*;

import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@ToString
@Jacksonized
//@SQLDelete(sql = "update User set deleted=true where id=?")
//@Where(clause = "deleted = false")
public class User implements UserDetails , Serializable {
    @Id
    @Tsid
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private Boolean locked;
    @JsonIgnore
    private Boolean enabled;
    @JsonIgnore
    private Boolean deleted;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;
    @JsonIgnore
    public Integer code;
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Token> tokens;

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn
    private Person person;

    @JsonBackReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employee> employee= new ArrayList<>();

    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private List<Clinic> clinics = new ArrayList<>();

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return email;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return locked;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
