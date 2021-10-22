package za.jfx.servicies.impl;

import org.springframework.stereotype.Service;
import za.jfx.model.jfx.Network;
import za.jfx.model.jfx.PointOfPresence;
import za.jfx.repositories.jfx.NetworkRepository;
import za.jfx.servicies.NetworkService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NetworkServiceImpl implements NetworkService {

    private final NetworkRepository networkRepository;

    public NetworkServiceImpl(NetworkRepository networkRepository) {
        this.networkRepository = networkRepository;
    }

    @Override
    public List<Network> findByPointOfPresence(PointOfPresence pointOfPresence) {
        return networkRepository.findByPointOfPresence(pointOfPresence);
    }

    @Override
    public List<Network> findBySubnet(String subnet) {
        return null;
    }

    @Override
    public List<Network> findBySubnet(PointOfPresence pointOfPresence, String subnet) {
        return networkRepository.findByPointOfPresenceAndSubnet(pointOfPresence, subnet);
    }

    @Override
    public List<Network> findByIpAddress(String ipAddress) {
        return sortByHostName(networkRepository.findByIpAdressStartsWith(ipAddress));
    }

    @Override
    public void deleteBySubnet(List<Network> networks) {
        networkRepository.deleteAll(networks);
    }

    @Override
    public Network add(Network network) {
        return null;
    }

    @Override
    public void addAll(List<Network> networks) {
        networkRepository.saveAll(networks);
    }

    @Override
    public Network update(Network network) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Network findById(Long id) {
        return null;
    }

    @Override
    public List<Network> findAll() {
        return sortByHostName(networkRepository.findAll());
    }

    private List<Network> sortByHostName(List<Network> networks){
        return networks.stream()
                .sorted(Comparator.comparing(Network::getHostName))
                .collect(Collectors.toList());
    }
}
