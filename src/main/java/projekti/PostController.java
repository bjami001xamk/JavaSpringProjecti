/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Miett
 */
@Controller
public class PostController {
    
    @Autowired
    private PersonRepository personRepository;
    
    @Autowired
    private PostRepository postRepository;
    
    @PostMapping("/users/{userUrl}/addPost")
    public String addPost(@PathVariable String userUrl, @RequestParam String postContent) {
        Post newPost = new Post(postContent);
        Person person = personRepository.findByUserUrl(userUrl);
        newPost.setPoster(person);
        postRepository.save(newPost);
        
        return "redirect:/users/" + userUrl;
    }
    
    @PostMapping("/users/{userUrl}/likepost")
    public String likePoist(@PathVariable String userUrl, @RequestParam Long postId) {
        Post post = postRepository.getOne(postId);
        // need to add who is liking
        Person person = personRepository.findByUserUrl("ccc");
        
        person.getLikedPosts().add(post);
        personRepository.save(person);

        //post.getPostLikes().add(person);
        //postRepository.save(post);
        
        return "redirect:/users/" + userUrl;
    }
    
    //@ResponseBody
    @PostMapping("/api/addMessageToPost")
        public ResponseEntity<String> addMessageToPost(@RequestParam Long id, String content) {
        System.out.println(content);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
