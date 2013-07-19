package org.eapconfig.configextension.extension;

import org.jboss.as.controller.AbstractRemoveStepHandler;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.dmr.ModelNode;
import org.jboss.logging.Logger;

/**
 * Handler responsible for removing the subsystem resource from the model
 *
 */
class EAPConfigExtensionSubsystemRemove extends AbstractRemoveStepHandler {

    static final EAPConfigExtensionSubsystemRemove INSTANCE = new EAPConfigExtensionSubsystemRemove();

    private final Logger log = Logger.getLogger(EAPConfigExtensionSubsystemRemove.class);

    private EAPConfigExtensionSubsystemRemove() {
    }

    @Override
    protected void performRuntime(OperationContext context, ModelNode operation, ModelNode model) throws OperationFailedException {
    }

    @Override
    protected void recoverServices(OperationContext context, ModelNode operation, ModelNode model) throws OperationFailedException {
    }

}
