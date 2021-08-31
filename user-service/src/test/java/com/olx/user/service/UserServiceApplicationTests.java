package com.olx.user.service;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserServiceApplicationTests {

	@Autowired
    private MockMvc mvc;
	
	@Test
	void contextLoads() {
	}
	
	@Order(0)
	@Test
	public void registerTest() throws Exception
	{
		String json = "{\"firstName\":\"Ime\",\"lastName\":\"Prezime\",\"email\":\"ime@email.com\",\"password\":\"Password.2\",\"phoneNumber\":\"+38762009954\",\"location\":\"Location\"}";
		mvc.perform(MockMvcRequestBuilders.post("/olx/users/register")
										  .contentType(MediaType.APPLICATION_JSON)
										  .content(json))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.email", is("ime@email.com")));
	}
	
	@Order(0)
	@Test
	public void registerErrorTest() throws Exception
	{
		String json = "{\"firstName\":\"Ime\",\"lastName\":\"Prezime\",\"email\":\"ime2@email.com\",\"password\":\"password\",\"phoneNumber\":\"+38762009954\",\"location\":\"Location\"}";
		mvc.perform(MockMvcRequestBuilders.post("/olx/users/register")
										  .contentType(MediaType.APPLICATION_JSON)
										  .content(json))
				.andExpect(status().isBadRequest());
	}
	
	@Order(1)
	@Test
	public void updateUserTest() throws Exception
	{
		String json = "{\"email\":\"new_mail@email.com\"}";
		mvc.perform(MockMvcRequestBuilders.put("/olx/users/1")
										  .contentType(MediaType.APPLICATION_JSON)
										  .content(json))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.email", is("new_mail@email.com")));
		
		String json2 = "{\"email\":\"ime@email.com\"}";
		mvc.perform(MockMvcRequestBuilders.put("/olx/users/1")
										  .contentType(MediaType.APPLICATION_JSON)
										  .content(json2))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.email", is("ime@email.com")));
	}
	
	@Order(1)
	@Test
	public void updateUserErrorTest() throws Exception
	{
		String json = "{\"email\":\"email\"}";
		mvc.perform(MockMvcRequestBuilders.put("/olx/users/1")
										  .contentType(MediaType.APPLICATION_JSON)
										  .content(json))
				.andExpect(status().isBadRequest());
	}
	
	@Order(2)
	@Test
	public void loginTest() throws Exception
	{
		String json = "{\"email\":\"ime@email.com\",\"password\":\"Password.2\"}";
		mvc.perform(MockMvcRequestBuilders.post("/olx/users/login")
										  .contentType(MediaType.APPLICATION_JSON)
										  .content(json))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.email", is("ime@email.com")));
	}
	
	@Order(2)
	@Test
	public void loginErrorTest() throws Exception
	{
		String json = "{\"email\":\"ime@email.com\",\"password\":\"Password.3\"}";
		mvc.perform(MockMvcRequestBuilders.post("/olx/users/login")
										  .contentType(MediaType.APPLICATION_JSON)
										  .content(json))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getAllUsersTest() throws Exception
	{
		mvc.perform(MockMvcRequestBuilders.get("/olx/users/all"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray());
	}
	
	@Order(1)
	@Test
	public void getUserByIdTest() throws Exception
	{
		mvc.perform(MockMvcRequestBuilders.get("/olx/users/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", is("Ime")));
	}
	
	@Order(1)
	@Test
	public void getUserByIdErrorTest() throws Exception
	{
		mvc.perform(MockMvcRequestBuilders.get("/olx/users/2"))
				.andExpect(status().isBadRequest());
	}
	
	@Order(3)
	@Test
	public void deleteUserByIdTest() throws Exception
	{
		mvc.perform(MockMvcRequestBuilders.delete("/olx/users/1"))
				.andExpect(status().isOk());
	}
	
	@Order(3)
	@Test
	public void deleteUserByIdErrorTest() throws Exception
	{
		mvc.perform(MockMvcRequestBuilders.delete("/olx/users/2"))
				.andExpect(status().isBadRequest());
	}
}
