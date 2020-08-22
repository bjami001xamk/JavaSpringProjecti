package projekti;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.domain.AbstractPersistable;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data 
@NoArgsConstructor
public class FriendRequest extends AbstractPersistable<Long> {
    
    private Long personWhoReceiveFriendRequestId;

    @JsonIgnore
    @ManyToOne
    private Person personWhoSentFriendRequest;
    


    public FriendRequest(Person person, Long person2) {
        this.personWhoSentFriendRequest = person;
        this.personWhoReceiveFriendRequestId = person2;
    }

}