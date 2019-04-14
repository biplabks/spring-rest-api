package academy.biplabks.controller;

import academy.biplabks.entity.Employee;
import academy.biplabks.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@AutoConfigureMockMvc
@ActiveProfiles("integrationtest") //this is the profile from application-integrationtest.properties
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mvc;  //it will do something similar to postman

    @Autowired
    private EmployeeRepository repository;

    @Before
    public void setup() {
        Employee emp = new Employee();
        emp.setId("biplab-id");
        emp.setFirstName("Biplab");
        emp.setLastName("Saha");
        emp.setEmail("biplab.saha@gmail.com");

        repository.save(emp);

        /*emp = new Employee();
        emp.setId("shanto-id");
        emp.setFirstName("Shanto");
        emp.setLastName("Saha");
        emp.setEmail("shanto.saha@gmail.com");

        repository.save(emp);*/
    }

    @After
    public void cleanup() {
        repository.deleteAll();
    }

    @Test
    public void findAll() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status()
                                                .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email", Matchers.is("biplab.saha@gmail.com")));
    }

    @Test
    public void findOne() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/employees/biplab-id"))
                .andExpect(MockMvcResultMatchers.status()
                                                .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("biplab.saha@gmail.com")));
    }

    @Test
    public void findOne404() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/employees/biplab-idsdsd"))
                .andExpect(MockMvcResultMatchers.status()
                        .isNotFound());
    }

    @Test
    public void create() throws Exception{
        ObjectMapper mapper = new ObjectMapper();

        Employee emp = new Employee();
        emp.setFirstName("Anamika");
        emp.setLastName("Saha");
        emp.setEmail("anamika.saha@gmail.com");

        mvc.perform(MockMvcRequestBuilders.post("/employees")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(mapper.writeValueAsBytes(emp)))
                .andExpect(MockMvcResultMatchers.status()
                                                .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("anamika.saha@gmail.com")));

        mvc.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status()
                                                .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    public void create400() throws Exception{
        ObjectMapper mapper = new ObjectMapper();

        Employee emp = new Employee();
        emp.setFirstName("Anamika");
        emp.setLastName("Saha");
        emp.setEmail("biplab.saha@gmail.com");

        mvc.perform(MockMvcRequestBuilders.post("/employees")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsBytes(emp)))
                            .andExpect(MockMvcResultMatchers.status()
                            .isBadRequest());
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }
}