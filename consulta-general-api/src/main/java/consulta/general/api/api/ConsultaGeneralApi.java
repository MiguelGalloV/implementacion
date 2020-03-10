package consulta.general.api.api;

import java.io.IOException;
import java.util.Map;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;

/**
 * @author lprada
 */
public interface ConsultaGeneralApi {
	
	public JSONArray consultaGeneral() throws IOException, PortalException;
	
	public JSONArray consultaGeneral(Map <String, String> parametros) throws IOException, PortalException;
	
	public JSONArray consultarPerfil(String userID) throws IOException, PortalException;
	
}