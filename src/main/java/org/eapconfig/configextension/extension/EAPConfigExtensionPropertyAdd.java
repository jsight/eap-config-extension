package org.eapconfig.configextension.extension;

import java.util.List;

import org.eapconfig.configextension.deployment.EAPConfigExtensionProperties;
import org.jboss.as.controller.AbstractAddStepHandler;
import org.jboss.as.controller.AbstractBoottimeAddStepHandler;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.ServiceVerificationHandler;
import org.jboss.as.controller.descriptions.ModelDescriptionConstants;
import org.jboss.dmr.ModelNode;
import org.jboss.logging.Logger;
import org.jboss.msc.service.ServiceController;

public class EAPConfigExtensionPropertyAdd extends
		AbstractAddStepHandler {

	static final EAPConfigExtensionPropertyAdd INSTANCE = new EAPConfigExtensionPropertyAdd();

	private final Logger log = Logger
			.getLogger(EAPConfigExtensionPropertyAdd.class);

	private EAPConfigExtensionPropertyAdd() {
	}

	/** {@inheritDoc} */
	@Override
	protected void populateModel(ModelNode operation, ModelNode model)
			throws OperationFailedException {
		EAPConfigExtensionPropertyDefinition.VALUE.validateAndSet(operation,
				model);;
	}

	@Override
	protected void performRuntime(OperationContext context,
			ModelNode operation, ModelNode model,
			ServiceVerificationHandler verificationHandler,
			List<ServiceController<?>> newControllers)
			throws OperationFailedException {
		final String propertyName = PathAddress
				.pathAddress(
						operation.require(ModelDescriptionConstants.OP_ADDR))
				.getLastElement().getValue();
		EAPConfigExtensionProperties.getInstance().setProperty(
				propertyName,
				EAPConfigExtensionPropertyDefinition.VALUE
						.resolveModelAttribute(context, model).asString());
	}

	@Override
	protected void rollbackRuntime(OperationContext context,
			ModelNode operation, ModelNode model,
			List<ServiceController<?>> controllers) {
		final String propertyName = PathAddress.pathAddress(operation.require(ModelDescriptionConstants.OP_ADDR)).getLastElement().getValue();
		EAPConfigExtensionProperties.getInstance().remove(propertyName);
	}

}
