package yandex.practicum.stealth.explore.server.category.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import yandex.practicum.stealth.explore.server.category.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
