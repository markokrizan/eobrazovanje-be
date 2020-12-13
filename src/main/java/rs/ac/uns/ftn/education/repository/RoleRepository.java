package rs.ac.uns.ftn.education.repository;

import rs.ac.uns.ftn.education.model.Role;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends BaseRepository<Role, Long> {
    Optional<Role> findByName(String roleName);
}
