package za.jfx.model.jfx;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "shared_resource")
@ToString(exclude = {"pointOfPresence"})
public class SharedResource extends BaseEntity{

    @Column(name ="resource_name" ,length = 256)
    private String resourceName;
    @Column(name ="resource_path" ,length = 256)
    private String resourcePath;
    @Column(name ="server_name" ,length = 256)
    private String serverName;
    @Column(name ="server_ip" ,length = 256)
    private String serverIp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_of_presence_id")
    private PointOfPresence pointOfPresence;

}
