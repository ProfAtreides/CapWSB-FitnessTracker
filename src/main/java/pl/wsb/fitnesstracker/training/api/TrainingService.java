package pl.wsb.fitnesstracker.training.api;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing trainings.
 * Provides business logic operations for training entities.
 */
public interface TrainingService {

    /**
     * Retrieves all trainings from the system.
     *
     * @return List of all trainings
     */
    List<Training> findAllTrainings();

    /**
     * Retrieves trainings for a specific user.
     *
     * @param userId the ID of the user
     * @return List of trainings belonging to the specified user
     */
    List<Training> findTrainingsByUserId(Long userId);

    /**
     * Retrieves a training by its ID.
     *
     * @param trainingId the ID of the training
     * @return An {@link Optional} containing the training if found, or {@link Optional#empty()} otherwise
     */
    Optional<Training> getTraining(Long trainingId);
}

