package ar.gov.santafe.meduc.lce.dao.util;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@ApplicationScoped
public class ProductorUtil {

	@Produces
	public Gson getGsonParser(){ 
	GsonBuilder b = new GsonBuilder();
	b.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
	Gson gsonObj = b.create();
	return gsonObj;
	}
}
