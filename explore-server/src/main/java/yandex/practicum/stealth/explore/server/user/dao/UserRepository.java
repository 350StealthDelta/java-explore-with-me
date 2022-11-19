package yandex.practicum.stealth.explore.server.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import yandex.practicum.stealth.explore.server.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
