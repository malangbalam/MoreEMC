package mlbm.moreEMC.api;

import org.mozilla.javascript.Scriptable;

/**
 * @author hasunwoo this class must be extended in order to use script API
 * @ScriptAPI annotation must be use in order to activate this API.
 */
public abstract class ScriptAPIProvider implements Scriptable {

	/**
	 * programmer should return your modid.
	 * 
	 * @return modid
	 */
	abstract public String getModID();

	@Override
	public Object get(String name, Scriptable start) {

		return null;
	}

	@Override
	public Object get(int index, Scriptable start) {

		return null;
	}

	@Override
	public boolean has(String name, Scriptable start) {

		return false;
	}

	@Override
	public boolean has(int index, Scriptable start) {

		return false;
	}

	@Override
	public void put(String name, Scriptable start, Object value) {

	}

	@Override
	public void put(int index, Scriptable start, Object value) {

	}

	@Override
	public void delete(String name) {

	}

	@Override
	public void delete(int index) {

	}

	@Override
	public Scriptable getPrototype() {

		return null;
	}

	@Override
	public void setPrototype(Scriptable prototype) {

	}

	@Override
	public Scriptable getParentScope() {

		return null;
	}

	@Override
	public void setParentScope(Scriptable parent) {

	}

	@Override
	public Object[] getIds() {

		return null;
	}

	@Override
	public Object getDefaultValue(Class<?> hint) {

		return null;
	}

	@Override
	public boolean hasInstance(Scriptable instance) {

		return false;
	}

	/**
	 * it called on script scope building process. if this method return false,
	 * this API won't load
	 * 
	 * @return
	 */
	public boolean shouldLoad() {
		return true;
	}
}
