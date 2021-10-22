package za.jfx.servicies;

import za.jfx.model.jfx.PointOfPresence;

import java.util.List;

public interface PointOfPresenceService extends CrudService<PointOfPresence> {

    List<PointOfPresence> findAll();

}
