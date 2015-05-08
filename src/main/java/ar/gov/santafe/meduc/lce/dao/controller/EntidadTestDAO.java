package ar.gov.santafe.meduc.lce.dao.controller;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import modelo.administracion.TestDaoRest;

import com.google.gson.Gson;


@Path("/test")
@Stateless
public class EntidadTestDAO {
	
	@Inject
	private Gson gsonObj ;
	
	@PersistenceContext(unitName = "SigaeEJBPU")
	private EntityManager em;
	
	@GET
	public Response listarEntidades(){
		List<TestDaoRest> testEntities = em.createQuery("SELECT tdr FROM TestDaoRest tdr").getResultList();
		GenericEntity<List<TestDaoRest>> list = new GenericEntity<List<TestDaoRest>>(testEntities) {};
		return Response.ok(list).build();
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarEntidad(@PathParam("id") Integer id){
		TestDaoRest testEntity = em.find(TestDaoRest.class, id);
		if(testEntity == null) testEntity = new TestDaoRest();
		return Response.ok(gsonObj.toJson(testEntity),MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response crearEntidad(TestDaoRest obj){
		System.out.println("= = = = CREAR ENTIDAD "+obj.toString());
		em.persist(obj);
		return Response.ok().build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response actualizarEntidad(TestDaoRest obj){
		TestDaoRest test=obj;
		if(obj.getId()>0){
			test = em.merge(obj);
		}
		return Response.ok(gsonObj.toJson(test),MediaType.APPLICATION_JSON).build();
	}

	@DELETE
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response borrarEntidad(@PathParam("id") Integer id){
		TestDaoRest testEntity = em.find(TestDaoRest.class, id);
		em.remove(testEntity);
		return Response.ok("OK",MediaType.TEXT_PLAIN).build();
	}

}
