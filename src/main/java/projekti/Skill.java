package projekti;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;



@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode (callSuper = false)
public class Skill extends AbstractPersistable<Long>{

    @ManyToOne
    private Person person;
    private String skillName;

    private int peopleWhoLiked;
    
    public Skill(Person person, String skillName) {
        this.person = person;
        this.skillName = skillName;
        this.peopleWhoLiked = 0;
    }
}
