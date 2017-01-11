package es.david.soapui;

import java.util.List;

import com.eviware.soapui.impl.rest.RestMethod;
import com.eviware.soapui.impl.rest.RestRequest;
import com.eviware.soapui.plugins.ActionConfiguration;
import com.eviware.soapui.support.action.support.AbstractSoapUIAction;
import com.eviware.soapui.support.types.StringToStringsMap;

	@ActionConfiguration(actionGroup = ActionGroups.REST_METHOD_ACTIONS)
	public class AddHceAuthenticationHeader extends AbstractSoapUIAction<RestMethod> {
		
		public static final String AUTHORIZATION_HCE_HEADER = "jwt_authentication";
		public static final String AUTHORIZATION_HCE_HEADER_VALUE = "Bearer ${HCELogin#jwtToken}";

		public AddHceAuthenticationHeader() {
		    super("Add HCE Auth Header", "Add auth header to request");
		}

		public void perform(RestMethod method, Object obj) {
			
			List<RestRequest> requestList = method.getRequestList();
			
			for (RestRequest request : requestList) {
				StringToStringsMap map = request.getRequestHeaders();			
				map.remove(AUTHORIZATION_HCE_HEADER);
				map.add(AUTHORIZATION_HCE_HEADER, AUTHORIZATION_HCE_HEADER_VALUE);
				request.setRequestHeaders(map);
			}
		}
		
	}
