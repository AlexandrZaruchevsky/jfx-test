package za.jfx.servicies.impl;

import org.springframework.stereotype.Service;
import za.jfx.model.jfx.PointOfPresence;
import za.jfx.repositories.jfx.PointOfPresenceRepository;
import za.jfx.servicies.PointOfPresenceService;

import java.util.List;

@Service
public class PointOfPresenceServiceImpl implements PointOfPresenceService {

    private final PointOfPresenceRepository pointOfPresenceRepository;

    public PointOfPresenceServiceImpl(PointOfPresenceRepository pointOfPresenceRepository) {
        this.pointOfPresenceRepository = pointOfPresenceRepository;
    }

    @Override
    public PointOfPresence add(PointOfPresence pointOfPresence) {
        return null;
    }

    @Override
    public void addAll(List<PointOfPresence> pointOfPresences) {

    }

    @Override
    public PointOfPresence update(PointOfPresence pointOfPresence) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public PointOfPresence findById(Long id) {
        return null;
    }

    @Override
    public List<PointOfPresence> findAll() {
        return pointOfPresenceRepository.findAll();
    }
}
