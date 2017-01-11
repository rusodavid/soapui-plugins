package es.david.soapui;

import com.eviware.soapui.SoapUI;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase;
import com.eviware.soapui.model.settings.Settings;
import com.eviware.soapui.plugins.ActionConfiguration;
import com.eviware.soapui.support.action.support.AbstractSoapUIAction;


@ActionConfiguration(actionGroup = ActionGroups.TEST_CASE_ACTIONS)
public class AddAssertion extends AbstractSoapUIAction<WsdlTestCase> {
	
	public AddAssertion() {
		super("Automatic assertions");
	}


	public void perform(WsdlTestCase testCase, Object arg1) {
		//List<TestCase> testCaseList = testSuite.getTestCaseList();
		//for (TestCase testCase : testCaseList) {
			Settings settings = testCase.getSettings();		
			SoapUI.log.error("Type: " + settings.getString("type","No type"));
		//}		
	}

}
