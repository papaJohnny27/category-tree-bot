package kz.telegram.categorytreebot.repository;

import kz.telegram.categorytreebot.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByUserIdAndParentIsNull(Long userId);

    boolean existsByNameAndUserId(String name, Long userId);

    Optional<Category> findByNameAndUserId(String name, Long userId );

    @Modifying
    void deleteByNameAndUserId(String name, Long userId );

}
