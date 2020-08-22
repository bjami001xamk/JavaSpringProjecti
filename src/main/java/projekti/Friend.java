package projekti;

import javax.persistence.Entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Friend extends AbstractPersistable<Long>{
    private Long person1Id;
    private Long person2Id;

    public Friend(Person person1, Person person2) {
        this.person1Id = person1.getId();
        this.person2Id = person2.getId();
    }

    
}