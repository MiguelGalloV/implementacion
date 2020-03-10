package consulta.general.api.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import consulta.general.api.api.ConsultaGeneralApi;
import utils.api.api.UtilsApi;



@Component(service=ConsultaGeneralApi.class)
public class ConsultaGeneralApiImpl implements ConsultaGeneralApi{
	
	Log LOGGER= LogFactoryUtil.getLog(ConsultaGeneralApiImpl.class);
	

    
	@Override
	public JSONArray consultaGeneral()
			throws IOException, JSONException {
		
		Map <String, String> parametros=new HashMap<>();

		JSONObject response = JSONFactoryUtil.createJSONObject(api.serviceGetMethod("http://50.116.18.134:3000/squad/usuario/lista", parametros));
		
		JSONArray values = response.getJSONArray("payload");
		
		return values;
	}
	
	@Override
	public JSONArray consultarPerfil(String userID) throws IOException, PortalException {
		
		Map <String, String> parametros=new HashMap<>();
		parametros.put("id", userID);
		
		JSONObject userDataResponse = JSONFactoryUtil.createJSONObject(api.serviceGetMethod("http://50.116.18.134:3000/squad/usuario/perfil", parametros));
		
		JSONArray userData = userDataResponse.getJSONArray("payload");
		
		return userData;
	}
	
	@Override
	public JSONArray consultaGeneral(Map<String, String> parametros) throws IOException, PortalException {
		JSONObject response = JSONFactoryUtil.createJSONObject(api.serviceGetMethod("http://50.116.18.134:3000/squad/usuario/lista", parametros));
		
		JSONArray values = response.getJSONArray("payload");
		
		return values;
	}


	@Reference
	UtilsApi api;
	
}
