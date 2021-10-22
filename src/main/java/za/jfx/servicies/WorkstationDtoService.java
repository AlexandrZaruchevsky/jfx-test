package za.jfx.servicies;

import za.jfx.model.jfx.PointOfPresence;
import za.jfx.model.jfx.Workstation;

import java.util.List;

public interface WorkstationDtoService extends CrudService<Workstation> {

    List<Workstation> findByPointOfPresence(PointOfPresence pointOfPresence);
    List<Workstation> findByIpAddress(String ipAddress);
    List<Workstation> findByHostName(String hostName);

}
