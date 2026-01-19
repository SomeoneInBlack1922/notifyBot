package group.notify.telegram;

import java.io.StringReader;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.Exception;

public class ResponseParser{
    private static Gson gson = new Gson();
    public static <T> T parse(String object, TypeToken<T> type) throws Exception{
        return gson.fromJson(new StringReader(object), type);
    }
}