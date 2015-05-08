package ar.gov.santafe.meduc.lce.dao.controller;


import javax.ejb.Local;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
@Local
public interface CrudService{

	public <T> Response  create(T t);
	public <T> Response find(@PathParam("entityName") String className, @PathParam("id") Long id) ;
	public <T> Response findPackage(@PathParam("package") String pkgName, @PathParam("entityName") String className, @PathParam("id") Long id) ;
	public <T> Response  update(T t);
	public Response delete(String className, Long id); 
	public Response findAll(@PathParam("entityName") String entityName);
	
}
