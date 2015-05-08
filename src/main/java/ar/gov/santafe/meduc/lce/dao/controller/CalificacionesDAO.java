package ar.gov.santafe.meduc.lce.dao.controller;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import modelo.organismos.Organismo;
import ar.gov.santafe.meduc.lce.dao.util.HibernateProxyTypeAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Path("/calificacion")
@Stateless
public class CalificacionesDAO {

	@PersistenceContext(unitName = "SigaeEJBPU")
	private EntityManager em;


	@GET
	@Path("/test")
	@Produces(MediaType.APPLICATION_JSON)
	public Response test() {
		Organismo org = new Organismo();
		try {
			org = em.find(Organismo.class, 2501L);
		} catch (Exception e) {
			org.setNomOrganTitulo(" exception [" + e.getMessage() + " ]");
			e.printStackTrace();
		}
		GsonBuilder b = new GsonBuilder();
		b.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
		Gson gsonObj = b.create();
		return Response.ok(gsonObj.toJson(org),MediaType.APPLICATION_JSON).build();
	}
}
