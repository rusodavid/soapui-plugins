package es.david.soapui;

import java.util.Map;

import com.eviware.soapui.SoapUI;
import com.eviware.soapui.impl.rest.RestRequest;
import com.eviware.soapui.impl.wsdl.WsdlTestSuite;
import com.eviware.soapui.model.ModelItem;
import com.eviware.soapui.model.testsuite.TestProperty;
import com.eviware.soapui.plugins.ActionConfiguration;
import com.eviware.soapui.support.action.support.AbstractSoapUIAction;


@ActionConfiguration(actionGroup = ActionGroups.TEST_SUITE_ACTIONS)
public class TestSuiteAction extends AbstractSoapUIAction<WsdlTestSuite> {

    public TestSuiteAction() {
        super("Test Suite Action", "A plugin action at the test suite level");
    }


	@Override
	public void perform(WsdlTestSuite testSuite, Object obj) {
		if (obj != null) {
			SoapUI.log.info(obj.getClass());
			SoapUI.log.info(obj);
		}
		
		ModelItem modelItem = testSuite.getModelItem();
		SoapUI.log.info(modelItem.getName());
		Map<String, TestProperty> map = testSuite.getProperties();
		TestProperty testProperty = null;
		for (String key : map.keySet()) {
			testProperty = map.get(key);
			SoapUI.log.info(testProperty.getName() + " " + testProperty.getValue());
		}
	}


}
