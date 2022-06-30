package repisitory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.User;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.util.List;

public class UserRepository implements Repository<User> {
    private final String URL;
    private final Gson GSON = new Gson();
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private final ResponseHandler<String> responseHandler = response -> {
        int status = response.getStatusLine().getStatusCode();
        if (status >= 200 && status < 300) {
            HttpEntity entity = response.getEntity();
            return entity != null ? EntityUtils.toString(entity) : null;
        } else {
            throw new ClientProtocolException("Unexpected response status: " + status);
        }
    };

    public UserRepository(String url) {
        URL = url;
    }

    @Override
    public User getById(int id) throws Exception {
        HttpGet httpGet = new HttpGet(URL + "//" + id);
        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("Content-type", "application/json");
        String responseBody = httpClient.execute(httpGet, responseHandler);
        return GSON.fromJson(responseBody, User.class);
    }

    @Override
    public User getByParameter(String parameter, String value) throws Exception {
        HttpGet httpGet = new HttpGet(URL + "//");
        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("Content-type", "application/json");
        URI uri = new URIBuilder(httpGet.getURI())
                .addParameter(parameter, value)
                .build();
        httpGet.setURI(uri);
        String responseBody = httpClient.execute(httpGet, responseHandler);
        List<User> users = GSON.fromJson(responseBody, new TypeToken<List<User>>() {
        }.getType());
        return users != null ? users.get(0) : null;
    }

    @Override
    public User put(User user) throws Exception {
        HttpPut httpPut = new HttpPut(URL + "//" + user.getId());
        httpPut.setHeader("Accept", "application/json");
        httpPut.setHeader("Content-type", "application/json");
        StringEntity stringEntity = new StringEntity(GSON.toJson(user));
        httpPut.setEntity(stringEntity);
        String responseBody = httpClient.execute(httpPut, responseHandler);
        return GSON.fromJson(responseBody, User.class);
    }

    @Override
    public User post(User user) throws Exception {
        HttpPost httpPost = new HttpPost(URL);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        StringEntity stringEntity = new StringEntity(GSON.toJson(user));
        httpPost.setEntity(stringEntity);

        String responseBody = httpClient.execute(httpPost, responseHandler);
        return GSON.fromJson(responseBody, User.class);
    }


    @Override
    public void delete(User user) throws Exception {
        HttpDelete httpDelete = new HttpDelete(URL + "//" + user.getId());
        httpDelete.setHeader("Accept", "application/json");
        httpDelete.setHeader("Content-type", "application/json");
        StringEntity stringEntity = new StringEntity(GSON.toJson(user));
        String responseBody = httpClient.execute(httpDelete, responseHandler);

    }


    @Override
    public List<User> get() throws Exception {
        HttpGet httpGet = new HttpGet(URL);
        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("Content-type", "application/json");
        String responseBody = httpClient.execute(httpGet, responseHandler);
        return GSON.fromJson(responseBody, new TypeToken<List<User>>() {
        }.getType());
    }


}
