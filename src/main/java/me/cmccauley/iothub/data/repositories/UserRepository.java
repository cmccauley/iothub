package me.cmccauley.iothub.data.repositories;

import me.cmccauley.iothub.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
