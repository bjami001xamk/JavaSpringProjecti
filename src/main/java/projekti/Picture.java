package projekti;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Picture extends AbstractPersistable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fileName;
    private String contentType;
    private Long size;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] content;

    @OneToOne(mappedBy = "picture")
    @MapsId
    private Person person;

}
