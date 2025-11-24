package nl.maritoldenburger.bakehub.mappers;

import nl.maritoldenburger.bakehub.dtos.user.UserDto;
import nl.maritoldenburger.bakehub.dtos.user.UserInputDto;
import nl.maritoldenburger.bakehub.models.User;

public class UserMapper {

    public static User toEntity(UserInputDto userInputDto) {
        User user = new User();

        user.setUsername(userInputDto.username);
        user.setEmail(userInputDto.email);
        user.setPassword(userInputDto.password);

        return user;
    }

    public static UserDto toDto(User user) {
        UserDto userDto = new UserDto();

        userDto.id = user.getId();
        userDto.username = user.getUsername();
        userDto.email = user.getEmail();
        userDto.role = user.getRole();

        return userDto;
    }
}
