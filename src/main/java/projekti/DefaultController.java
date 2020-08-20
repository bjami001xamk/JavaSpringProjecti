package projekti;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DefaultController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private PostRepository postRepository;

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
        return "person";
    }

    @GetMapping("/profile")
    public String profilePage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Person person = personRepository.findByUsername(username);
        model.addAttribute("person", person);
        model.addAttribute("skills", person.getPersonSkills());
        model.addAttribute("posts", postRepository.findAll());
        return "person";

    }

    
    @GetMapping(path="/allpersons", produces="application/json")
    @ResponseBody
    public List<Person> returnAllPersons(){
        return personRepository.findAll();
    }

    @ResponseBody
    @GetMapping("/testi")
    public String testing() {
        String salasana1 = passwordEncoder.encode("salasana");

        Picture pictureTest = new Picture();
        Person testPerson = new Person("testietu", "testitaka", "testiurl", pictureTest, "user1", salasana1);
        Skill newSkill = new Skill(testPerson,"uusitaito");
        testPerson.getPersonSkills().add(newSkill);
        System.out.println("testi");
        pictureTest.setPerson(testPerson);

        pictureRepository.save(pictureTest);
        personRepository.save(testPerson);
        skillRepository.save(newSkill);
        Picture pictureTest2 = new Picture();
        Person testPerson2 = new Person("aaa", "bbb", "ccc", pictureTest2, "user2", salasana1);
        pictureTest2.setPerson(testPerson2);
        pictureRepository.save(pictureTest2);
        personRepository.save(testPerson2);
        return "ehkä lisätty ehkä ei";
    }

}
