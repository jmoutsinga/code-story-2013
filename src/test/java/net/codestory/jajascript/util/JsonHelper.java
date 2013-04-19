/**
 * 
 */
package net.codestory.jajascript.util;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

import javax.servlet.ServletInputStream;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author jmoutsinga
 * 
 */
public class JsonHelper {

    private Gson jsonHelper;

    public JsonHelper() {
        jsonHelper = new Gson();
    }

    public <T> List<T> fromJsonAsStream(ServletInputStream inputStream) {
        Type type = new TypeToken<List<T>>() {
        }.getType();
        return jsonHelper.fromJson(new InputStreamReader(inputStream), type);
    }

    public <T> T fromJson(String jsonString, Class<T> clazz) {
        return jsonHelper.fromJson(jsonString, clazz);
    }

    public <T> String toJsonString(T optimalRent) {
        return jsonHelper.toJson(optimalRent);
    }
}
