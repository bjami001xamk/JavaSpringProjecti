package projekti;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Skill extends AbstractPersistable<Long>{

    @ManyToOne
    private Person person;
    private String skillName;

    @ManyToMany
    private List<Person> peopleWhoLiked = new ArrayList<>();
    
    public Skill(Person person, String skillName) {
        this.person = person;
        this.skillName = skillName;
        this.peopleWhoLiked = new ArrayList<>();
    }
}
