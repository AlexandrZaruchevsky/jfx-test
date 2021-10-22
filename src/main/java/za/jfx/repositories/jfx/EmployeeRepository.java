package za.jfx.repositories.jfx;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.PagingAndSortingRepository;
import za.jfx.model.jfx.Employee;
import za.jfx.model.jfx.PointOfPresence;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {

//     @EntityGraph("Employee.AllFields")
     List<Employee> findAll();

     @EntityGraph("Employee.AllFields")
     Optional<Employee> findById(Long id);


//     @EntityGraph("Employee.AllFields")
     List<Employee> findAllByLastNameStartsWith(String lastName);
//     @EntityGraph("Employee.AllFields")
     List<Employee> findAllByLastNameStartsWithAndFirstNameStartsWith(String lastName, String firstName);
//     @EntityGraph("Employee.AllFields")
     List<Employee> findAllByLastNameStartsWithAndFirstNameStartsWithAndMiddleNameStartsWith(String lastName, String firstName, String middleName);
//     @EntityGraph("Employee.AllFields")
     List<Employee> findByPointOfPresence(PointOfPresence pointOfPresence);



}
