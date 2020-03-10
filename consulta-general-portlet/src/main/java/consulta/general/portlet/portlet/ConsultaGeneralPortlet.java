package consulta.general.portlet.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.petra.string.StringPool;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.ProcessAction;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import consulta.general.api.api.ConsultaGeneralApi;
import consulta.general.portlet.constants.ConsultaGeneralPortletKeys;

/**
 * @author lprada
 */
@Component(immediate = true, property = { "com.liferay.portlet.display-category=Consulta",
		"com.liferay.portlet.header-portlet-css=/css/main.css", "com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=ConsultaGeneral", "javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + ConsultaGeneralPortletKeys.CONSULTAGENERAL,
		"javax.portlet.resource-bundle=content.Language", "javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.init-param.add-process-action-success-action=false" }, service = Portlet.class)

public class ConsultaGeneralPortlet extends MVCPortlet {

	Log LOGGER = LogFactoryUtil.getLog(ConsultaGeneralPortlet.class);

	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		String page = ParamUtil.getString(renderRequest, "page", this.viewTemplate);


		JSONArray userList = null;
		try {
			userList = api.consultaGeneral();
		} catch (PortalException e) {
			e.printStackTrace();
		}

		// Hacer campos visibles en view.jsp
		renderRequest.setAttribute("userList", userList);

		this.include(page, renderRequest, renderResponse);
	}

	// Accion o envio de formularios
	@ProcessAction(name = "consultarLista")
	public void consultarLista(ActionRequest actionRequest, ActionResponse actionResponse)
			throws IOException, PortletException {
		LOGGER.info("ID usuario tabla " + ParamUtil.getString(actionRequest, "userID"));

		String userID = ParamUtil.getString(actionRequest, "userID");

		String nombres = StringPool.BLANK;
		String cargo = StringPool.BLANK;

		String descripcion = StringPool.BLANK;
		
		String imagenprofile = StringPool.BLANK;
		
		String estado = StringPool.BLANK;

		String disponibilidad = StringPool.BLANK;

		String correo = StringPool.BLANK;

		String nombreTecnologia = StringPool.BLANK;
		
		String imagenTecnologia = StringPool.BLANK;
		
		String nombreAsignacion = StringPool.BLANK;
		
		String valorAsignacion = StringPool.BLANK;
		
		JSONArray tecnologias = null;
		
		JSONArray asignacion = null;

		JSONArray userProfile = null;
		try {
			userProfile = api.consultarPerfil(userID);

			for (int i = 0; i < userProfile.length(); i++) {

				JSONObject item = userProfile.getJSONObject(i);

				nombres = item.getString("nombres");

				cargo = item.getString("cargo");

				descripcion = item.getString("descripcion");

				estado = item.getString("estado");

				disponibilidad = item.getString("disponibilidad");

				correo = item.getString("correo");
				
				imagenprofile =item.getString("imagen");
				
				tecnologias = item.getJSONArray("tecnologias");

				for (int j = 0; j < tecnologias.length(); j++) {
					nombreTecnologia = tecnologias.getJSONObject(j).getString("nombre");
					imagenTecnologia= tecnologias.getJSONObject(j).getString("imagen");
				}
				
				asignacion = item.getJSONArray("asignacion");
				
				for (int k = 0; k < asignacion.length(); k++) {
					nombreAsignacion = asignacion.getJSONObject(k).getString("nombre");
					valorAsignacion = asignacion.getJSONObject(k).getString("valor");
				}

			}

		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Hacer campos visibles en jsp
		actionRequest.setAttribute("nombres", nombres);
		actionRequest.setAttribute("cargo", cargo);
		actionRequest.setAttribute("descripcion", descripcion);
		actionRequest.setAttribute("imagenprofile", imagenprofile);
		actionRequest.setAttribute("tecnologias", tecnologias);
		actionRequest.setAttribute("asignacion", asignacion);
		actionRequest.setAttribute("nombreTecnologia", nombreTecnologia);
		actionRequest.setAttribute("imagenTecnologia", imagenTecnologia);
		actionRequest.setAttribute("nombreAsignacion", nombreAsignacion);
		actionRequest.setAttribute("valorAsignacion", valorAsignacion);
		

		actionRequest.setAttribute("userProfile", userProfile);
	}

	
	// Accion o envio de formularios
		@ProcessAction(name = "consultarProyecto")
		public void consultarProyecto(ActionRequest actionRequest, ActionResponse actionResponse)
				throws IOException, PortletException {
			LOGGER.info("ID Proyecto " + ParamUtil.getString(actionRequest, "projectID"));

			String projectID = ParamUtil.getString(actionRequest, "projectID");

			
			
			// Hacer campos visibles en jsp
			actionRequest.setAttribute("projectID", projectID);
		}
		
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws IOException, PortletException {
		LOGGER.info("--1 " + ParamUtil.getString(resourceRequest, "texto"));
		LOGGER.info("--2 " + ParamUtil.getString(resourceRequest, "disponibilidad"));
		LOGGER.info("--3 " + ParamUtil.getString(resourceRequest, "tecnologia"));

		String texto = ParamUtil.getString(resourceRequest, "texto");
		String disponibilidad = ParamUtil.getString(resourceRequest, "disponibilidad",StringPool.BLANK);
		String tecnologia = ParamUtil.getString(resourceRequest, "tecnologia");
		
		Map <String, String> parametros=new HashMap<>();
		
		if(texto != StringPool.BLANK) {
			parametros.put("texto", texto);
		}
		if(disponibilidad != StringPool.BLANK) {
			parametros.put("disponibilidad", disponibilidad);
		}
		
		if(tecnologia != StringPool.BLANK) {
			parametros.put("tecnologia", tecnologia);
		}
		
		
		JSONArray userList = null;
		try {
			userList = api.consultaGeneral(parametros);
		} catch (PortalException e) {
			e.printStackTrace();
		}

		writeJSON(resourceRequest, resourceResponse, userList);

	}

	@Reference
	ConsultaGeneralApi api;
}