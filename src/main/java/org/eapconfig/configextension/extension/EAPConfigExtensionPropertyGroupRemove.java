package org.eapconfig.configextension.extension;

import org.eapconfig.configextension.deployment.EAPConfigExtensionPropertyGroupMap;
import org.jboss.as.controller.AbstractRemoveStepHandler;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.descriptions.ModelDescriptionConstants;
import org.jboss.dmr.ModelNode;

public class EAPConfigExtensionPropertyGroupRemove extends AbstractRemoveStepHandler {
	static final EAPConfigExtensionPropertyGroupRemove INSTANCE = new EAPConfigExtensionPropertyGroupRemove();
	
	private EAPConfigExtensionPropertyGroupRemove() {
	}
		
	@Override
	protected void performRuntime(OperationContext context, ModelNode operation, ModelNode model) throws OperationFailedException {
	    PathAddress opAddr = PathAddress.pathAddress(operation.require(ModelDescriptionConstants.OP_ADDR));
        final String propertyGroupName = opAddr.getLastElement().getValue();
        
        EAPConfigExtensionPropertyGroupMap.getInstance().remove(propertyGroupName);
	}
}
