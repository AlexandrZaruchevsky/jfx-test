package za.jfx.repositories.jfx;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.PagingAndSortingRepository;
import za.jfx.model.jfx.Employee;
import za.jfx.model.jfx.Equipment;

import java.util.List;
import java.util.Optional;

public interface EquipmentRepository extends PagingAndSortingRepository<Equipment, Long> {

    @EntityGraph("Equipment.AllFields")
    Optional<Equipment> findById(Long id);
    List<Equipment> findAll();
    List<Equipment> findAllByEmployee(Employee employee);
//    List<Equipment> findAllByNetwork(boolean isNetwork);


}
