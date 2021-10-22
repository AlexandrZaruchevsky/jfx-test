package za.jfx.servicies.impl;

import org.springframework.stereotype.Service;
import za.jfx.model.jfx.PointOfPresence;
import za.jfx.model.jfx.Workstation;
import za.jfx.repositories.jfx.WorkstationForDtoRepository;
import za.jfx.servicies.WorkstationDtoService;

import java.util.List;

@Service
public class WorkstationDtoServiceImpl implements WorkstationDtoService {

    private final WorkstationForDtoRepository workstationForDtoRepository;

    public WorkstationDtoServiceImpl(WorkstationForDtoRepository workstationForDtoRepository) {
        this.workstationForDtoRepository = workstationForDtoRepository;
    }

    @Override
    public List<Workstation> findByPointOfPresence(PointOfPresence pointOfPresence) {
        return null;
    }

    @Override
    public List<Workstation> findByIpAddress(String ipAddress) {
        return null;
    }

    @Override
    public List<Workstation> findByHostName(String hostName) {
        return null;
    }

    @Override
    public Workstation add(Workstation workstation) {
        return null;
    }

    @Override
    public void addAll(List<Workstation> workstations) {

    }

    @Override
    public Workstation update(Workstation workstation) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Workstation findById(Long id) {
        return null;
    }

    @Override
    public List<Workstation> findAll() {
        return null;
    }
}
