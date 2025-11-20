package nl.maritoldenburger.bakehub.repositories;

import nl.maritoldenburger.bakehub.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
