package nl.maritoldenburger.bakehub.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserInputDto {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50)
    public String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    public String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 100)
    public String password;
}
