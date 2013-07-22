package org.eapconfig.configextension.extension;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.SUBSYSTEM;
import static org.jboss.as.controller.parsing.ParseUtils.missingRequired;
import static org.jboss.as.controller.parsing.ParseUtils.requireNoAttributes;
import static org.jboss.as.controller.parsing.ParseUtils.requireNoContent;
import static org.jboss.as.controller.parsing.ParseUtils.requireNoNamespaceAttribute;
import static org.jboss.as.controller.parsing.ParseUtils.unexpectedAttribute;
import static org.jboss.as.controller.parsing.ParseUtils.unexpectedElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;

import org.jboss.as.controller.Extension;
import org.jboss.as.controller.ExtensionContext;
import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.PathElement;
import org.jboss.as.controller.SubsystemRegistration;
import org.jboss.as.controller.descriptions.StandardResourceDescriptionResolver;
import org.jboss.as.controller.operations.common.GenericSubsystemDescribeHandler;
import org.jboss.as.controller.operations.common.Util;
import org.jboss.as.controller.parsing.Attribute;
import org.jboss.as.controller.parsing.Element;
import org.jboss.as.controller.parsing.ExtensionParsingContext;
import org.jboss.as.controller.persistence.SubsystemMarshallingContext;
import org.jboss.as.controller.registry.ManagementResourceRegistration;
import org.jboss.dmr.ModelNode;
import org.jboss.staxmapper.XMLElementReader;
import org.jboss.staxmapper.XMLElementWriter;
import org.jboss.staxmapper.XMLExtendedStreamReader;
import org.jboss.staxmapper.XMLExtendedStreamWriter;

/**
 */
public class EAPConfigExtensionSubsystemExtension implements Extension {

	/**
	 * The name space used for the {@code substystem} element
	 */
	public static final String NAMESPACE = "urn:eapconfig:eapconfigextension:1.0";

	/**
	 * The name of our subsystem within the model.
	 */
	public static final String SUBSYSTEM_NAME = "eapconfigextension";

	public static final String PROPERTY_GROUP_NAME = "propertyGroup";
	public static final String PROPERTY_NAME = "property";
	
	/**
	 * The parser used for parsing our subsystem
	 */
	private final SubsystemParser parser = new SubsystemParser();

	protected static final PathElement SUBSYSTEM_PATH = PathElement.pathElement(SUBSYSTEM, SUBSYSTEM_NAME);
	protected static final PathElement PROPERTY_PATH = PathElement.pathElement(PROPERTY_NAME);
	protected static final PathElement PROPERTY_GROUP_PATH = PathElement.pathElement(PROPERTY_GROUP_NAME);
	private static final String RESOURCE_NAME = EAPConfigExtensionSubsystemExtension.class.getPackage().getName() + ".LocalDescriptions";

	static StandardResourceDescriptionResolver getResourceDescriptionResolver(final String keyPrefix) {
		String prefix = SUBSYSTEM_NAME + (keyPrefix == null ? "" : "." + keyPrefix);
		return new StandardResourceDescriptionResolver(prefix, RESOURCE_NAME, EAPConfigExtensionSubsystemExtension.class.getClassLoader(),true, false);
	}

	@Override
	public void initializeParsers(ExtensionParsingContext context) {
		context.setSubsystemXmlMapping(SUBSYSTEM_NAME, NAMESPACE, parser);
	}

	@Override
	public void initialize(ExtensionContext context) {
		final SubsystemRegistration subsystem = context.registerSubsystem(SUBSYSTEM_NAME, 1, 0);
		final ManagementResourceRegistration registration = subsystem.registerSubsystemModel(EAPConfigExtensionSubsystemDefinition.INSTANCE);
		registration.registerOperationHandler(GenericSubsystemDescribeHandler.DEFINITION, GenericSubsystemDescribeHandler.INSTANCE);
		subsystem.registerXMLElementWriter(parser);
	}

