package projekti;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Person extends AbstractPersistable<Long> {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;
//    private List<String> skills = new ArrayList<>();
//    private List<String> posts = new ArrayList<>();
    private String userUrl;

    @OneToOne(cascade = CascadeType.MERGE) // Fetch type lazy?
    @JoinColumn(name= "picture_id")
    private Picture picture;

    public Person(String firstName, String lastName, String userUrl, Picture picture) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userUrl = userUrl;
        this.picture = picture;


    }

}
