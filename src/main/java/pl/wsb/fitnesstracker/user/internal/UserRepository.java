package pl.wsb.fitnesstracker.user.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wsb.fitnesstracker.user.api.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The interface User repository.
 */
interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Query searching users by email address. It matches by exact match.
     *
     * @param email email of the user to search
     * @return {@link Optional} containing found user or {@link Optional#empty()} if none matched
     */
    default Optional<User> findByEmail(String email) {
        return findAll().stream()
                .filter(user -> Objects.equals(user.getEmail(), email))
                .findFirst();
    }

    /**
     * Find by email containing ignore case list.
     *
     * @param emailFragment the email fragment
     * @return the list
     */
    default List<User> findByEmailContainingIgnoreCase(String emailFragment) {
        return findAll().stream()
                .filter(user -> user.getEmail() != null &&
                        user.getEmail().toLowerCase().contains(emailFragment.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Find by birthdate before list.
     *
     * @param birthdate the birthdate
     * @return the list
     */
    default List<User> findByBirthdateBefore(LocalDate birthdate) {
        return findAll().stream()
                .filter(user -> user.getBirthdate() != null &&
                        user.getBirthdate().isBefore(birthdate))
                .collect(Collectors.toList());
    }

}
