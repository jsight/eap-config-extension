package org.eapconfig.configextension.extension;

import org.jboss.as.controller.SimpleAttributeDefinition;
import org.jboss.as.controller.SimpleAttributeDefinitionBuilder;
import org.jboss.as.controller.SimpleResourceDefinition;
import org.jboss.as.controller.descriptions.ModelDescriptionConstants;
import org.jboss.as.controller.registry.ManagementResourceRegistration;
import org.jboss.dmr.ModelType;

public class EAPConfigExtensionPropertyGroupDefinition extends SimpleResourceDefinition {
	public static EAPConfigExtensionPropertyGroupDefinition INSTANCE = new EAPConfigExtensionPropertyGroupDefinition();

    private EAPConfigExtensionPropertyGroupDefinition() {
        super(EAPConfigExtensionSubsystemExtension.PROPERTY_GROUP_PATH,
                EAPConfigExtensionSubsystemExtension.getResourceDescriptionResolver(null),
                //We always need to add an 'add' operation
                EAPConfigExtensionPropertyGroupAdd.INSTANCE,
                //Every resource that is added, normally needs a remove operation
                EAPConfigExtensionPropertyGroupRemove.INSTANCE);
    }
    
    @Override
    public void registerChildren(
    		ManagementResourceRegistration resourceRegistration) {
    	super.registerChildren(resourceRegistration);
    	resourceRegistration.registerSubModel(EAPConfigExtensionPropertyDefinition.INSTANCE);
    }
}
