package za.jfx.repositories.jfx;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.PagingAndSortingRepository;
import za.jfx.model.jfx.PointOfPresence;
import za.jfx.model.jfx.Workstation;

import java.util.List;

public interface WorkstationRepository extends PagingAndSortingRepository<Workstation, Long> {

//    @EntityGraph("Workstation.AllFields")
    List<Workstation> findAll();

//    @EntityGraph("Workstation.AllFields")
    @EntityGraph("Workstation.PointOfPresenceField")
    List<Workstation> findByPointOfPresence(PointOfPresence pointOfPresence);

    List<Workstation> findByIpAddressContains(String ipAddress);
    List<Workstation> findByHostNameContains(String hostName);

}
