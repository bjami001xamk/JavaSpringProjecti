package projekti;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class Services {
    
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private SkillRepository skillRepository;
    
    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private PersonRepository personRepository;

    public Page<Post> get25PostsAndTop10CommentsOnly() {
        Pageable pageable = PageRequest.of(0, 25, Sort.by("postDate").descending());
        Page<Post> allPosts = postRepository.findAll(pageable);

        for(Post post : allPosts) {
            List<Comment> allComments = post.getPostComments();
            List<Comment> top10Comments = allComments.stream().sorted((a,b) -> b.getId().intValue() - a.getId().intValue())
                        .limit(10)
                        .collect(Collectors.toList());
            post.setPostComments(top10Comments);
        }        
        return allPosts;
    }

    public HashMap<String, List<Skill>> getSkills(Person person) {
        List<Skill> top3Skills = new ArrayList<>();
        List<Skill> restOfSkills = new ArrayList<>();
        List<Skill> ArrangedSkills = skillRepository.findByPerson(person).stream()
            .sorted((a,b) -> b.getPeopleWhoLiked() - a.getPeopleWhoLiked())
            .collect(Collectors.toList());

        for(int i=0;i<ArrangedSkills.size();i++) {
            if(i<3) {
                top3Skills.add(ArrangedSkills.get(i));
            } else {
                restOfSkills.add(ArrangedSkills.get(i));
            }
            System.out.println(ArrangedSkills.get(i).getPeopleWhoLiked());
        }

        HashMap<String, List<Skill>> hashMap = new HashMap<String, List<Skill>>();
        hashMap.put("top3Skills", top3Skills);
        hashMap.put("restOfSkills", restOfSkills);
        return hashMap;
    }

    public List<Person> getPersonsFriends(Long personId) {
        List<Friend> friendships = friendRepository.findByPerson1IdOrPerson2Id(personId, personId);
        
        List<Person> personsWhoAreFriends = new ArrayList<Person>();
        
        for(Friend friend : friendships) {
            if(friend.getPerson1Id() == personId) {
                personsWhoAreFriends.add(personRepository.getOne(friend.getPerson2Id()));
            } else {
                personsWhoAreFriends.add(personRepository.getOne(friend.getPerson1Id()));
            }
        }
        return personsWhoAreFriends;
    }

}