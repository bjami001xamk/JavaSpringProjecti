package projekti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FriendRequestController {
    
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @PostMapping("/api/addFriendRequest")
    public ResponseEntity<String> addFriendRequest(@RequestParam String userUrl) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person person = personRepository.findByUserUrl(userUrl);
        Person LoggedInPerson = personRepository.findByUsername(auth.getName());
        FriendRequest friendRequest = new FriendRequest(LoggedInPerson, person.getId());
        friendRequestRepository.save(friendRequest);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/api/acceptAsFriend")
    public String acceptAsFriend(@RequestParam Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person LoggedInPerson = personRepository.findByUsername(auth.getName());
        FriendRequest friendRequest = friendRequestRepository.getOne(id);
        

        return "redirect:/profile";
    }

    

}