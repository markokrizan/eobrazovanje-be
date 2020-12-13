package rs.ac.uns.ftn.education.repository;

import rs.ac.uns.ftn.education.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository<T extends User>  extends BaseRepository<T, Long> {
    List<T> findByIdIn(List<Long> userIds);

    Optional<T> findByUsername(String username);

    Boolean existsByUsername(String username);

    Page<T> findAll(Pageable page);
}
