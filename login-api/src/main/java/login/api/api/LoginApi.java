package login.api.api;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.IOException;

/**
 * @author lprada
 */
public interface LoginApi {
	
	public User login(Long companyId,long[] roleIds,long[] organizationIds,ServiceContext serviceContext, String user, String password) throws IOException, PortalException;
}