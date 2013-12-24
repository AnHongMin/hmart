package com.hmart.common.util.json;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.json.JSONObject;

/**
 * <pre>
 * iBatis에서 바로 JSONObject형태로 받기위한 클래스.
 * 쿼리xml에서
 * &lt;typeAlias alias="JSONMap" type="com.hmart.json.JSONMap" /&gt;
 * 처럼 지정해두고 사용..
 * </pre>
 */
@SuppressWarnings("rawtypes")
public class JSONMap extends JSONObject implements Map {
	public int size() {
		return super.length();
	}

	public boolean isEmpty() {
		return (super.length() == 0 ? true : false);
	}

	public boolean containsKey(Object key) {
		return !super.isNull(key.toString());
	}

	public boolean containsValue(Object value) {
		Iterator it = super.keys();
		while (it.hasNext()) {
			String key = it.next().toString();
			try {
				if (super.get(key) == value) {
					return true;
				}
			} catch (Exception e) {
			}
		}
		return false;
	}

	public Object get(Object key) {
		try {
			return super.get(key.toString());
		} catch (Exception e) {
		}
		return null;
	}

	public Object put(Object key, Object value) {
		try {
			return super.put(key.toString(), value);
		} catch (Exception e) {
		}
		return null;
	}

	public Object remove(Object key) {
		return super.remove(key.toString());
	}

	public void putAll(Map m) {
		Iterator it = m.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next().toString();
			try {
				super.put(key, m.get(key));
			} catch (Exception e) {
			}
		}
	}

	public void clear() {
		Iterator it = super.keys();
		while (it.hasNext()) {
			String key = it.next().toString();
			super.remove(key);
		}
	}

	public Set keySet() {
		return null;
	}

	public Collection values() {
		return null;
	}

	public Set entrySet() {
		return null;
	}
}
