package za.jfx.servicies.impl;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import za.jfx.dto.EmployeeDto;
import za.jfx.dto.EquipmentDto;
import za.jfx.factories.EmployeeFactory;
import za.jfx.factories.EquipmentFactory;
import za.jfx.model.jfx.Equipment;
import za.jfx.repositories.jfx.EquipmentRepository;
import za.jfx.servicies.EquipmentDtoService;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Primary
public class EquipmentDtoServiceImpl implements EquipmentDtoService {

    private final EquipmentRepository equipmentRepository;

    public EquipmentDtoServiceImpl(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    @Override
    public List<EquipmentDto> findAllByEmployee(EmployeeDto employee) {
        return equipmentRepository.findAllByEmployee(EmployeeFactory.createEmployeeFromDto(employee)).stream()
                .map(EquipmentFactory::createEquipmentDtoFromEquipment)
                .collect(Collectors.toList());
    }

    @Override
    public List<EquipmentDto> findByNetwork(boolean isNetwork) {
//        return equipmentRepository.findAllByNetwork(isNetwork).stream()
//                .map(EquipmentFactory::createEquipmentDtoFromEquipment)
//                .collect(Collectors.toList());
        return null;
    }

    @Override
    public void addAllEq(List<Equipment> equipments) {
        equipmentRepository.saveAll(equipments);
    }

    @Override
    public EquipmentDto add(EquipmentDto equipmentDto) {
        return EquipmentFactory.createEquipmentDtoFromEquipment(
                equipmentRepository.save(EquipmentFactory.createEquipmentFromNewDto(equipmentDto))
        );
    }

    @Override
    public void addAll(List<EquipmentDto> equipmentDtoDtos) {
        equipmentRepository.saveAll(
                equipmentDtoDtos.stream()
                        .map(EquipmentFactory::createEquipmentFromNewDto)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public EquipmentDto update(EquipmentDto equipmentDto) {
        return EquipmentFactory.createEquipmentDtoFromEquipment(
                equipmentRepository.save(
                        EquipmentFactory.createEquipmentFromDto(equipmentDto)
                )
        );
    }

    @Override
    public void delete(Long id) {
        equipmentRepository.deleteById(id);
    }

    @Override
    public EquipmentDto findById(Long id) {
        return EquipmentFactory.createEquipmentDtoFromEquipment(
                equipmentRepository.findById(id).orElse(null)
        );
    }

    @Override
    public List<EquipmentDto> findAll() {
        return equipmentRepository.findAll().stream()
                .map(EquipmentFactory::createEquipmentDtoFromEquipment)
                .collect(Collectors.toList());
    }

}
