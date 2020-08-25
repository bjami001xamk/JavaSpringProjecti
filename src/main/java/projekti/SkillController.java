package projekti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SkillController {

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private PersonRepository personRepository;

    @PostMapping("/users/{userUrl}/addskill")
    public String addNewSkill(@RequestParam String skillText, @PathVariable String userUrl) {
        Person person = personRepository.findByUserUrl(userUrl);
        Skill newSkill = new Skill(person, skillText);
        person.getPersonSkills().add(newSkill);
        skillRepository.save(newSkill);
        personRepository.save(person);
        return "redirect:/profile/";
    }

    @PostMapping("api/likeSkill")
    public ResponseEntity<String> likeSkill(@RequestParam Long skillId) {
        Skill skill = skillRepository.getOne(skillId);
        skill.setPeopleWhoLiked(skill.getPeopleWhoLiked() + 1);
        skillRepository.save(skill);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
