package org.eapconfig.configextension.extension;

import org.eapconfig.configextension.deployment.EAPConfigExtensionProperties;
import org.eapconfig.configextension.deployment.EAPConfigExtensionPropertyGroupMap;
import org.jboss.as.controller.AbstractWriteAttributeHandler;
import org.jboss.as.controller.AttributeDefinition;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.descriptions.ModelDescriptionConstants;
import org.jboss.dmr.ModelNode;

public class EAPConfigExtensionPropertyWrite extends AbstractWriteAttributeHandler<Void> {

    public EAPConfigExtensionPropertyWrite(AttributeDefinition attribute) {
        super(attribute);
    }

    @Override
    protected boolean applyUpdateToRuntime(OperationContext context, ModelNode operation, String attributeName, ModelNode resolvedValue, ModelNode currentValue, HandbackHolder handbackHolder) throws OperationFailedException {
        PathAddress opAddr = PathAddress.pathAddress(operation.require(ModelDescriptionConstants.OP_ADDR));
        final String propertyGroupName = opAddr.getElement(opAddr.size() - 2).getValue();
        final String propertyName = opAddr.getLastElement().getValue();
        final String propertyValue = resolvedValue.asString();
        applyUpdateToConfig(propertyGroupName, propertyName, propertyValue);
        return false;
    }

    @Override
    protected void revertUpdateToRuntime(OperationContext context, ModelNode operation, String attributeName, ModelNode valueToRestore, ModelNode valueToRevert, Void handback) throws OperationFailedException {
        PathAddress opAddr = PathAddress.pathAddress(operation.require(ModelDescriptionConstants.OP_ADDR));
        final String propertyGroupName = opAddr.getElement(opAddr.size() - 2).getValue();
        final String propName = PathAddress.pathAddress(operation.require(ModelDescriptionConstants.OP_ADDR)).getLastElement().getValue();
        String propValue = valueToRestore.asString();
        applyUpdateToConfig(propertyGroupName, propName, propValue);
    }

    public static void applyUpdateToConfig(String propertyGroup, String propName, String propValue) {
        EAPConfigExtensionProperties properties = EAPConfigExtensionPropertyGroupMap.getInstance().get(propertyGroup);
        properties.setProperty(propName, propValue);
    }
}
