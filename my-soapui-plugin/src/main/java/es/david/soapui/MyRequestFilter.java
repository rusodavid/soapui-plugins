package es.david.soapui;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import com.eviware.soapui.SoapUI;
import com.eviware.soapui.impl.rest.RestRequestInterface;
import com.eviware.soapui.impl.support.AbstractHttpRequestInterface;
import com.eviware.soapui.impl.wsdl.submit.filters.AbstractRequestFilter;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCaseRunner;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestRunContext;
import com.eviware.soapui.model.ModelItem;
import com.eviware.soapui.model.iface.SubmitContext;
import com.eviware.soapui.model.project.Project;
import com.eviware.soapui.model.testsuite.TestCase;
import com.eviware.soapui.model.testsuite.TestCaseRunContext;
import com.eviware.soapui.model.testsuite.TestStep;
import com.eviware.soapui.model.testsuite.TestStepResult;
import com.eviware.soapui.model.testsuite.TestSuite;
import com.eviware.soapui.model.workspace.Workspace;
import com.eviware.soapui.plugins.auto.PluginRequestFilter;
import com.eviware.soapui.support.action.swing.ActionList;
import com.eviware.soapui.support.types.StringToStringsMap;

@PluginRequestFilter(protocol = "http")
public class MyRequestFilter extends AbstractRequestFilter {
	@Override
	public void afterAbstractHttpResponse(SubmitContext context, AbstractHttpRequestInterface<?> request) {
//		HttpResponse response = (HttpResponse) context.getProperty(BaseHttpRequestTransport.RESPONSE);
//		response.setProperty("Secret Message", "bu!");
	}


	@Override
	public void filterRestRequest(SubmitContext context, RestRequestInterface request) {
		super.filterRestRequest(context, request);
	}
	
	public void filterRestRequest2(SubmitContext context, RestRequestInterface request) {
		
		String path = request.getPath();
		SoapUI.log.info("Path: " + path);
		
		if (path.contains("polizas")) {
			String[] pathParams = request.getPath().split("/");
			String decodedIdPoliza = null;
			boolean foundPolizas = false;
			for (String param : pathParams) {
				SoapUI.log.debug("Param: " + param);
				if (foundPolizas == true) {
					try {
						decodedIdPoliza = URLDecoder.decode(param, "UTF-8");
						SoapUI.log.debug("decodedIdPoliza: " + decodedIdPoliza);
					} catch (UnsupportedEncodingException e) {
						SoapUI.log.error("Error decoding param: " + decodedIdPoliza);
					}
					path.replace(param,decodedIdPoliza);
					break;
				}
				
				if ("polizas".equals(param)) {
					foundPolizas = true;
				}
			}
		}
		
		//request.setPath(path);
		SoapUI.log.info("Decode Path: " + path);

//		SoapUI.log.info("BindAddress: " + request.getBindAddress());
//		SoapUI.log.info("Endpoint: " + request.getEndpoint());
		
		

		//		StringToObjectMap map = context.getProperties();
		//		for (String key : map.keySet()) {
		//			SoapUI.log.debug("key: " + key);
		//		}


		ModelItem parent = context.getModelItem();
		SoapUI.log.debug("context parent name " + parent.getName());
		ModelItem grandParent = parent.getParent();
		SoapUI.log.debug("context grandParent name " + grandParent.getName());
		ModelItem grandGrandParent = grandParent.getParent();
		SoapUI.log.debug("context grandGrandParent name " + grandGrandParent.getName());

		ModelItem contextParent = request.getParent();
		SoapUI.log.debug("contextParent: " + contextParent.getName());		
//		contextParent = contextParent.getParent();
//		SoapUI.log.debug("contextParent: " + contextParent.getName());
//		contextParent = contextParent.getParent();
//		SoapUI.log.debug("contextParent: " + contextParent.getName());

		if (!"auth".equals(contextParent.getName())) {
			SoapUI.log.debug("Mete cabecera de autenticacion");
			boolean hasAuthenticationHeader = hasAuthenticationHeader(request);
			
			if (!hasAuthenticationHeader) {
				String token = runAuth(context);				
				request = addAuthenticationHeader(request, token);
			}
		}
		
		super.filterRestRequest(context, request);
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	private boolean hasAuthenticationHeader(RestRequestInterface request) {
		boolean ret = false;
		StringToStringsMap map = request.getRequestHeaders();
		SoapUI.log.debug("Headers:");
		for (String key : map.getKeys()) {
			SoapUI.log.debug(key);
		}
		SoapUI.log.debug("End headers");

		List<String> authenticationHeaderList = map.get("Authorization");
		if ((authenticationHeaderList != null) && (authenticationHeaderList.size() > 0)) {
			ret = true;
		}
		
		return ret;
	}


	private String runAuth(SubmitContext context) {
		
		Workspace workspace = SoapUI.getWorkspace();
		Project project = workspace.getProjectByName("REST PortalClientes");
		TestSuite authenticationTestSuite = project.getTestSuiteByName("WEB Authentication");
		TestCase authenticationTestCase = authenticationTestSuite.getTestCaseByName("runAuth");
		TestStep autheticationTestStep = authenticationTestCase.getTestStepByName("auth");

		String token = null;
		
		if (context instanceof TestCaseRunContext) {
			TestCaseRunContext testCaseContext = (TestCaseRunContext) context;
			TestStepResult result = autheticationTestStep.run(
					new WsdlTestCaseRunner((WsdlTestCase) authenticationTestCase, null), new WsdlTestRunContext(autheticationTestStep));

			
			SoapUI.log.info("Name: " + result.getTestStep().getName());
			SoapUI.log.info("Status: " + result.getStatus());

			for (String message : result.getMessages()) {
				SoapUI.log.info("Message: " + message);				
			}

			ActionList action = result.getActions();
			SoapUI.log.info("Action Label: " + action.getLabel());
			SoapUI.log.info("Size: " + result.getSize());	
		}

		token = authenticationTestSuite.getPropertyValue("token");
		SoapUI.log.info("Token: " + token);

		return token;
	}

	private RestRequestInterface addAuthenticationHeader(RestRequestInterface request, String token) {

		StringToStringsMap map = request.getRequestHeaders();
		SoapUI.log.debug("Headers:");
		for (String key : map.getKeys()) {
			SoapUI.log.debug(key);
		}
		SoapUI.log.debug("End headers");

		map.add("Authorization", "Bearer " + token);
		request.setRequestHeaders(map);

		return request;

	}
}
