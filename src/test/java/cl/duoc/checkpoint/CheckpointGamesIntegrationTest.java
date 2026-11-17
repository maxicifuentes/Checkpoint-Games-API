package cl.duoc.checkpoint;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest @AutoConfigureMockMvc @ActiveProfiles("test")
class CheckpointGamesIntegrationTest {
 @Autowired MockMvc mvc; @Autowired ObjectMapper mapper;
 @Test void publicProductsAndAdminSecurityWork() throws Exception { mvc.perform(get("/api/v1/productos")).andExpect(status().isOk()).andExpect(jsonPath("$[0].codigo").value("NEBULA")); mvc.perform(post("/api/v1/productos").contentType(MediaType.APPLICATION_JSON).content("{}" )).andExpect(status().isForbidden()); }
 @Test void loginReturnsJwtAndCustomerCanCreateOrder() throws Exception { String response=mvc.perform(post("/api/v1/auth/login").contentType(MediaType.APPLICATION_JSON).content("{\"correo\":\"cliente@gmail.com\",\"clave\":\"user1\"}" )).andExpect(status().isOk()).andReturn().getResponse().getContentAsString(); JsonNode json=mapper.readTree(response); String token=json.get("token").asText(); mvc.perform(post("/api/v1/boletas").header("Authorization","Bearer "+token).contentType(MediaType.APPLICATION_JSON).content("{\"items\":[{\"productoId\":1,\"cantidad\":1}]}" )).andExpect(status().isCreated()).andExpect(jsonPath("$.total").value(39990)); }
}
