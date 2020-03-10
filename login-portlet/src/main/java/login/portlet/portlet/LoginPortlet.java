package login.portlet.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.auth.session.AuthenticatedSessionManagerUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.ProcessAction;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import login.api.api.LoginApi;
import login.portlet.constants.LoginPortletKeys;

/**
 * @author lprada
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=Login",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=Login-Portlet",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + LoginPortletKeys.LOGIN,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user" }, service = Portlet.class)
public class LoginPortlet extends MVCPortlet {
	Log LOGGER = LogFactoryUtil.getLog(LoginPortlet.class);

	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		String page = ParamUtil.getString(renderRequest, "page", this.viewTemplate);
		this.include(page, renderRequest, renderResponse);
	}

	// Accion o envio de formularios
	@ProcessAction(name = "login")
	public void login(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException {
	
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

		long[] roleIds = { ParamUtil.getLong(actionRequest, "roleId") };
		long[] organizationIds = new long[] { ParamUtil.getLong(actionRequest, "organizationId") };

		ServiceContext serviceContext = null;
		try {
			serviceContext = ServiceContextFactory.getInstance(User.class.getName(), actionRequest);
			User liferayUser = api.login(PortalUtil.getCompanyId(actionRequest), roleIds, organizationIds,
					serviceContext, ParamUtil.getString(actionRequest, "username"),
					ParamUtil.getString(actionRequest, "password"));
			AuthenticatedSessionManagerUtil.login(PortalUtil.getHttpServletRequest(actionRequest),
					PortalUtil.getHttpServletResponse(actionResponse), liferayUser.getLogin(),
					ParamUtil.getString(actionRequest, "password"), false, CompanyConstants.AUTH_TYPE_EA);

			actionResponse.sendRedirect(themeDisplay.getPathFriendlyURLPrivateGroup());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Reference
	LoginApi api;
}