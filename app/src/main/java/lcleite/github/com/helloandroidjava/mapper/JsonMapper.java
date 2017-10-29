package lcleite.github.com.helloandroidjava.mapper;

import org.json.JSONObject;

/**
 * Created by leandro on 22/09/17.
 */

public interface JsonMapper<T> {
    T toModel(JSONObject jsonObject);
}
