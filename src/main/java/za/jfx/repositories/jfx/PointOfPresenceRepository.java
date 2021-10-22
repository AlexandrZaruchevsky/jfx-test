package za.jfx.repositories.jfx;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.PagingAndSortingRepository;
import za.jfx.model.jfx.PointOfPresence;

import java.util.List;

public interface PointOfPresenceRepository extends PagingAndSortingRepository<PointOfPresence, Long> {

    List<PointOfPresence> findAll();

    @EntityGraph("PointOfPresence.AllFields")
    List<PointOfPresence> findByName(String name);

}
