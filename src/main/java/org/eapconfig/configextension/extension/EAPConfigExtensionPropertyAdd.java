package org.eapconfig.configextension.extension;

import java.util.List;

import org.eapconfig.configextension.deployment.EAPConfigExtensionPropertyGroupMap;
import org.jboss.as.controller.AbstractAddStepHandler;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.ServiceVerificationHandler;
import org.jboss.as.controller.descriptions.ModelDescriptionConstants;
import org.jboss.dmr.ModelNode;
import org.jboss.msc.service.ServiceController;

public class EAPConfigExtensionPropertyAdd extends AbstractAddStepHandler {

    static final EAPConfigExtensionPropertyAdd INSTANCE = new EAPConfigExtensionPropertyAdd();

    private EAPConfigExtensionPropertyAdd() {
    }

    /** {@inheritDoc} */
    @Override
    protected void populateModel(ModelNode operation, ModelNode model) throws OperationFailedException {
        EAPConfigExtensionPropertyDefinition.VALUE.validateAndSet(operation, model);
    }

    @Override
    protected void performRuntime(OperationContext context, ModelNode operation, ModelNode model, ServiceVerificationHandler verificationHandler,
            List<ServiceController<?>> newControllers) throws OperationFailedException {
        PathAddress opAddr = PathAddress.pathAddress(operation.require(ModelDescriptionConstants.OP_ADDR));
        final String propertyGroupName = opAddr.getElement(opAddr.size() - 2).getValue();
        final String propertyName = opAddr.getLastElement().getValue();
        
        final String propertyValue = EAPConfigExtensionPropertyDefinition.VALUE.resolveModelAttribute(context, model).asString();
        EAPConfigExtensionPropertyGroupMap.getInstance().get(propertyGroupName).setProperty(propertyName, propertyValue);
    }

    @Override
    protected void rollbackRuntime(OperationContext context, ModelNode operation, ModelNode model, List<ServiceController<?>> controllers) {
        PathAddress opAddr = PathAddress.pathAddress(operation.require(ModelDescriptionConstants.OP_ADDR));
        final String propertyGroupName = opAddr.getElement(opAddr.size() - 2).getValue();
        final String propertyName = opAddr.getLastElement().getValue();
        
        EAPConfigExtensionPropertyGroupMap.getInstance().get(propertyGroupName).remove(propertyName);
    }

}
