${LICENSE}
package ${restPackageName};

import javax.ws.rs.*;

import org.jboss.resteasy.annotations.Form;

import ${entityPackageName}.*;
import ${restPackageName}.resp.*;

import com.google.inject.Singleton;

${ABOUT}
@Singleton @Produces({"application/json"}) @Path("/r/${lowerEntityName}")
public class ${entityName}Resource {
    @GET @Path("/{id}/detail")
    public ${entityName}Item queryDetail(@PathParam("id") long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @GET @Path("/{id}")
    public ${entityName}Item querySimple(@PathParam("id") long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @POST @Path("/")
    public ${entityName}Item save(@Form(prefix="${lowerEntityName}") ${entityName} ${lowerEntityName}) {
        // TODO Auto-generated method stub
        return null;
    }

    @PUT @Path("/{id}")
    public ${entityName}Item update(@PathParam("id") long id, ${entityName} ${lowerEntityName}) {
        // TODO Auto-generated method stub
        return null;
    }

    @GET @Path("/page")
    public ${entityName}ItemPage queryPage() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @GET @Path("/list")
    public ${entityName}ItemList queryList() {
        // TODO Auto-generated method stub
        return null;
    }
}
