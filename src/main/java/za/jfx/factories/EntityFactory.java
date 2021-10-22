package za.jfx.factories;

import com.sun.istack.NotNull;
import za.jfx.dto.WorkstationDto;
import za.jfx.model.jfx.Workstation;

public class EntityFactory {

    public static WorkstationDto createWorkstationDtoFromWorkstation(@NotNull Workstation workstation) {
        return new WorkstationDto(
                workstation.getId(),
                workstation.getIpAddress(),
                workstation.getHostName(),
                workstation.getHostFullName(),
                workstation.getDescription()
        );
    }

    public static WorkstationDto createWorkstationDto() {
        return new WorkstationDto();
    }

    public static Workstation createWorkstationFromWorkstationDtoWithId(@NotNull WorkstationDto workstationDto) {
        Workstation workstation = new Workstation();
        workstation.setId(workstationDto.getId());
        workstation.setIpAddress(workstationDto.getIpAddress());
        workstation.setHostName(workstationDto.getHostName());
        workstation.setHostFullName(workstationDto.getHostFullName());
        workstation.setDescription(workstationDto.getDescription());
        return workstation;
    }

    public static Workstation createWorkstationFromWorkstationDtoWithoutId(@NotNull WorkstationDto workstationDto) {
        Workstation workstation = new Workstation();
        workstation.setIpAddress(workstationDto.getIpAddress());
        workstation.setHostName(workstationDto.getHostName());
        workstation.setHostFullName(workstationDto.getHostFullName());
        workstation.setDescription(workstationDto.getDescription());
        return workstation;
    }




}
