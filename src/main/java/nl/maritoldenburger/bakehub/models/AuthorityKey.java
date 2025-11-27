package nl.maritoldenburger.bakehub.models;

import jakarta.persistence.Embeddable;
import nl.maritoldenburger.bakehub.enums.Role;

import java.io.Serializable;

@Embeddable
public class AuthorityKey implements Serializable {
    private String username;
    private Role authority;
}
