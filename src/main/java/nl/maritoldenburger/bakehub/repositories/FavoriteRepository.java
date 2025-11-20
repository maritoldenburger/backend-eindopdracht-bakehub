package nl.maritoldenburger.bakehub.repositories;

import nl.maritoldenburger.bakehub.models.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
}
