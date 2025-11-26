package nl.maritoldenburger.bakehub.repositories;

import nl.maritoldenburger.bakehub.models.Favorite;
import nl.maritoldenburger.bakehub.models.Recipe;
import nl.maritoldenburger.bakehub.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    boolean existsByUserAndRecipe(User user, Recipe recipe);
}
