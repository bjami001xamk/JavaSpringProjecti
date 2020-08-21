package projekti;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SecurityController {
    
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    
    @PostMapping("/register")
    public String Register(@Valid @ModelAttribute PersonValidation incomingPerson, HttpServletRequest request) {
        System.out.println("testi");
        Person person = new Person(incomingPerson);
        person.setPassword(passwordEncoder.encode(incomingPerson.getPassword()));
        Picture picture = new Picture();
        person.setPicture(picture);
        picture.setPerson(person);
        pictureRepository.save(picture);
        personRepository.save(person);
        
        try {
            request.login(incomingPerson.getUsername(), incomingPerson.getPassword());
        } catch (Exception e) {
            return "Error with login" + e;
        }
            
        return "redirect:/profile";
        
    }
}