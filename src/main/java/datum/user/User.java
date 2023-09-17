package datum.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import datum.app.clinic.model.Clinic;
import datum.token.PasswordResetToken;
import datum.token.Token;
import jakarta.persistence.*;

import datum.app.clinic.model.Employee;

import java.time.OffsetDateTime;
import java.util.*;

import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.CreationTimestamp;
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
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private PasswordResetToken token;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn
    private Person person;

    @JsonIgnore
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime date;

    @JsonBackReference
    @OneToMany(mappedBy = "user")
    private List<Employee> employee;

    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private List<Clinic> clinics;

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
