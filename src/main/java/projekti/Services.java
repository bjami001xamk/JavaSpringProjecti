package projekti;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Services {
    
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private SkillRepository skillRepository;

    public List<Post> getPostsAndTop10CommentsOnly() {
        List<Post> allPosts = postRepository.findAll();
        for(Post post : allPosts) {
            List<Comment> allComments = post.getPostComments();
            List<Comment> top10Comments = allComments.stream().sorted((a,b) -> b.getId().intValue() - a.getId().intValue()).limit(10).collect(Collectors.toList());
            post.setPostComments(top10Comments);
        }        
        return allPosts;
    }

    public HashMap<String, List<Skill>> getSkills() {
        /*Pageable pageable = PageRequest.of(0,3, Sort.by("peopleWhoLiked").descending());
        Page<Skill> top3Skills = skillRepository.findAll(pageable);
        return top3Skills;*/
        List<Skill> top3Skills = new ArrayList<>();
        List<Skill> restOfSkills = new ArrayList<>();
        List<Skill> ArrangedSkills = skillRepository.findAll().stream()
            .sorted((a,b) -> a.getPeopleWhoLiked().size() - b.getPeopleWhoLiked().size())
            .collect(Collectors.toList());

        for(int i=0;i<ArrangedSkills.size();i++) {
            if(i<3) {
                top3Skills.add(ArrangedSkills.get(i));
            } else {
                restOfSkills.add(ArrangedSkills.get(i));
            }
        }

        HashMap<String, List<Skill>> hashMap = new HashMap<String, List<Skill>>();
        hashMap.put("top3Skills", top3Skills);
        hashMap.put("restOfSkills", restOfSkills);
        return hashMap;
    }

    /*public List<Skill> getRestOfSkills() {
        List<Skill> allSkills = skillRepository.findAll();
        allSkills.remove(2);allSkills.remove(1);allSkills.remove(0);
        return allSkills;
    }*/
}