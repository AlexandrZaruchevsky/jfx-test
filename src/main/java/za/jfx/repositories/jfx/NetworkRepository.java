package za.jfx.repositories.jfx;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.PagingAndSortingRepository;
import za.jfx.model.jfx.Network;
import za.jfx.model.jfx.PointOfPresence;

import java.util.List;

public interface NetworkRepository extends PagingAndSortingRepository<Network, Long> {

    @EntityGraph("Network.AllFields")
    List<Network> findAll();

    List<Network> findByPointOfPresence(PointOfPresence pointOfPresence);

    List<Network> findBySubnet(String subnet);

    List<Network> findByPointOfPresenceAndSubnet(PointOfPresence pointOfPresence, String subnet);

    @EntityGraph("Network.AllFields")
    List<Network> findByIpAdressStartsWith(String ipAddress);

}
