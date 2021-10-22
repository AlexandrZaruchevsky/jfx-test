package za.jfx.model.jfx;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "point_of_presence")
@ToString(exclude = {"networks","sharedResources","employees", "workstations"})
@NamedEntityGraphs({
        @NamedEntityGraph(name = "PointOfPresence.AllFields", includeAllAttributes = true)
})
public class PointOfPresence extends BaseEntity{

    @Column(name = "name", length = 256, unique = true)
    private String name;

    @Column(name = "address", length = 1024)
    private String address;

    @OneToMany(mappedBy = "pointOfPresence", fetch = FetchType.LAZY)
    private List<Network> networks;

    @OneToMany(mappedBy = "pointOfPresence", fetch = FetchType.LAZY)
    private List<SharedResource> sharedResources;

    @OneToMany(mappedBy = "pointOfPresence", fetch = FetchType.LAZY)
    private List<Employee> employees;

    @OneToMany(mappedBy = "pointOfPresence", fetch = FetchType.LAZY)
    private List<Workstation> workstations;

}
