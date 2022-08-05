package ro.btrl.dpuscau.technicalchallenge.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CheckEnrollmentTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenDocumentIsExpired_shouldGetReason() throws Exception {
        System.out.println(new File(".").getCanonicalPath());
        this.mockMvc.perform(MockMvcRequestBuilders.post("/enrollment/check").
                        content(Files.readAllBytes(Paths.get("./src/test/resources/documentExpired.json"))).
                        contentType("application/json").
                        characterEncoding(Charset.forName("UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Document is expired")))
                .andExpect(content().string(containsString("isEligible\":false")));
    }

    @Test
    public void whenClientIsRisky_shouldGetReason() throws Exception {
        System.out.println(Paths.get("../"));
        this.mockMvc.perform(MockMvcRequestBuilders.post("/enrollment/check").contentType("application/json").characterEncoding(Charset.forName("UTF-8"))
                        .content(Files.readAllBytes(Paths.get("./src/test/resources/riskyPerson.json"))))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("High risk client")))
                .andExpect(content().string(containsString("isEligible\":false")));
    }

    @Test
    public void whenClientAlreadyExists_shouldGetReason() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/enrollment/check").
                        content(Files.readAllBytes(Paths.get("./src/test/resources/existingClient.json"))).
                        contentType("application/json").
                        characterEncoding(Charset.forName("UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Client already exists")))
                .andExpect(content().string(containsString("isEligible\":false")));
    }

    @Test
    public void whenEligibleClient_shouldGetEligible() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/enrollment/check").
                        content(Files.readAllBytes(Paths.get("./src/test/resources/eligibleEnrollment.json"))).
                        contentType("application/json").
                        characterEncoding(Charset.forName("UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("isEligible\":true")))
                .andExpect(content().string(not(containsString("reason"))));
    }
}
