package projekti;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PostController {
    
    @Autowired
    private PersonRepository personRepository;
    
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;
    
    @PostMapping("/users/{userUrl}/addPost")
    public String addPost(@PathVariable String userUrl, @RequestParam String postContent) {
        Post newPost = new Post(postContent);
        Person person = personRepository.findByUserUrl(userUrl);
        newPost.setPoster(person);
        postRepository.save(newPost);
        
        return "redirect:/profile";
    }
    
    @PostMapping("/api/likePost")
    public ResponseEntity<String> likePost(@RequestParam Long postId) {
        Post post = postRepository.getOne(postId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person person = personRepository.findByUsername(auth.getName());
        List<Post> postsThatLoggedInPersonHasLiked = person.getLikedPosts();
        
        if(postsThatLoggedInPersonHasLiked.contains(post)) {
            return new ResponseEntity<>("Person has already liked that post", HttpStatus.BAD_REQUEST);
        }

        person.getLikedPosts().add(post);
        personRepository.save(person);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @PostMapping("/api/addMessageToPost")
        public ResponseEntity<String> addMessageToPost(@RequestParam Long id, String content) {
        Post post = postRepository.getOne(id);
        Comment comment = new Comment(content, post);
        commentRepository.save(comment);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
