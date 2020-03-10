package utils.api.api;

import java.io.IOException;
import java.util.Map;

/**
 * @author lprada
 */

public interface UtilsApi {
	
	public String serviceGetMethod(String url, Map <String, String> parametros) throws IOException;
	
	public String servicePostMethod(String url, String body) throws IOException;
	
	public String servicePutMethod(String url, String userID, String body) throws IOException;
	
	public String decodeJwtMethod(String token) throws IOException;
}