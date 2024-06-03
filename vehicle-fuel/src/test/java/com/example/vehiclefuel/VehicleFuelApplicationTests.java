package com.example.vehiclefuel;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
class VehicleFuelApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGetVehicleMpg() throws Exception {
		mockMvc.perform(get("/vehicles/mpg")
				.param("year", "2020")
				.param("make", "Toyota")
				.param("model", "Camry"))
				.andExpect(status().isOk());
	}

}
