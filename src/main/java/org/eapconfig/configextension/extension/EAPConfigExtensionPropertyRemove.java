package org.eapconfig.configextension.extension;

import org.eapconfig.configextension.deployment.EAPConfigExtensionProperties;
import org.eapconfig.configextension.deployment.EAPConfigExtensionPropertyGroupMap;
import org.jboss.as.controller.AbstractRemoveStepHandler;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.descriptions.ModelDescriptionConstants;
import org.jboss.dmr.ModelNode;

public class EAPConfigExtensionPropertyRemove extends AbstractRemoveStepHandler {

	static final EAPConfigExtensionPropertyRemove INSTANCE = new EAPConfigExtensionPropertyRemove();

	private EAPConfigExtensionPropertyRemove() {
	}

	@Override
	protected void performRuntime(OperationContext context,
			ModelNode operation, ModelNode model)
			throws OperationFailedException {
	    PathAddress opAddr = PathAddress.pathAddress(operation.require(ModelDescriptionConstants.OP_ADDR));
        final String propertyGroupName = opAddr.getElement(opAddr.size() - 2).getValue();
        final String propertyName = opAddr.getLastElement().getValue();
        
        EAPConfigExtensionProperties props = EAPConfigExtensionPropertyGroupMap.getInstance().get(propertyGroupName);
        props.remove(propertyName);
	}

	@Override
	protected void recoverServices(OperationContext context,
			ModelNode operation, ModelNode model)
			throws OperationFailedException {
	    PathAddress opAddr = PathAddress.pathAddress(operation.require(ModelDescriptionConstants.OP_ADDR));
        final String propertyGroupName = opAddr.getElement(opAddr.size() - 2).getValue();
        final String propertyName = opAddr.getLastElement().getValue();
        
        EAPConfigExtensionProperties props = EAPConfigExtensionPropertyGroupMap.getInstance().get(propertyGroupName);
        props.setProperty(propertyName, EAPConfigExtensionPropertyDefinition.VALUE.resolveModelAttribute(context, model).asString());
	}

}
