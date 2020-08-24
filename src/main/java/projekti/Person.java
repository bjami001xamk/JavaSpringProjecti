package projekti;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Person extends AbstractPersistable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    private String firstName;

    private String lastName;

    private String userUrl;

    @JsonIgnore
    private String username;

    @JsonIgnore
    private String password;


    @OneToMany(mappedBy = "person")
    @JsonIgnore
    private List<Skill> personSkills = new ArrayList<>();
    
    @ManyToMany(mappedBy = "person")
    @JsonIgnore
    private List<Skill> skillsThatPersonHasLiked = new ArrayList<>();

    @OneToOne(cascade = CascadeType.MERGE) // Fetch type lazy?
    @JsonIgnore
    @JoinColumn(name= "picture_id")
    private Picture picture;
   
    @OneToMany(mappedBy = "poster")
    @JsonIgnore
    private List<Post> posts = new ArrayList<>();
    
    public Person(String firstName, String lastName, String userUrl, Picture picture, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userUrl = userUrl;
        this.picture = picture;
        this.username = username;
        this.password = password;
        this.friendRequests = new ArrayList<FriendRequest>();
        this.likedPosts = new ArrayList<Post>();
    }

    public Person(PersonValidation person){
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.userUrl = person.getUserUrl();
        this.username = person.getUsername();
        this.password = person.getPassword();
        this.friendRequests = new ArrayList<FriendRequest>();
    }


    @ManyToMany
    @JsonIgnore
    private List<Post> likedPosts = new ArrayList<>();
    
    @JsonIgnore
    @OneToMany(mappedBy = "personWhoSentFriendRequest")
    private List<FriendRequest> friendRequests = new ArrayList<>();

    /*@JsonIgnore
    @OneToMany(mappedBy = "PersonWhoRequested")
    private List<Friend> Friends = new ArrayList<>();*/
    



}
