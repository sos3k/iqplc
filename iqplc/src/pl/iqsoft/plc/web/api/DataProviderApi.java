package pl.iqsoft.plc.web.api;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("/data")
public class DataProviderApi {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Float> getDataValues() {
		List<Float> results = new ArrayList<>();
		results.add(13.44f);
		
		return results;
	}
}
