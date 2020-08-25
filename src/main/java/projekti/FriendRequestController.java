package projekti;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class FriendRequestController {
    
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Autowired
    private FriendRepository friendRepository;

    @Transactional
    @PostMapping("/api/addFriendRequest")
    public ResponseEntity<String> addFriendRequest(@RequestParam String userUrl) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person person = personRepository.findByUserUrl(userUrl);
        Person LoggedInPerson = personRepository.findByUsername(auth.getName());
        FriendRequest friendRequest = new FriendRequest(LoggedInPerson, person.getId());
        if(friendRequestRepository.findByPersonWhoSentFriendRequestAndPersonWhoReceiveFriendRequestId(LoggedInPerson, person.getId()) != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        friendRequestRepository.save(friendRequest);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/api/acceptAsFriend")
    public String acceptAsFriend(@RequestParam Long id) {
        FriendRequest friendRequest = friendRequestRepository.getOne(id);
        Person person1 = friendRequest.getPersonWhoSentFriendRequest();
        Person person2 = personRepository.getOne(friendRequest.getPersonWhoReceiveFriendRequestId());
        Friend newFriendship = new Friend(person1, person2);
        friendRepository.save(newFriendship);
        friendRequestRepository.deleteById(id);
        return "redirect:/profile";
    }

    @Transactional
    @PostMapping("/api/removeFriend")
    public String removeFriend(@RequestParam Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long id2 = personRepository.findByUsername(auth.getName()).getId();
        List<Friend> Friendships = friendRepository.findAll();
        for(Friend friend : Friendships) {
            if((friend.getPerson1Id() == id && friend.getPerson2Id() == id2) ||
                (friend.getPerson1Id() == id2 && friend.getPerson2Id() == id)
            ) {
                friendRepository.delete(friend);
                return "redirect:/profile";
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        //return "redirect:/profile";
    }
    
    @Transactional
    @PostMapping("/api/declineAsFriend")
    public ResponseEntity<String> declineFriend(@RequestParam Long id){
        friendRequestRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}