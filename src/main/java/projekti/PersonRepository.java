package projekti;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
        Person findByUserUrl(String userUrl);
        Person findByUsername(String username);
}
