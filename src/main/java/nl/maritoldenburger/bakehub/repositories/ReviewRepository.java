package nl.maritoldenburger.bakehub.repositories;

import nl.maritoldenburger.bakehub.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
