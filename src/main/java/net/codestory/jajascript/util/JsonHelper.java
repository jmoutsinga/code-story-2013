/**
 * 
 */
package net.codestory.jajascript.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import net.codestory.jajascript.domain.RentalWish;

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

    public List<RentalWish> fromJsonAsStream(InputStream inputStream) {
        Type type = new TypeToken<List<RentalWish>>() {
        }.getType();
        List<RentalWish> result = jsonHelper.fromJson(new InputStreamReader(inputStream), type);
        return result == null ? Collections.<RentalWish> emptyList() : result;
    }

    public <T> T fromJson(String jsonString, Class<T> clazz) {
        return jsonHelper.fromJson(jsonString, clazz);
    }

    public <T> String toJsonString(T optimalRent) {
        return jsonHelper.toJson(optimalRent);
    }
}
