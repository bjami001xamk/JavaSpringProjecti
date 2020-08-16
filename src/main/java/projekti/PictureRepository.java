package projekti;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface PictureRepository extends JpaRepository<Picture, Long> {

}
