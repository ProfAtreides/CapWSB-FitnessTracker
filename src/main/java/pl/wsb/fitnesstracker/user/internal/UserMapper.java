package pl.wsb.fitnesstracker.user.internal;

import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserDto;
import pl.wsb.fitnesstracker.user.api.UserDtoSimple;
import pl.wsb.fitnesstracker.user.api.UserEmailDto;

/**
 * The type User mapper.
 */
@Component
class UserMapper {
    /**
     * To dto user dto.
     *
     * @param user the user
     * @return the user dto
     */
    UserDto toDto(User user) {
        return new UserDto(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate(),
                user.getEmail());
    }

    /**
     * To simple dto user dto simple.
     *
     * @param user the user
     * @return the user dto simple
     */
    UserDtoSimple toSimpleDto(User user) {
        return new UserDtoSimple(user.getId(),
                user.getFirstName(),
                user.getLastName());
    }

    /**
     * To email dto user email dto.
     *
     * @param user the user
     * @return the user email dto
     */
    UserEmailDto toEmailDto(User user) {
        return new UserEmailDto(user.getId(),
                user.getEmail());
    }

    /**
     * To entity user.
     *
     * @param userDto the user dto
     * @return the user
     */
    User toEntity(UserDto userDto) {
        return new User(
                userDto.firstName(),
                userDto.lastName(),
                userDto.birthdate(),
                userDto.email());
    }
}

