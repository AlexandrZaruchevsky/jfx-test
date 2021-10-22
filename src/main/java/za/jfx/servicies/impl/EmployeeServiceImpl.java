package za.jfx.servicies.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import za.jfx.model.jfx.Employee;
import za.jfx.model.jfx.PointOfPresence;
import za.jfx.repositories.jfx.EmployeeRepository;
import za.jfx.servicies.EmployeeService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee add(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void addAll(List<Employee> employees) {
        employeeRepository.saveAll(employees);
    }

    @Override
    public Employee update(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public Employee findById(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow();
        return employee;
    }

    @Override
    public List<Employee> findAll() {
        return sortedByFio(employeeRepository.findAll());
    }

    @Override
    public List<Employee> findByFio(String... fio) {
        List<Employee> employees;
        switch (fio.length) {
            case 1:
                employees = employeeRepository.findAllByLastNameStartsWith(fio[0]);
                break;
            case 2:
                employees = employeeRepository.findAllByLastNameStartsWithAndFirstNameStartsWith(fio[0], fio[1]);
                break;
            default:
                employees = employeeRepository.findAllByLastNameStartsWithAndFirstNameStartsWithAndMiddleNameStartsWith(fio[0], fio[1], fio[2]);
        }
        return sortedByFio(employees);
    }

    @Override
    public List<Employee> findByPointOfPresence(PointOfPresence pointOfPresence) {
        return sortedByFio(employeeRepository.findByPointOfPresence(pointOfPresence));
    }

    private List<Employee> sortedByFio(List<Employee> employees) {
        return employees.stream()
                .sorted(
                        Comparator.comparing(Employee::getLastName)
                                .thenComparing(Employee::getFirstName)
                                .thenComparing(Employee::getMiddleName))
                .collect(Collectors.toList());

    }

}
