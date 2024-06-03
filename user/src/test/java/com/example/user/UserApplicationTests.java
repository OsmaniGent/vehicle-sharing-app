package com.example.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.example.user.daos.UserDAO;
import com.example.user.models.PendingRoute;
import com.example.user.daos.PendingRouteDAO;
import com.example.user.models.User;
import com.example.user.repositories.PendingRouteRepository;
import com.example.user.repositories.UserRepository;
import com.example.user.services.PendingRouteService;
import com.example.user.services.UserService;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
class UserApplicationTests {

	@Mock
	private UserDAO userDAO;

	@InjectMocks
	private UserService userService;

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private PendingRouteDAO PendingRouteDAO;

	@InjectMocks
	private PendingRouteService pendingRouteService;

	@Test
	public void testAuthenticateUser() {
		String username = "testuser";
		String password = "hashedpassword";

		User mockUser = new User();
		mockUser.setUsername(username);
		mockUser.setHashedPassword(password);

		when(userDAO.findByUsername(username)).thenReturn(mockUser);

		assertTrue(userService.authenticateUser(username, password).isPresent());
		assertFalse(userService.authenticateUser(username, "wrongpassword").isPresent());
	}

	@Test
	public void testAddUser() throws Exception {
		String newUserJson = "{\"username\":\"testuser2\",\"hashedPassword\":\"hashedpassword\"}";
		mockMvc.perform(post("/user/addUser")
				.contentType(MediaType.APPLICATION_JSON)
				.content(newUserJson))
				.andExpect(status().isCreated());
	}

	@Test
	public void testLoginUser() throws Exception {
		String loginJson = "{\"username\":\"testuser2\",\"hashedPassword\":\"hashedpassword\"}";
		mockMvc.perform(post("/user/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(loginJson))
				.andExpect(status().isOk());
	}

	@Test
	public void testFindRoute() {
		String routeId = "route1";
		PendingRoute mockRoute = new PendingRoute();
		mockRoute.setId(routeId);

		when(PendingRouteDAO.findById(routeId)).thenReturn(Optional.of(mockRoute));

		Optional<PendingRoute> route = pendingRouteService.findRoute(routeId);
		assertEquals(routeId, route.get().getId());
	}

	@Test
	public void testAddReview() throws Exception {
		String reviewJson = "{\"reviewedUserId\": \"user1\", \"reviewerId\": \"user2\", \"rating\": 5, \"comment\": \"Great ride!\"}";
		mockMvc.perform(post("/user/addReview")
				.contentType(MediaType.APPLICATION_JSON)
				.content(reviewJson))
				.andExpect(status().isOk());
	}
	

}
