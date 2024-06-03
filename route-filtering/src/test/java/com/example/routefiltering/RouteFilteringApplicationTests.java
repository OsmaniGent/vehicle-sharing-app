package com.example.routefiltering;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.routefiltering.daos.RouteDAO;
import com.example.routefiltering.models.Route;
import com.example.routefiltering.models.SegmentCostDTO;
import com.example.routefiltering.repositories.RouteRepository;
import com.example.routefiltering.services.CostAccumulationService;
import com.example.routefiltering.services.RouteService;
import com.example.routefiltering.utils.RouteExpirationTask;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class RouteFilteringApplicationTests {

	@Mock
	private RouteDAO routeDAO;

	@InjectMocks
	private RouteService routeService;

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private RouteExpirationTask routeExpirationTask;

	@InjectMocks
	private CostAccumulationService costService;

	@BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

	@Test
	public void testAddRoute() throws Exception {
		String newRouteJson = "{\"id\":\"route1\", \"start\":\"13.388860,52.517037\", \"end\":\"13.397634,52.529407\"}";
		mockMvc.perform(post("/filter/add")
				.contentType(MediaType.APPLICATION_JSON)
				.content(newRouteJson))
				.andExpect(status().isCreated());
	}

	@Test
	public void testFindRoutesNearEndPoint() throws Exception {
		mockMvc.perform(get("/filter/near")
				.param("longitude", "13.388860")
				.param("latitude", "52.517037"))
				.andExpect(status().isOk());
	}

	@Test
	public void testFindRoutesNear() {
		double longitude = 13.388860;
		double latitude = 52.517037;
		Route mockRoute = new Route();
		mockRoute.setId("route1");
		when(routeDAO.findRoutesNear(longitude, latitude)).thenReturn(Collections.singletonList(mockRoute));

		List<Route> routes = routeService.findRoutesNear(longitude, latitude);
		assertEquals(1, routes.size());
		assertEquals("route1", routes.get(0).getId());
	}

	@Test
	public void testCheckForExpiredRoutes() {
		Route route1 = new Route();
		route1.setId("route1");
		route1.setExpiry(new Date(System.currentTimeMillis() - 1000));
		route1.setIsOpen(true);
		
		List<Route> expiredRoutes = Arrays.asList(route1);
		when(routeDAO.findExpiredRoutes(new Date())).thenReturn(expiredRoutes);

		routeExpirationTask.checkForExpiredRoutes();

		verify(routeDAO).saveRoute(route1);
	}

	@Test
	public void testAccumulateAndReturnCosts() {
		SegmentCostDTO segmentCost = new SegmentCostDTO();
		segmentCost.setCost(100.0);
		segmentCost.setIdentifiers(Arrays.asList("passenger1", "passenger2"));

		Map<String, Double> expectedCosts = new HashMap<>();
		expectedCosts.put("passenger1", 33.333333333333336);
		expectedCosts.put("passenger2", 33.333333333333336);

		Map<String, Double> actualCosts = costService.accumulateAndReturnCosts(segmentCost);
		assertEquals(expectedCosts, actualCosts);
	}

	@Test
	public void testCalculateIndividualCosts() throws Exception {
		String segmentCostJson = "{\"cost\": 100.0, \"identifiers\": [\"passenger1\", \"passenger2\"]}";
		mockMvc.perform(post("/segment/cost")
				.contentType(MediaType.APPLICATION_JSON)
				.content(segmentCostJson))
				.andExpect(status().isOk());
	}

}
