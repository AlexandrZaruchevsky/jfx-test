package za.jfx.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WorkstationDto {

    private Long id;
    private String ipAddress;
    private String hostName;
    private String hostFullName;
    private String description;

    public WorkstationDto(String ipAddress, String hostName, String hostFullName, String description) {
        this.ipAddress = ipAddress;
        this.hostName = hostName;
        this.hostFullName = hostFullName;
        this.description = description;
    }

    public WorkstationDto(Long id, String ipAddress, String hostName, String hostFullName, String description) {
        this.id = id;
        this.ipAddress = ipAddress;
        this.hostName = hostName;
        this.hostFullName = hostFullName;
        this.description = description;
    }
}
