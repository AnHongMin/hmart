package com.hmart.common.util.json;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * 객체를 reflection을 이용해서 JSON 객체로 변환한다.
 * @author dagui
 *
 */
public class JSONUtil {
	
	/**
	 * <p>객체를 JSON 호환 가능한 형식으로 변환한다.</p>
	 * 
	 * <p>JSON 호환가능한 형식은 {@link JSONObject},{@link JSONArray},{@link JSONObject#NULL},
	 *
	 * @param object JSON으로 변환할 객체
	 * @return the object
	 * @throws JSONException jSON exception
	 * {@link String}, {@link Integer}, {@link Long}, {@link Boolean} 이다.</p>
	 * 
	 * <p>배열과 {@link Collection} 객체는 {@link JSONArray}로 {@link Map}과 기타 bean 들은
	 * {@link JSONObject}로 변환된다.</p>
	 * @see JSONObject#put(String, Object)
	 * @see #toJSON(Map)
	 * @see #toJSON(Collection)
	 */
	@SuppressWarnings("rawtypes")
	public static Object toJSON(Object object) throws JSONException {
		if (object == null) {
			return JSONObject.NULL;
		}
		else if (object instanceof Short) {
			return new Integer(((Short)object).shortValue());
		}
		else if (object instanceof Float) {
			return new Double(((Float)object).floatValue());
		}
		else if (
				(object instanceof String) ||
				(object instanceof Integer) ||
				(object instanceof Long) ||
				(object instanceof JSONArray) ||
				(object instanceof JSONObject) ||
				(object instanceof Boolean)
		) {
			return object;
		}
		else if (object instanceof Collection) {
			return toJSON((Collection)object);
		}
		else if (object instanceof Map) {
			return toJSON((Map)object);
		}
		// 배열의 경우 List 로 변환
		else if (object.getClass().getComponentType() != null) {
			return toJSON(Arrays.asList((Object[])object));
		}
//		else if (false)
//			// toJSON()
//		}
		else {
			// public 프로퍼티를 이용해서 JSONObject를 만든다.
			PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(object);

			JSONObject jsonObj = new JSONObject();
			for (int i = 0; i < pds.length; i++) {
				PropertyDescriptor pd = pds[i];
				Method getter = pd.getReadMethod();
				if (pd.getName().equals("class") ||
						getter == null ||
						getter.getParameterTypes().length > 0)
					continue;

				try {
					Object value = getter.invoke(object, new Object[]{});
					jsonObj.put(pd.getName(), toJSON(value));
				}
				catch (Exception e) {
					// simply ignore error
				}
			}
			return jsonObj;
		}
	}

	/**
	 * <p>{@link Map}객체를 {@link JSONObject} 형식으로 변환한다. 키들은 모두 {@link String}으로
	 * 값들은 JSON 호환 가능한 형식으로 변환된다.</p>
	 * 
	 * <p>JSON 호환가능한 형식은 {@link JSONObject},{@link JSONArray},{@link JSONObject#NULL},
	 *
	 * @param map JSON으로 변환할 맵 객체
	 * @return the jSON object
	 * @throws JSONException jSON exception
	 * {@link String}, {@link Integer}, {@link Long}, {@link Boolean} 이다.</p>
	 * @see JSONObject#put(String, Object)
	 * @see #toJSON(Object)
	 */
	@SuppressWarnings("rawtypes")
	public static JSONObject toJSON(Map map) throws JSONException {
		JSONObject jsonMap = new JSONObject();
		
		Iterator it = map.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next().toString();
			Object value = map.get(key);
			jsonMap.put(key, toJSON(value));
		}
		
		return jsonMap;
	}
	
	/**
	 * <p>{@link Collection}객체를 {@link JSONArray} 형식으로 변환한다.
	 * 원소들은 모두 JSON 호환 가능한 형식으로 변환된다.</p>
	 * 
	 * <p>JSON 호환가능한 형식은 {@link JSONObject},{@link JSONArray},{@link JSONObject#NULL},
	 *
	 * @param collection JSON으로 변환할 컬렉션 객체
	 * @return the jSON array
	 * @throws JSONException jSON exception
	 * {@link String}, {@link Integer}, {@link Long}, {@link Boolean} 이다.</p>
	 * @see JSONObject#put(String, Object)
	 * @see #toJSON(Object)
	 */
	@SuppressWarnings("rawtypes")
	public static JSONArray toJSON(Collection collection) throws JSONException {
		JSONArray jsonList = new JSONArray();
		
		Iterator it = collection.iterator();
		while (it.hasNext()) {
			Object element = it.next();
			jsonList.put(toJSON(element));
		}
		
		return jsonList;
	}

}
