package org.eapconfig.configextension.extension;

import java.util.List;

import org.eapconfig.configextension.deployment.EAPConfigExtensionProperties;
import org.jboss.as.controller.AbstractBoottimeAddStepHandler;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.ServiceVerificationHandler;
import org.jboss.as.controller.descriptions.ModelDescriptionConstants;
import org.jboss.dmr.ModelNode;
import org.jboss.logging.Logger;
import org.jboss.msc.service.ServiceController;

/**
 * Handler responsible for adding the subsystem resource to the model
 */
class EAPConfigExtensionSubsystemAdd extends AbstractBoottimeAddStepHandler {

    static final EAPConfigExtensionSubsystemAdd INSTANCE = new EAPConfigExtensionSubsystemAdd();

    private final Logger log = Logger.getLogger(EAPConfigExtensionSubsystemAdd.class);

    private EAPConfigExtensionSubsystemAdd() {
    }

    /** {@inheritDoc} */
    @Override
    protected void populateModel(ModelNode operation, ModelNode model) throws OperationFailedException {
    }

    /** {@inheritDoc} */
    @Override
    public void performBoottime(OperationContext context, ModelNode operation, ModelNode model,
            ServiceVerificationHandler verificationHandler, List<ServiceController<?>> newControllers)
            throws OperationFailedException {
    }
    
    @Override
    protected void rollbackRuntime(OperationContext context,
    		ModelNode operation, ModelNode model,
    		List<ServiceController<?>> controllers) {
    }
}
