package ru.cringules.moodtool.backend.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cringules.moodtool.backend.data.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsernameIgnoreCase(String username);
    boolean existsByUsernameIgnoreCase(String username);
}
