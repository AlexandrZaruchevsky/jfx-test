package za.jfx.servicies.impl;

import org.springframework.stereotype.Service;
import za.jfx.model.jfx.PointOfPresence;
import za.jfx.model.jfx.Workstation;
import za.jfx.repositories.jfx.WorkstationRepository;
import za.jfx.servicies.WorkstationService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkstationServiceImpl implements WorkstationService {

    private final WorkstationRepository workstationRepository;

    public WorkstationServiceImpl(WorkstationRepository workstationRepository) {
        this.workstationRepository = workstationRepository;
    }

    @Override
    public List<Workstation> findByPointOfPresence(PointOfPresence pointOfPresence) {
        return sortByHostName(workstationRepository.findByPointOfPresence(pointOfPresence));
    }

    @Override
    public List<Workstation> findByIpAddress(String ipAddress) {
        return workstationRepository.findByIpAddressContains(ipAddress);
    }

    @Override
    public List<Workstation> findByHostName(String hostName) {
        return workstationRepository.findByHostNameContains(hostName);
    }

    @Override
    public Workstation add(Workstation workstation) {
        return workstationRepository.save(workstation);
    }

    @Override
    public void addAll(List<Workstation> workstations) {
        workstationRepository.saveAll(workstations);
    }

    @Override
    public Workstation update(Workstation workstation) {
        return workstationRepository.save(workstation);
    }

    @Override
    public void delete(Long id) {
        workstationRepository.deleteById(id);
    }

    @Override
    public Workstation findById(Long id) {
        return workstationRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Workstation> findAll() {
        return sortByHostName(workstationRepository.findAll());
    }

    private List<Workstation> sortByHostName(List<Workstation> workstations) {
        return workstations.stream()
                .sorted(Comparator.comparing(workstation -> workstation.getHostName().toLowerCase()))
                .collect(Collectors.toList());
    }

}
