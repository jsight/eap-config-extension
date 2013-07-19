package org.eapconfig.configextension.extension;

import org.eapconfig.configextension.deployment.EAPConfigExtensionProperties;
import org.jboss.as.controller.AbstractWriteAttributeHandler;
import org.jboss.as.controller.AttributeDefinition;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.descriptions.ModelDescriptionConstants;
import org.jboss.dmr.ModelNode;

public class EAPConfigExtensionPropertyWrite extends AbstractWriteAttributeHandler<Void> {

    private static final EAPConfigExtensionProperties config = EAPConfigExtensionProperties.getInstance();

    public EAPConfigExtensionPropertyWrite(AttributeDefinition attribute) {
        super(attribute);
    }

    @Override
    protected boolean applyUpdateToRuntime(OperationContext context, ModelNode operation, String attributeName, ModelNode resolvedValue, ModelNode currentValue, HandbackHolder handbackHolder) throws OperationFailedException {
        final String propName = PathAddress.pathAddress(operation.require(ModelDescriptionConstants.OP_ADDR)).getLastElement().getValue();
        String propValue = resolvedValue.asString();
        applyUpdateToConfig(propName, propValue);
        return false;
    }

    @Override
    protected void revertUpdateToRuntime(OperationContext context, ModelNode operation, String attributeName, ModelNode valueToRestore, ModelNode valueToRevert, Void handback) throws OperationFailedException {
        final String propName = PathAddress.pathAddress(operation.require(ModelDescriptionConstants.OP_ADDR)).getLastElement().getValue();
        String propValue = valueToRestore.asString();
        applyUpdateToConfig(propName, propValue);
    }

    public static void applyUpdateToConfig(String propName, String propValue) {
        config.setProperty(propName, propValue);
    }	
}
