package projekti;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class PictureController {
    
    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private PersonRepository personRepository;

    @PostMapping("/addPicture")
    public String receivePicture(@RequestParam("file") MultipartFile file, @RequestParam String userUrl) throws IOException {
        Person person = personRepository.findByUserUrl(userUrl);
        Picture newPicture = person.getPicture();
        newPicture.setContent(file.getBytes());
        newPicture.setFileName(file.getOriginalFilename());
        newPicture.setSize(file.getSize());
        newPicture.setContentType(file.getContentType());
        person.setPicture(newPicture);
        newPicture.setPerson((person));
        pictureRepository.save(newPicture);

        return "redirect:/profile/";
    }

    
    @GetMapping("/pictures/{userUrl}")
    public ResponseEntity<byte[]> testPicture(@PathVariable String userUrl) {
        Person person = personRepository.findByUserUrl(userUrl);
        Picture picture = person.getPicture();
        if(picture.getFileName() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person has not set profile image yet");
        }
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(picture.getContentType()));
        headers.setContentLength(picture.getSize());
        headers.add("Content-Disposition", "attachment; filename=" + picture.getFileName());
        return new ResponseEntity<>(picture.getContent(), headers, HttpStatus.CREATED);
    }
}