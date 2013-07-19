package org.eapconfig.configextension.extension;

import org.jboss.as.controller.SimpleAttributeDefinition;
import org.jboss.as.controller.SimpleAttributeDefinitionBuilder;
import org.jboss.as.controller.SimpleResourceDefinition;
import org.jboss.as.controller.descriptions.ModelDescriptionConstants;
import org.jboss.as.controller.registry.ManagementResourceRegistration;
import org.jboss.dmr.ModelType;

public class EAPConfigExtensionPropertyDefinition extends SimpleResourceDefinition {
    public static final EAPConfigExtensionPropertyDefinition  INSTANCE = new EAPConfigExtensionPropertyDefinition ();

    static final SimpleAttributeDefinition VALUE = new SimpleAttributeDefinitionBuilder(ModelDescriptionConstants.VALUE, ModelType.STRING, false)
    	.setAllowExpression(true)
    	.build();
    
    private EAPConfigExtensionPropertyDefinition() {
        super(EAPConfigExtensionSubsystemExtension.PROPERTY_PATH,
                EAPConfigExtensionSubsystemExtension.getResourceDescriptionResolver(null),
                //We always need to add an 'add' operation
                EAPConfigExtensionPropertyAdd.INSTANCE,
                //Every resource that is added, normally needs a remove operation
                EAPConfigExtensionPropertyRemove.INSTANCE);
    }

    @Override
    public void registerAttributes(ManagementResourceRegistration resourceRegistration) {
        super.registerAttributes(resourceRegistration);
        resourceRegistration.registerReadWriteAttribute(VALUE, null, new EAPConfigExtensionPropertyWrite(VALUE));
    }
}
