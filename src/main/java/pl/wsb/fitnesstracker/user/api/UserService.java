package pl.wsb.fitnesstracker.user.api;

/**
 * Interface (API) for modifying operations on {@link User} entities through the API.
 * Implementing classes are responsible for executing changes within a database transaction, whether by continuing an existing transaction or creating a new one if required.
 */
public interface UserService {
    /**
     * Create user user.
     *
     * @param user the user
     * @return the user
     */
    User createUser(User user);

    /**
     * Delete user.
     *
     * @param userId the user id
     */
    void deleteUser(Long userId);

    /**
     * Update user user.
     *
     * @param userId the user id
     * @param user   the user
     * @return the user
     */
    User updateUser(Long userId, User user);
}
