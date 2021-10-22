package za.jfx.model.jfx;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "workstations")
@NamedEntityGraphs({
        @NamedEntityGraph(name = "Workstation.AllFields", includeAllAttributes = true),
        @NamedEntityGraph(name = "Workstation.PointOfPresenceField", attributeNodes = {
                @NamedAttributeNode("pointOfPresence")
        })
})
@ToString(exclude = {"pointOfPresence"})
public class Workstation extends BaseEntity {

    @Column(name = "ip_address", length = 32)
    private String ipAddress;
    @Column(name = "host_name", length = 256)
    private String hostName;
    @Column(name = "host_full_name", length = 256)
    private String hostFullName;
    @Column(name = "description", length = 1024)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_of_presence_id")
    private PointOfPresence pointOfPresence;

}
