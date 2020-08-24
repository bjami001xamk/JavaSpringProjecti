package projekti;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Services {
    
    @Autowired
    private PostRepository postRepository;

    public List<Post> getPostsAndTop10CommentsOnly() {
        List<Post> allPosts = postRepository.findAll();
        for(Post post : allPosts) {
            List<Comment> allComments = post.getPostComments();
            List<Comment> top10Comments = allComments.stream().sorted((a,b) -> b.getId().intValue() - a.getId().intValue()).limit(10).collect(Collectors.toList());
            post.setPostComments(top10Comments);
        }
        
        return allPosts;
    }
}