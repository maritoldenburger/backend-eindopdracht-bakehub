package nl.maritoldenburger.bakehub.repositories;

import nl.maritoldenburger.bakehub.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
