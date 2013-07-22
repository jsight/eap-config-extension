package org.eapconfig.configextension.extension;

import java.util.List;

import org.eapconfig.configextension.deployment.EAPConfigExtensionProperties;
import org.eapconfig.configextension.deployment.EAPConfigExtensionPropertyGroupMap;
import org.jboss.as.controller.AbstractAddStepHandler;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.ServiceVerificationHandler;
import org.jboss.as.controller.descriptions.ModelDescriptionConstants;
import org.jboss.dmr.ModelNode;
import org.jboss.msc.service.ServiceController;

public class EAPConfigExtensionPropertyGroupAdd  extends AbstractAddStepHandler {
	static final EAPConfigExtensionPropertyGroupAdd INSTANCE = new EAPConfigExtensionPropertyGroupAdd();
	
	private EAPConfigExtensionPropertyGroupAdd() {
	}

	@Override
	protected void populateModel(ModelNode operation, ModelNode model) throws OperationFailedException {
	}
	
	@Override
	protected void performRuntime(OperationContext context, ModelNode operation, ModelNode model, ServiceVerificationHandler verificationHandler,
	        List<ServiceController<?>> newControllers) throws OperationFailedException {
	    PathAddress opAddr = PathAddress.pathAddress(operation.require(ModelDescriptionConstants.OP_ADDR));
        final String propertyGroupName = opAddr.getLastElement().getValue();
        
        EAPConfigExtensionProperties props = EAPConfigExtensionPropertyGroupMap.getInstance().get(propertyGroupName);
        if (props == null) {
            props = new EAPConfigExtensionProperties();
            EAPConfigExtensionPropertyGroupMap.getInstance().put(propertyGroupName, props);
        }
	}
	
	@Override
	protected void rollbackRuntime(OperationContext context, ModelNode operation, ModelNode model, List<ServiceController<?>> controllers) {
	    PathAddress opAddr = PathAddress.pathAddress(operation.require(ModelDescriptionConstants.OP_ADDR));
        final String propertyGroupName = opAddr.getLastElement().getValue();
        
        EAPConfigExtensionPropertyGroupMap.getInstance().remove(propertyGroupName);
	}
}
