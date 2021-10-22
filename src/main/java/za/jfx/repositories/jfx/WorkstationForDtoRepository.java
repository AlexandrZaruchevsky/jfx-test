package za.jfx.repositories.jfx;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.PagingAndSortingRepository;
import za.jfx.model.jfx.PointOfPresence;
import za.jfx.model.jfx.Workstation;

import java.util.List;
import java.util.Optional;

public interface WorkstationForDtoRepository extends PagingAndSortingRepository<Workstation, Long> {

    List<Workstation> findAll();

    List<Workstation> findAllByPointOfPresence(PointOfPresence pointOfPresence);

    @EntityGraph("Workstation.PointOfPresenceField")
    Optional<Workstation> findById(Long id);

}
