package academy.biplabks.service;

import academy.biplabks.entity.Employee;
import academy.biplabks.exception.ResourceNotFoundException;
import academy.biplabks.repository.EmployeeRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class EmployeeServiceImplTest {

    @TestConfiguration
    static class EmployeeServiceImplTestConfiguration {
        @Bean
        public EmployeeService getService() {
            return new EmployeeServiceImpl();
        }
    }

    @Autowired  //there will be only one autowired
    private EmployeeService service;

    @MockBean
    private EmployeeRepository repository;

    private List<Employee> employees;
    @Before
    public void setup() {
        Employee emp = new Employee();
        emp.setFirstName("Biplab");
        emp.setLastName("Saha");
        emp.setEmail("biplab.saha@gmail.com");

        employees = Collections.singletonList(emp);

        Mockito.when(repository.findAll())
                .thenReturn(employees);

        Mockito.when(repository.findById(emp.getId()))
                .thenReturn(Optional.of(emp));
    }

    @After
    public void cleanup() {
    }

    @Test
    public void findAll() {
        List<Employee> result = service.findAll();
        Assert.assertEquals("employee list should match", employees , result);
    }

    @Test
    public void findOne() {
        Employee result = service.findOne(employees.get(0).getId());
        Assert.assertEquals("employee should match", employees.get(0) , result);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findOneNotFound() {
        Employee result = service.findOne("sdsdsd");
    }

    @Test
    public void create() {
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }
}