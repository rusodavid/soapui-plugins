package es.david.soapui;

import java.util.List;

import com.eviware.soapui.SoapUI;
import com.eviware.soapui.impl.wsdl.WsdlTestSuite;
import com.eviware.soapui.model.settings.Settings;
import com.eviware.soapui.model.testsuite.TestCase;
import com.eviware.soapui.plugins.ActionConfiguration;
import com.eviware.soapui.support.action.support.AbstractSoapUIAction;


@ActionConfiguration(actionGroup = ActionGroups.TEST_SUITE_ACTIONS)
public class AddAssertion extends AbstractSoapUIAction<WsdlTestSuite> {
	
	public AddAssertion() {
		super("Automatic assertions");
	}


	public void perform(WsdlTestSuite testSuite, Object obj) {
		
		List<TestCase> testCaseList = testSuite.getTestCaseList();
		for (TestCase testCase : testCaseList) {
			Settings settings = testCase.getSettings();		
			SoapUI.log.error("Type: " + settings.getString("type","No type"));
		}
	}

}
