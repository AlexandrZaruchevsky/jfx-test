package za.jfx.servicies.impl;

import org.springframework.stereotype.Service;
import za.jfx.model.jfx.Equipment;
import za.jfx.repositories.jfx.EquipmentRepository;
import za.jfx.servicies.EquipmentService;

import java.util.List;

@Service
public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentRepository equipmentRepository;

    public EquipmentServiceImpl(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    @Override
    public Equipment add(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    @Override
    public void addAll(List<Equipment> equipment) {
        equipmentRepository.saveAll(equipment);
    }

    @Override
    public Equipment update(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    @Override
    public void delete(Long id) {
        equipmentRepository.deleteById(id);
    }

    @Override
    public Equipment findById(Long id) {
        return equipmentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Equipment> findAll() {
        return equipmentRepository.findAll();
    }
}
