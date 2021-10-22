package za.jfx.servicies;

import za.jfx.model.jfx.Employee;
import za.jfx.model.jfx.PointOfPresence;

import java.util.List;

public interface EmployeeService extends CrudService<Employee> {

    List<Employee> findByFio(String... fio);
    List<Employee> findByPointOfPresence(PointOfPresence pointOfPresence);

}
