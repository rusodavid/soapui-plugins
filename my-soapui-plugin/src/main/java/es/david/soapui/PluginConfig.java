package es.david.soapui;

import com.eviware.soapui.plugins.PluginAdapter;
import com.eviware.soapui.plugins.PluginConfiguration;

@PluginConfiguration(groupId = "es.david.soapui.plugins", name = "my-soapui-plugin SoapUI Action", version = "0.1",
        autoDetect = true, description = "my-soapui-plugin SoapUI Action",
        infoUrl = "" )
public class PluginConfig extends PluginAdapter {
}
