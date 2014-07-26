package pl.codeleak.demo.optional_as_requestparam;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.codeleak.demo.Application;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class SampleSomeControllerTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void bindsNonNullLocalDateAsRequestParam() throws Exception {
        mockMvc.perform(get("/o/r").param("ld", "2020-01-01"))
                .andExpect(content().string("ld: 2020-01-01"));
    }

    @Test
    public void bindsNoLocalDateAsRequestParam() throws Exception {
        mockMvc.perform(get("/o/r"))
                .andExpect(content().string("ld: "));
    }

    @Test
    public void bindsNonNullCustomHeader() throws Exception {
        mockMvc.perform(get("/o/h").header("Custom-Header", "My Custom Header Value"))
                .andExpect(content().string("Custom-Header: My Custom Header Value"));
    }

    @Test
    public void noCustomHeaderGiven() throws Exception {
        mockMvc.perform(get("/o/h").header("Custom-Header", ""))
                .andExpect(content().string("Custom-Header: "));
    }

    @Test
    public void bindsNonNullMatrixVariables() throws Exception {
        mockMvc.perform(get("/o/m/42;p=4;q=2"))
                .andExpect(content().string("p: 4, q: 2"));
    }

    @Test
    public void bindsNullMatrixVariables() throws Exception {
        mockMvc.perform(get("/o/m/42;p=;q="))
                .andExpect(content().string("p: , q: "));
    }
}