package projekti;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long>{
    List<Friend> findByPerson1IdOrPerson2Id(Long Id, Long Id2);
}