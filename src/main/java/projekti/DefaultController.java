package projekti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class DefaultController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PictureRepository pictureRepository;

    @GetMapping("/")
    public String helloWorld(Model model) {
        model.addAttribute("message", "World!");
        model.addAttribute("testi", "testi");
        //Person person = personRepository.findByUserUrl("testiurl");
        return "index";
    }

    @PostMapping("/login")
    public String login(@RequestParam String userId, @RequestParam String password) {
        Account account = accountRepository.findByUsername(userId);


        System.out.println(userId);
        System.out.println(password);
        return "index";
    }

    @GetMapping("/users/{userUrl}")
    public String personPage(@PathVariable String userUrl, Model model) {
        model.addAttribute("person", personRepository.findByUserUrl(userUrl));
        return "person";
    }

    @ResponseBody
    @GetMapping("/testi")
    public String testing() {
        Picture pictureTest = new Picture();
        Person testPerson = new Person("testietu", "testitaka", "testiurl", pictureTest);
        pictureTest.setPerson(testPerson);
        pictureRepository.save(pictureTest);
        personRepository.save(testPerson);

        Picture pictureTest2 = new Picture();
        Person testPerson2 = new Person("aaa", "bbb", "ccc", pictureTest2);
        pictureTest2.setPerson(testPerson2);
        pictureRepository.save(pictureTest2);
        personRepository.save(testPerson2);
        return "ehkä lisätty ehkä ei";
    }

    @PostMapping("/addPicture")
    public String receivePicture(@RequestParam("file") MultipartFile file, @RequestParam String userUrl) throws IOException {
        Person person = personRepository.findByUserUrl(userUrl);
        Picture newPicture = person.getPicture();
        //newPicture.setContent(file.getBytes());
        newPicture.setFileName(file.getOriginalFilename());
        newPicture.setSize(file.getSize());
        newPicture.setContentType(file.getContentType());

        person.setPicture(newPicture);
        newPicture.setPerson((person));

        pictureRepository.save(newPicture);
        //personRepository.save(person);

        return "redirect:/users/" + userUrl;
    }

    @GetMapping("/pictures/{userUrl}/")
    public ResponseEntity<byte[]> testPicture(@PathVariable String userUrl) {
        Person person = personRepository.findByUserUrl(userUrl);
        Picture picture = person.getPicture();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(picture.getContentType()));
        headers.setContentLength(picture.getSize());
        //headers.add("Content-Disposition", "attachment; filename=" + picture.getFileName());
        return new ResponseEntity<>(picture.getContent(), headers, HttpStatus.CREATED);
    }

}
