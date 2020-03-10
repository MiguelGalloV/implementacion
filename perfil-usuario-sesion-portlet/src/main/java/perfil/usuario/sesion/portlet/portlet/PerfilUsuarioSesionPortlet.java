package perfil.usuario.sesion.portlet.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.io.IOException;
import java.io.Serializable;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

import perfil.usuario.sesion.portlet.constants.PerfilUsuarioSesionPortletKeys;

/**
 * @author lprada
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=Perfil sesion",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=PerfilUsuarioSesion",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + PerfilUsuarioSesionPortletKeys.PERFILUSUARIOSESION,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.init-param.add-process-action-success-action=false"
	},
	service = Portlet.class
)
public class PerfilUsuarioSesionPortlet extends MVCPortlet {
	Log LOGGER= LogFactoryUtil.getLog(PerfilUsuarioSesionPortlet.class);
	
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException{
		String page = ParamUtil.getString(renderRequest, "page", this.viewTemplate);

		
		//Declaracipon de variables
		String  userId = renderRequest.getRemoteUser();
		
		String nombres = "";
	    String cargo = "";
	    String descripcion = "";
	    String imagenprofile="";
		
		User liferayUser = null;
		JSONObject response = null;
		
		//Obtención de ID de usuario
		try {
			 liferayUser = UserLocalServiceUtil.getUserById(Long.parseLong(userId)); 
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Obtención de campo personalizado "user data" el cual almacena String obtenido en /usuario/perfil
		Serializable user = liferayUser.getExpandoBridge().getAttribute("user data");
		
		//Conversión de serializable a String
		String userToString = user.toString();
		
		//Navegación de Json de campo personalizado "user data"
		try {
			response = JSONFactoryUtil.createJSONObject(userToString);
			JSONArray values = response.getJSONArray("payload");
			
			for (int i = 0; i < values.length(); i++) {
			    
				JSONObject item = values.getJSONObject(i); 
			    
			    nombres = item.getString("nombres");
			    cargo = item.getString("cargo");
			    descripcion = item.getString("descripcion");
			    imagenprofile =item.getString("imagen");
 
			  }
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Hacer campos visibles en view.jsp
	    renderRequest.setAttribute("nombres", nombres);
	    renderRequest.setAttribute("cargo", cargo);
	    renderRequest.setAttribute("descripcion", descripcion);
	    renderRequest.setAttribute("imagenprofile", imagenprofile);
	    
	    
	    this.include(page, renderRequest, renderResponse);
	
	}
}