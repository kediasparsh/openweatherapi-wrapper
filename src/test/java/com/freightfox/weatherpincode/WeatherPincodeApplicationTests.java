package com.freightfox.weatherpincode;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class WeatherPincodeApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void contextLoads() {
	}

	@Test
	public void getWeatherTest() throws Exception {

		RequestBuilder request = MockMvcRequestBuilders.get("/weather").param("pincode", "395007").param("for_date", "2024-02-05");
		mockMvc.perform(request)
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.pincode").value(395007))
			.andExpect(jsonPath("$.date").value("2024-02-05"))
			.andExpect(jsonPath("$.temperature").isNumber())
			.andExpect(jsonPath("$.pressure").isNumber())
			.andExpect(jsonPath("$.humidity").isNumber())
			.andExpect(jsonPath("$.description").isString());
	}

}
