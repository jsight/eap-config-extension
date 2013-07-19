package org.eapconfig.configextension.extension;

import org.eapconfig.configextension.deployment.EAPConfigExtensionProperties;
import org.jboss.as.controller.AbstractRemoveStepHandler;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.descriptions.ModelDescriptionConstants;
import org.jboss.dmr.ModelNode;
import org.jboss.logging.Logger;

public class EAPConfigExtensionPropertyRemove extends AbstractRemoveStepHandler {

	static final EAPConfigExtensionPropertyRemove INSTANCE = new EAPConfigExtensionPropertyRemove();

	private final Logger log = Logger.getLogger(EAPConfigExtensionPropertyRemove.class);

	private EAPConfigExtensionPropertyRemove() {
	}

	@Override
	protected void performRuntime(OperationContext context,
			ModelNode operation, ModelNode model)
			throws OperationFailedException {
        final String propertyName = PathAddress.pathAddress(operation.require(ModelDescriptionConstants.OP_ADDR)).getLastElement().getValue();
        EAPConfigExtensionProperties.getInstance().remove(propertyName);
	}

	@Override
	protected void recoverServices(OperationContext context,
			ModelNode operation, ModelNode model)
			throws OperationFailedException {
        final String propertyName = PathAddress.pathAddress(operation.require(ModelDescriptionConstants.OP_ADDR)).getLastElement().getValue();
        EAPConfigExtensionProperties.getInstance().setProperty(propertyName, EAPConfigExtensionPropertyDefinition.VALUE.resolveModelAttribute(context, model).asString());
	}

}
