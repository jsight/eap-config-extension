package org.eapconfig.configextension.extension;

import org.jboss.as.controller.SimpleResourceDefinition;
import org.jboss.as.controller.registry.ManagementResourceRegistration;

/**
 */
public class EAPConfigExtensionSubsystemDefinition extends SimpleResourceDefinition {
    public static final EAPConfigExtensionSubsystemDefinition INSTANCE = new EAPConfigExtensionSubsystemDefinition();

    private EAPConfigExtensionSubsystemDefinition() {
        super(EAPConfigExtensionSubsystemExtension.SUBSYSTEM_PATH,
                EAPConfigExtensionSubsystemExtension.getResourceDescriptionResolver(null),
                //We always need to add an 'add' operation
                EAPConfigExtensionSubsystemAdd.INSTANCE,
                //Every resource that is added, normally needs a remove operation
                EAPConfigExtensionSubsystemRemove.INSTANCE);
    }
    
    @Override
    public void registerChildren(
    		ManagementResourceRegistration resourceRegistration) {
    	resourceRegistration.registerSubModel(EAPConfigExtensionPropertyGroupDefinition.INSTANCE);
    }
}
