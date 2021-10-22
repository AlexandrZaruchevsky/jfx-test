package za.jfx.servicies;

import za.jfx.dto.EmployeeDto;
import za.jfx.dto.EquipmentDto;
import za.jfx.model.jfx.Equipment;

import java.util.List;

public interface EquipmentDtoService extends CrudService<EquipmentDto> {

    List<EquipmentDto> findAllByEmployee(EmployeeDto employee);

    List<EquipmentDto> findByNetwork(boolean isNetwork);

    void addAllEq(List<Equipment> equipments);



}
