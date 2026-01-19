package group.notify.telegram.dataObjects;

import java.util.Collection;

import com.google.gson.reflect.TypeToken;

public class Response{
    public static TypeToken<Response> typeToken = new TypeToken<Response>() {};
    public boolean ok;
    public Collection<Update> result;
}