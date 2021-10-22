package za.jfx.model.jfx;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(
        name = "employees",
        indexes = {
                @Index(name = "idx_snils", columnList = "snils"),
                @Index(name = "idx_fio", columnList = "last_name,first_name,middle_name")
        }
)
@NamedEntityGraphs({
        @NamedEntityGraph(name = "Employee.AllFields", includeAllAttributes = true),
        @NamedEntityGraph(name = "Employee.FieldPointOfPresence", includeAllAttributes = true),
        @NamedEntityGraph(name = "Employee.FieldEquipment", includeAllAttributes = true)
})
@ToString(exclude = {"pointOfPresence","equipments"})
public class Employee extends BaseEntity {

    @Column(name = "snils", length = 14)
    private String snils;

    @Column(name = "last_name", length = 128)
    private String lastName;

    @Column(name = "first_name", length = 128)
    private String firstName;

    @Column(name = "middle_name", length = 128)
    private String middleName;

    @Column(name = "department", length = 1028)
    private String department;

    @Column(name = "position", length = 1028)
    private String position;

    @Column(name = "email", length = 256)
    private String email;

    @Column(name = "pnone", length = 32)
    private String phone;

    @Column(name = "pnone_kspd", length = 32)
    private String phoneKspd;

    @Column(name = "username", length = 64)
    private String username;

    @Column(name = "workstation", length = 256)
    private String workstation;

    @Column(name = "ip")
    private String ip;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_pf_presence_id")
    private PointOfPresence pointOfPresence;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Equipment> equipments;

    public String getFullFio() {
        return (lastName != null && lastName.trim().length() > 0 ? lastName + " " : "") +
                (firstName != null && firstName.trim().length() > 0 ? firstName + " " : "") +
                (middleName != null && middleName.trim().length() > 0 ? middleName : "");
    }

    public String getResource() {
        return (ip != null && ip.trim().length() > 0 ? ip.trim() : "") +
                (workstation != null && workstation.trim().length() > 0 ? " [" + workstation.trim() + "]" : "");
    }

    public String getForListView(){
        return getFullFio() +" : " +
                (position!=null?position:"None");
    }

}