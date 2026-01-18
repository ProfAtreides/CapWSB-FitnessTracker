package pl.wsb.fitnesstracker.training.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingDto;
import pl.wsb.fitnesstracker.user.api.UserDto;

/**
 * Mapper for converting between Training entity and TrainingDto.
 */
@Component
@RequiredArgsConstructor
class TrainingMapper {

    /**
     * Converts Training entity to TrainingDto.
     *
     * @param training the training entity
     * @return the training DTO
     */
    TrainingDto toDto(Training training) {
        UserDto userDto = null;
        if (training.getUser() != null) {
            userDto = new UserDto(
                    training.getUser().getId(),
                    training.getUser().getFirstName(),
                    training.getUser().getLastName(),
                    training.getUser().getBirthdate(),
                    training.getUser().getEmail()
            );
        }

        return new TrainingDto(
                training.getId(),
                userDto,
                training.getStartTime(),
                training.getEndTime(),
                training.getActivityType(),
                training.getDistance(),
                training.getAverageSpeed()
        );
    }
}


