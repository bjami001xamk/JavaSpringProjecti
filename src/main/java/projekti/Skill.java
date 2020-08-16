package projekti;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
    //private List<Long> peopleWhoHasLiked = new ArrayList<>();

    //@OneToMany("mappedBy = skill")
    //private List<Person> peopleWhoHasliked = new ArrayList<>();


}
