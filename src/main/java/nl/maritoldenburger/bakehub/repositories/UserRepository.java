package nl.maritoldenburger.bakehub.repositories;

import nl.maritoldenburger.bakehub.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
