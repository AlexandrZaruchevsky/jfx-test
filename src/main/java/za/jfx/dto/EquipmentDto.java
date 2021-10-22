package za.jfx.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EquipmentDto {

    private Long id;
    private String name;
    private String fullName;
    private String manufacturer;
    private String countryOfManufacture;
    private LocalDate dateOfManufacture;
    private String inventoryNumber;
    private String serialNumber;
    private boolean isNetwork;

    private String hostName;
    private String hostFullName;
    private String ipAddress;

    public EquipmentDto() {
    }

    public EquipmentDto(
            Long id,
            String name,
            String fullName,
            String manufacturer,
            String countryOfManufacture,
            LocalDate dateOfManufacture,
            String inventoryNumber,
            String serialNumber,
            boolean isNetwork,
            String hostName,
            String hostFullName,
            String ipAddress
    ) {
        this.id = id;
        this.name = name;
        this.fullName = fullName;
        this.manufacturer = manufacturer;
        this.countryOfManufacture = countryOfManufacture;
        this.dateOfManufacture = dateOfManufacture;
        this.inventoryNumber = inventoryNumber;
        this.serialNumber = serialNumber;
        this.isNetwork = isNetwork;
        this.hostName = hostName;
        this.hostFullName = hostFullName;
        this.ipAddress = ipAddress;
    }

}
