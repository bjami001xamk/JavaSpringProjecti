package projekti;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DefaultController {

@Autowired
private Services services;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @GetMapping("/")
    public String helloWorld(Model model) {
        return "redirect:/login";
    }

    @GetMapping("/users/{userUrl}")
    public String personPage(@PathVariable String userUrl, Model model) {
        Person person = personRepository.findByUserUrl(userUrl);
        model.addAttribute("person", person);
        model.addAttribute("skills", person.getPersonSkills());
        model.addAttribute("posts", postRepository.findAll());
        model.addAttribute("friends", services.getPersonsFriends(person.getId()));
        return "otherpersonpage";
    }

    @GetMapping("/profile")
    public String profilePage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Person person = personRepository.findByUsername(username);
        HashMap<String, List<Skill>> skills = services.getSkills();
        model.addAttribute("person", person);
        model.addAttribute("skills", skills.get("top3Skills"));
        model.addAttribute("restOfSkills", skills.get("restOfSkills"));
        model.addAttribute("posts", services.getPostsAndTop10CommentsOnly());
        model.addAttribute("friendRequests", friendRequestRepository.findByPersonWhoReceiveFriendRequestId(person.getId()));
        model.addAttribute("friends", services.getPersonsFriends(person.getId()));

        return "person";

    }

    @GetMapping(path="/allpersons", produces="application/json")
    @ResponseBody
    public List<Person> returnAllPersons(){
        return personRepository.findAll();
    }

}
