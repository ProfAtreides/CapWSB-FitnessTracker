package pl.wsb.fitnesstracker.user.internal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserNotFoundException;
import pl.wsb.fitnesstracker.user.api.UserProvider;
import pl.wsb.fitnesstracker.user.api.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * The type User service.
 */
@Service
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;

    /**
     * Instantiates a new User service.
     *
     * @param userRepository the user repository
     */
    UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(final User user) {
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(final Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }
        userRepository.deleteById(userId);
    }

    @Override
    public User updateUser(final Long userId, final User user) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (user.getFirstName() != null) {
            existingUser.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null) {
            existingUser.setLastName(user.getLastName());
        }
        if (user.getBirthdate() != null) {
            existingUser.setBirthdate(user.getBirthdate());
        }
        if (user.getEmail() != null) {
            existingUser.setEmail(user.getEmail());
        }

        return userRepository.save(existingUser);
    }

    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Find users by email fragment list.
     *
     * @param emailFragment the email fragment
     * @return the list
     */
    public List<User> findUsersByEmailFragment(final String emailFragment) {
        return userRepository.findByEmailContainingIgnoreCase(emailFragment);
    }

    /**
     * Find users older than list.
     *
     * @param age the age
     * @return the list
     */
    public List<User> findUsersOlderThan(final int age) {
        LocalDate birthdateThreshold = LocalDate.now().minusYears(age);
        return userRepository.findByBirthdateBefore(birthdateThreshold);
    }

}