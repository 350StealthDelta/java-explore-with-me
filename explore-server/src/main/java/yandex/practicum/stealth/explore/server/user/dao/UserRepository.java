package yandex.practicum.stealth.explore.server.user.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import yandex.practicum.stealth.explore.server.user.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User AS u " +
            "WHERE u.id IN ?1")
    Page<User> findAllByIds(List<Long> ids, Pageable pageable);
}
