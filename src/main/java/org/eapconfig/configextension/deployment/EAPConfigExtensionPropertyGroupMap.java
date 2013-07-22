package org.eapconfig.configextension.deployment;

import java.util.HashMap;

public class EAPConfigExtensionPropertyGroupMap extends HashMap<String, EAPConfigExtensionProperties> {
	private static final long serialVersionUID = 1L;

	private static final EAPConfigExtensionPropertyGroupMap instance = new EAPConfigExtensionPropertyGroupMap();
	
	public static EAPConfigExtensionPropertyGroupMap getInstance() {
		return instance;
	}
	
	private EAPConfigExtensionPropertyGroupMap() {
		
	}
	
	
}
