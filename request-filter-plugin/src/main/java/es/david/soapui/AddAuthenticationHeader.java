package es.david.soapui;

import java.util.List;

import com.eviware.soapui.impl.rest.RestMethod;
import com.eviware.soapui.impl.rest.RestRequest;
import com.eviware.soapui.plugins.ActionConfiguration;
import com.eviware.soapui.support.action.support.AbstractSoapUIAction;
import com.eviware.soapui.support.types.StringToStringsMap;

@ActionConfiguration(actionGroup = ActionGroups.REST_METHOD_ACTIONS)
public class AddAuthenticationHeader extends AbstractSoapUIAction<RestMethod> {
	
	public static final String AUTHORIZATION_HEADER = "Authorization";
	public static final String AUTHORIZATION_HEADER_VALUE = "Bearer ${Login#token}";

	public AddAuthenticationHeader() {
	    super("Add Auth Header", "Add auth header to request");
	}

	public void perform(RestMethod method, Object obj) {
		
		List<RestRequest> requestList = method.getRequestList();
		
		for (RestRequest request : requestList) {
			StringToStringsMap map = request.getRequestHeaders();			
			map.remove(AUTHORIZATION_HEADER);
			map.add(AUTHORIZATION_HEADER, AUTHORIZATION_HEADER_VALUE);
			request.setRequestHeaders(map);
		}
	}
	
}
