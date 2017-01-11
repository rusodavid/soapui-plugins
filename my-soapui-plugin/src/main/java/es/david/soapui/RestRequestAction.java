package es.david.soapui;

import com.eviware.soapui.SoapUI;
import com.eviware.soapui.impl.rest.RestRequest;
import com.eviware.soapui.model.ModelItem;
import com.eviware.soapui.plugins.ActionConfiguration;
import com.eviware.soapui.support.action.support.AbstractSoapUIAction;
import com.eviware.soapui.support.types.StringToStringsMap;

@ActionConfiguration(actionGroup = ActionGroups.REST_REQUEST_ACTIONS)
public class RestRequestAction extends AbstractSoapUIAction<RestRequest> {

    public RestRequestAction() {
        super("Request Action", "A plugin action at the request level");
    }

	@Override
	public void perform(RestRequest request, Object obj) {
		if (obj != null) {
			SoapUI.log.info(obj.getClass());
			SoapUI.log.info(obj);
		}
		
		ModelItem modelItem = request.getModelItem();
		SoapUI.log.info(modelItem.getName());
		StringToStringsMap map = request.getRequestHeaders();
		for (String key : map.getKeys()) {
			SoapUI.log.info(key + " " + map.get(key, "There is no value defined"));
		}
	}


}
