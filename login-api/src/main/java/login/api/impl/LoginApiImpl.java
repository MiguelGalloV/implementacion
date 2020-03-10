package login.api.impl;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import login.api.api.LoginApi;
import utils.api.api.UtilsApi;

@Component(service = LoginApi.class)
public class LoginApiImpl implements LoginApi {

	private static Log LOGGER = LogFactoryUtil.getLog(LoginApiImpl.class);

	@Override
	public User login(Long companyId, long[] roleIds, long[] organizationIds, ServiceContext serviceContext,
			String user, String password) throws IOException, PortalException {

		// Crear objeto Json vacio
		JSONObject request = JSONFactoryUtil.createJSONObject();
		request.put("username", user);
		request.put("password", password);

		// Objeto Json enviado a metodo Login
		JSONObject response = JSONFactoryUtil.createJSONObject(
				api.servicePostMethod("http://50.116.18.134:3000/squad/auth/login", request.toJSONString()));

		// Objeto Json decodifica token obtenido en respuesta de login
		JSONObject decodedToken = JSONFactoryUtil
				.createJSONObject(api.decodeJwtMethod(response.getJSONObject("payload").getString("token")));

		LOGGER.info("DECODED TOKEN " + decodedToken);

		Map<String, String> parametros = new HashMap<>();
		parametros.put("id", decodedToken.getString("user"));

		// Objeto Json usado en consulta /usuario/perfil
		JSONObject userDataResponse = JSONFactoryUtil
				.createJSONObject(api.serviceGetMethod("http://50.116.18.134:3000/squad/usuario/perfil", parametros));
		// + "?id=" + decodedToken.getString("user")));


		// Creacion de usuario en Liferay Portal
		User liferayUser = UserLocalServiceUtil.fetchUserByEmailAddress(companyId, user);

		if (liferayUser == null) {
			long[] groupIds = {};
			long[] userGroupIds = {};
			String firstName = user.split("@")[0];
			String lastName = "stefanini";
			String middleName = "";
			String screenName = user.split("@")[0];
			liferayUser = UserLocalServiceUtil.addUser(0, companyId, false, password, password, false, screenName, user,
					0L, StringPool.BLANK, LocaleUtil.getDefault(), firstName, StringPool.BLANK, lastName, 0, 0, true,
					Calendar.JANUARY, 1, 1979, StringPool.BLANK, groupIds, new long[] {}, new long[] {}, new long[] {},
					false, new ServiceContext());
		}

		addUserRoles(liferayUser, userDataResponse.getJSONArray("payload").getJSONObject(0).getJSONArray("roles"));

		// Uso de Api Expando para insertar datos de usuario en campo personalizado
		liferayUser.getExpandoBridge().setAttribute("user data", userDataResponse.toJSONString());

		return liferayUser;
	}

	public void addUserRoles(User liferayUser, JSONArray roles) {
		for (int i = 0; i < roles.length(); i++) {

			String item = roles.getString(i);
			try {
				Role siteMember = RoleLocalServiceUtil.getRole(liferayUser.getCompanyId(), item);

				LOGGER.info(siteMember.getPrimaryKey());
				LOGGER.info(siteMember.getRoleId());

				UserLocalServiceUtil.addRoleUser(siteMember.getRoleId(), liferayUser.getUserId());
			} catch (PortalException e) {
				LOGGER.error(e);
			}

		}

		UserLocalServiceUtil.updateUser(liferayUser);

	}

	@Reference
	UtilsApi api;

}
