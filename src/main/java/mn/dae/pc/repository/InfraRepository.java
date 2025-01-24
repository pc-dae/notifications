package mn.dae.pc.repository;

import mn.dae.pc.model.Infra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface InfraRepository extends JpaRepository<Infra, Long> {

    List<Infra> findByTypeAndName(String type, String name);

    // Custom query
    @Query("SELECT name FROM Infra i WHERE i.name = :name AND i.type = :type")
    List<Infra> findByTypeName(@Param("type") String type, @Param("name") String name);

}
