package za.jfx.model.jfx;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "network")
@ToString(exclude = {"pointOfPresence"})
@NoArgsConstructor
@NamedEntityGraphs({
        @NamedEntityGraph(name = "Network.AllFields", includeAllAttributes = true)
})
public class Network extends BaseEntity{

    private String subnet;

    private String ipAdress;
    private String hostName;
    private String hostFullName;
    private LocalDateTime datePoll;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_of_presence_id")
    private PointOfPresence pointOfPresence;

}
