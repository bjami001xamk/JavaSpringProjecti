package projekti;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long>{
    List<FriendRequest> findByPersonWhoReceiveFriendRequestId(Long id);

}