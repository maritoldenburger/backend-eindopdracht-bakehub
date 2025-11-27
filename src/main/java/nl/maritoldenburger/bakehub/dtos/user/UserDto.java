package nl.maritoldenburger.bakehub.dtos.user;

import nl.maritoldenburger.bakehub.models.Authority;

import java.util.Set;

public class UserDto {
    public Long id;
    public String username;
    public String email;
    public Set<Authority> authorities;
}
