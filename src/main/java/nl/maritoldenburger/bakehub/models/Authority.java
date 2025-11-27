package nl.maritoldenburger.bakehub.models;

import jakarta.persistence.*;
import nl.maritoldenburger.bakehub.enums.Role;

import java.io.Serializable;

@Entity
@IdClass(AuthorityKey.class)
@Table(name = "authorities")
public class Authority implements Serializable {

    @Id
    @Column(nullable = false)
    private String username;

    @Id
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role authority;

    public Authority() {
    }

    public Authority(String username, Role authority) {
        this.username = username;
        this.authority = authority;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getAuthority() {
        return authority;
    }

    public void setAuthority(Role authority) {
        this.authority = authority;
    }
}