	/**
	 * The subsystem parser, which uses stax to read and write to and from xml
	 */
	private static class SubsystemParser implements XMLStreamConstants,
			XMLElementReader<List<ModelNode>>,
			XMLElementWriter<SubsystemMarshallingContext> {

		private static final String ELEMENT_PROPERTY_GROUP = "propertyGroup";
		private static final String ELEMENT_PROPERTIES = "properties";
		private static final String ELEMENT_PROPERTY = "property";
		private static final String ATTRIBUTE_NAME = "name";
		private static final String ATTRIBUTE_VALUE = "value";

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void writeContent(XMLExtendedStreamWriter writer,
				SubsystemMarshallingContext context) throws XMLStreamException {
			context.startSubsystemElement(
					EAPConfigExtensionSubsystemExtension.NAMESPACE, false);

			ModelNode node = context.getModelNode();
			ModelNode properties = node.get(PROPERTY_NAME);
			if (properties.isDefined()) {
				writer.writeStartElement(ELEMENT_PROPERTIES);
				for (String key : properties.keys()) {
					writer.writeStartElement(ELEMENT_PROPERTY);
					writer.writeAttribute(ATTRIBUTE_NAME, key);
					EAPConfigExtensionPropertyDefinition.VALUE.marshallAsAttribute(properties.get(key), writer);
					writer.writeEndElement();
				}
				writer.writeEndElement();
			}
	        writer.writeEndElement();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void readElement(XMLExtendedStreamReader reader,
				List<ModelNode> operations) throws XMLStreamException {
			final PathAddress address = PathAddress
					.pathAddress(EAPConfigExtensionSubsystemExtension.SUBSYSTEM_PATH);
			final ModelNode addOp = Util.createAddOperation(address);
			operations.add(addOp);

			List<ModelNode> propertiesOps = null;

			while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
				String ns = reader.getNamespaceURI();
				if (ns.equals(EAPConfigExtensionSubsystemExtension.NAMESPACE)) {
					final Element element = Element.forName(reader
							.getLocalName());
					switch (element) {
					case PROPERTIES: {
						propertiesOps = parseProperties(reader, address);
						break;
					}
					default:
						throw unexpectedElement(reader);
					}
				}
			}

			if (propertiesOps != null) {
				operations.addAll(propertiesOps);
			}
		}

		private List<ModelNode> parseProperties(XMLExtendedStreamReader reader,
				PathAddress address) throws XMLStreamException {

			requireNoAttributes(reader);

			List<ModelNode> result = new ArrayList<ModelNode>();
			// Handle properties
			while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
				String namespace = reader.getNamespaceURI();
				if (namespace.equals(EAPConfigExtensionSubsystemExtension.NAMESPACE)) {
					final Element element = Element.forName(reader
							.getLocalName());
					switch (element) {
					case PROPERTY: {
						ModelNode propNode = parseProperty(reader, address);
						result.add(propNode);
						break;
					}
					default:
						throw unexpectedElement(reader);
					}
				}
			}
			return result;
		}

		private ModelNode parseProperty(XMLExtendedStreamReader reader,
				PathAddress parent) throws XMLStreamException {

			// Handle attributes
			String name = null;
			String value = null;
			int count = reader.getAttributeCount();
			for (int i = 0; i < count; i++) {
				requireNoNamespaceAttribute(reader, i);
				final String attrValue = reader.getAttributeValue(i);
				final Attribute attribute = Attribute.forName(reader
						.getAttributeLocalName(i));
				switch (attribute) {
				case NAME: {
					name = attrValue;
					break;
				}
				case VALUE: {
					value = attrValue;
					break;
				}
				default:
					throw unexpectedAttribute(reader, i);
				}
			}

			if (name == null) {
				throw missingRequired(reader,
						Collections.singleton(Attribute.NAME));
			}
			if (value == null) {
				throw missingRequired(reader,
						Collections.singleton(Attribute.VALUE));
			}

			requireNoContent(reader);

			final PathAddress address = parent.append(ELEMENT_PROPERTY, name);
			ModelNode propNode = Util.createAddOperation(address);
			propNode.get(ATTRIBUTE_VALUE).set(value);
			return propNode;
		}
	}
}
