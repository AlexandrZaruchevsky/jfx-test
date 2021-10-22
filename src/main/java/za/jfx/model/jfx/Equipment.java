package za.jfx.model.jfx;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(
        name = "equipments"
)
@NamedEntityGraphs({
        @NamedEntityGraph(name = "Equipment.AllFields", includeAllAttributes = true)
})
@ToString(exclude = {"employee"})
public class Equipment extends BaseEntity {

    @Column(name="name", length = 128)
    private String name;
    @Column(name="full_name", length = 255)
    private String fullName;
    @Column(name="manufacturer", length = 255)
    private String manufacturer;
    @Column(name="country_of_manufacturer", length = 128)
    private String countryOfManufacture;
    @Column(name="date_of_manufacturer")
    private LocalDate dateOfManufacture;
    @Column(name="inventory_number", length = 64)
    private String inventoryNumber;
    @Column(name="serial_number", length = 64)
    private String serialNumber;
    @Column(name="id_network")
    private boolean isNetwork;

    @Column(name="host_name", length = 128)
    private String hostName;
    @Column(name="host_full_name", length = 255)
    private String hostFullName;
    @Column(name="ip_address", length = 64)
    private String ipAddress;

    @Column(name = "description", length = 1024)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

}
