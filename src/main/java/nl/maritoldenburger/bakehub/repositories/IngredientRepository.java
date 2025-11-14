package nl.maritoldenburger.bakehub.repositories;

import nl.maritoldenburger.bakehub.models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
