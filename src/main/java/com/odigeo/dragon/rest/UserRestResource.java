package com.odigeo.dragon.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.odigeo.dragon.entity.User;
import com.odigeo.dragon.repository.UserRepository;

@Path("/users")
@Component
public class UserRestResource {
	
	@Autowired
	UserRepository userRepository;
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
		System.out.println("getUsers is called");
        Iterable<User> users = userRepository.findAll();
		return Response.status(200).entity(users).build();
    }

	@GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getUser(@PathParam("id") Long id) {
		System.out.println("getUser is called, id : " + id);
		User user = userRepository.findOne(id);
		return Response.status(200).entity(user).build();
    }
	
	@POST
	@Consumes({"application/json"})
    public Response createUser(User user) {
		System.out.println("createuser is called. " + user);
		userRepository.save(user);
		return Response.status(200).entity("Usuario creado: " + user).build();

    }
	
	@PUT
	@Consumes({"application/json"})
    public Response updateUser(User user) {
		System.out.println("updateUser is called. " + user);
		
		if(user.getId()==null){
			return Response.status(400).entity("id no informado").build();
		}
		
		if(!userRepository.exists(user.getId())){
			return Response.status(201).entity("Usuario no encontrado").build();
		}
		userRepository.save(user);
		return Response.status(200).entity("Usuario modificado:" + user).build();

    }
	
	@DELETE
	@Path("/{id}")
    public Response deleteUser(@PathParam("id") Long id) {
		System.out.println("deleteUser called. Id: " + id);
		userRepository.delete(id);
		return Response.status(200).entity("Usuario eliminado id:" + id).build();

    }
	
}
