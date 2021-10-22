package za.jfx.servicies;

import za.jfx.model.jfx.Network;
import za.jfx.model.jfx.PointOfPresence;

import java.util.List;

public interface NetworkService extends CrudService<Network> {

    List<Network> findByPointOfPresence(PointOfPresence pointOfPresence);

    List<Network> findBySubnet(String subnet);

    List<Network> findBySubnet(PointOfPresence pointOfPresence, String subnet);

    List<Network> findByIpAddress(String ipAddress);

    void deleteBySubnet(List<Network> networks);

}
