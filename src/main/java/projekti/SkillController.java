package projekti;

import org.springframework.beans.factory.annotation.Autowired;
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
        return "redirect:/users/" + userUrl;
    }

    @PostMapping("users/{userUrl}/likeskill")
    public String likeSkill(@RequestParam Long skillId, @PathVariable String userUrl) {
        
        //toimiva
        Skill skill = skillRepository.getOne(skillId);
        Person personWhoIsLiking = personRepository.findByUserUrl("ccc");
        
        if(skill.getPeopleWhoLiked().contains(personWhoIsLiking)) {
            return "redirect:/users/" + userUrl;
        }
        skill.getPeopleWhoLiked().add(personWhoIsLiking);
        skillRepository.save(skill);
                
        return "redirect:/users/" + userUrl;
    }
}
