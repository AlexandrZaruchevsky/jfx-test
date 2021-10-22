package za.jfx.factories;

import za.jfx.dto.EquipmentDto;
import za.jfx.model.jfx.Equipment;

public final class EquipmentFactory {

    private EquipmentFactory() {
    }

    public static EquipmentDto createEquipmentDtoFromEquipmentNew(Equipment equipment) {
        return new EquipmentDto(
                null,
                equipment.getName(),
                equipment.getFullName(),
                equipment.getManufacturer(),
                equipment.getCountryOfManufacture(),
                equipment.getDateOfManufacture(),
                equipment.getInventoryNumber(),
                equipment.getSerialNumber(),
                equipment.isNetwork(),
                equipment.getHostName(),
                equipment.getHostFullName(),
                equipment.getIpAddress()
        );
    }

    public static EquipmentDto createEquipmentDtoFromEquipment(Equipment equipment) {
        EquipmentDto equipmentDtoFromEquipmentDtoNew = createEquipmentDtoFromEquipmentNew(equipment);
        equipmentDtoFromEquipmentDtoNew.setId(equipment.getId());
        return equipmentDtoFromEquipmentDtoNew;
    }

    public static Equipment createEquipmentFromNewDto(EquipmentDto equipmentDto) {
        Equipment equipment = new Equipment();
        equipment.setName(equipmentDto.getName());
        equipment.setFullName(equipmentDto.getFullName());
        equipment.setManufacturer(equipmentDto.getManufacturer());
        equipment.setCountryOfManufacture(equipmentDto.getCountryOfManufacture());
        equipment.setDateOfManufacture(equipmentDto.getDateOfManufacture());
        equipment.setInventoryNumber(equipmentDto.getInventoryNumber());
        equipment.setSerialNumber(equipmentDto.getSerialNumber());
        equipment.setNetwork(equipmentDto.isNetwork());
        equipment.setHostName(equipmentDto.getHostName());
        equipment.setHostFullName(equipmentDto.getHostFullName());
        equipment.setIpAddress(equipmentDto.getIpAddress());
        return equipment;
    }

    public static Equipment createEquipmentFromDto(EquipmentDto equipmentDto) {
        Equipment equipmentFromNewDto = createEquipmentFromNewDto(equipmentDto);
        equipmentFromNewDto.setId(equipmentDto.getId());
        return equipmentFromNewDto;
    }

}
