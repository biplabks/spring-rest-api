package academy.biplabks.service;

import academy.biplabks.entity.Employee;
import academy.biplabks.exception.BadRequestException;
import academy.biplabks.exception.ResourceNotFoundException;
import academy.biplabks.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    EmployeeRepository repository;

    @Override
    @Transactional
    public List<Employee> findAll() {
        return (List<Employee>) repository.findAll();
    }

    @Override
    @Transactional
    public Employee findOne(String id) {
        return repository.findById(id)
                        .orElseThrow(
                                () -> new ResourceNotFoundException("Employee with id = " + id + " doesn't exist"));
    }

    @Override
    @Transactional
    public Employee create(Employee emp) {
        Optional<Employee> existing = repository.findByEmail(emp.getEmail());
        if (existing.isPresent()) {
            throw new BadRequestException("Employee with email " + emp.getEmail() + " already exists.");
        }

        return repository.save(emp);
    }

    @Override
    @Transactional
    public Employee update(String id, Employee emp) {
        Optional<Employee> existing = repository.findById(id);
        if (!existing.isPresent()) {
            throw new ResourceNotFoundException("Employee with id " + id + " doesn't exist.");
        }

        return repository.save(emp);
    }

    @Override
    @Transactional
    public void delete(String id) {
        Optional<Employee> existing = repository.findById(id);
        if (!existing.isPresent()) {
            throw new ResourceNotFoundException("Employee with id " + id + " doesn't exist.");
        }

        repository.delete(existing.get());
    }
}
