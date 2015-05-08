package ar.gov.santafe.meduc.lce.dao.controller;

import java.util.List;



import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;



import com.google.gson.Gson;

@Path("/crud")
@Stateless
public class CrudServiceRest implements CrudService {

	@PersistenceContext(unitName = "SigaeEJBPU")
	private EntityManager em;
	
	@Inject
	private Gson gsonParser ;

	@Override
	@POST
	public <T> Response create(T t) {
		em.persist(t);
		return Response.ok(gsonParser.toJson(t),MediaType.APPLICATION_JSON).build();
	}	
	
	
	@Override
	@PUT
	public <T> Response update(T t) {
		return Response.ok(gsonParser.toJson(this.em.merge(t)),MediaType.APPLICATION_JSON).build();
	}

	@Override
	@DELETE
	@Path("{entityName}/{id}")
	public Response delete(@PathParam("entityName") String className, @PathParam("id") Long id) {
		Class type=null;
		try {
			type = Class.forName(className).getClass();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Object ref = this.em.getReference(type, id);
		this.em.remove(ref);
		return Response.ok().build();
	}

	@Override
	@GET
	@Path("{entityName}/{id}")
	public <T> Response find(@PathParam("entityName") String className, @PathParam("id") Long id) {
		T t = null;
		try {
			t = (T)this.em.find(Class.forName(className), id);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("TYPE: "+className+" ID "+id);
		return Response.ok(gsonParser.toJson(t),MediaType.APPLICATION_JSON).build();
		
	}

	@Override
	@GET
	@Path("{package}/{entityName}/{id}")
	public <T> Response findPackage(@PathParam("package") String pkgName, @PathParam("entityName") String className, @PathParam("id") Long id) {
		T t = null;
		try {
			t = (T)this.em.find(Class.forName(pkgName+"."+className), id);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("TYPE: "+className+" ID "+id);
		return Response.ok(gsonParser.toJson(t),MediaType.APPLICATION_JSON).build();
		
	}
	

	@Override
	@GET
	@Path("{entityName}")
	public Response findAll(@PathParam("entityName") String entityName) {		
		return Response.ok(gsonParser.toJson(em.createQuery("from " + entityName).getResultList()),MediaType.APPLICATION_JSON).build();
	}

	
	
}
