package org.eapconfig.configextension.deployment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class EAPConfigExtensionProperties extends Properties {
	private static final long serialVersionUID = 1L;

	private List<EAPConfigExtensionPropertyListener> listeners = new ArrayList<EAPConfigExtensionPropertyListener>();
	
	private static final EAPConfigExtensionProperties instance = new EAPConfigExtensionProperties();

	public static EAPConfigExtensionProperties getInstance() {
		return instance;
	}
	
	public void addPropertyListener(EAPConfigExtensionPropertyListener listener) {
		listeners.add(listener);
	}
	
	public void removePropertyListener(EAPConfigExtensionPropertyListener listener) {
		listeners.remove(listener);
	}
	
	private void firePropertyChangeEvent(String key, String value) {
		for (EAPConfigExtensionPropertyListener listener : listeners) {
			listener.propertyChanged(key, value);
		}
	}
	
	@Override
	public synchronized Object remove(Object key) {
		firePropertyChangeEvent(key.toString(), null);
		return super.remove(key);
	}
	
	@Override
	public synchronized Object setProperty(String key, String value) {
		firePropertyChangeEvent(key, value);
		return super.setProperty(key, value);
	}
	@Override
	public synchronized Object put(Object key, Object value) {
		firePropertyChangeEvent(key.toString(), value == null ? null : value.toString());
		return super.put(key, value);
	}
	@Override
	public synchronized void putAll(Map<? extends Object, ? extends Object> t) {
		for (Map.Entry<? extends Object, ? extends Object> entry : t.entrySet()) {
			Object key = entry.getKey();
			Object value = entry.getValue();
			firePropertyChangeEvent(key.toString(), value == null ? null : value.toString());	
		}
		super.putAll(t);
	}
}
