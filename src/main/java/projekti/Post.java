
package projekti;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Post extends AbstractPersistable<Long>{
    private String content;
    private LocalDateTime postDate;
    private String postTime;
    
    @ManyToOne
    private Person poster;
    
    @ManyToMany(mappedBy = "likedPosts")
    private List<Person> postLikes;
    
    
    public Post(String content) {
        this.content = content;
        this.postDate = LocalDateTime.now();

        String minutes = postDate.getMinute() < 10 
            ? "0" + postDate.getMinute() 
            : String.valueOf(postDate.getMinute());

        this.postTime = postDate.getDayOfMonth() + "." + postDate.getMonthValue() + "." + postDate.getYear() + " " + postDate.getHour() + ":" + minutes;
    }
}
