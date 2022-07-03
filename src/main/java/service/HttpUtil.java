package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Comment;
import model.Post;
import model.User;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class HttpUtil {
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
    private static final Gson GSON = new Gson();
    URIBuilder URIBuilder = new URIBuilder();

    public static User sendGet(URI uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        final User user = GSON.fromJson(response.body(), User.class);
        return user;
    }

    public static User sendGetWithParameters(URI uri, String parameter, String value) throws IOException, InterruptedException, URISyntaxException {
        URIBuilder builder = new URIBuilder(uri);
        uri = builder.setParameter(parameter, value).build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        List<User> users = GSON.fromJson(response.body(), new TypeToken<List<User>>() {}.getType());
        return users.get(0);
    }

    public static List<User> sendGetWithListOfResults(URI uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        List<User> users = GSON.fromJson(response.body(), new TypeToken<List<User>>() {}.getType());
        return users;
    }

    public static User sendPost(URI uri, User user) throws IOException, InterruptedException {
        final String requestBody = GSON.toJson(user);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Content-type", "application/json")
                .build();
        final HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), User.class);
    }

    public static User sendPut(URI uri, User user) throws IOException, InterruptedException {
        final String requestBody = GSON.toJson(user);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Content-type", "application/json")
                .build();
        final HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), User.class);
    }

    public static boolean sendDelete(URI uri, User user) throws URISyntaxException, IOException, InterruptedException {
        URIBuilder builder = new URIBuilder(uri);
        uri = builder.setParameter("id", String.valueOf(user.getId())).build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .DELETE()
                .build();
        HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return true;
    }

//    public static List<Comment> getCommentsFromLastPost(URI uri, User user) throws IOException, InterruptedException, URISyntaxException {
//        int idLastPost = getPostsByUser(uri,user).stream().mapToInt(p-> p.getId()).max().orElseGet(() ->0);
//        URIBuilder builder = new URIBuilder(uri);
//        uri = builder.setParameter("id", String.valueOf(user.getId())).build();
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(uri)
//                .GET()
//                .header("Content-type", "application/json")
//                .build();
//        final HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
//        List<Post> posts = GSON.fromJson(response.body(), new TypeToken<List<Post>>() {}.getType());
//
//        List<Comment> comments =
//    }

    public static List<Post> getPostsByUser(URI uri, User user) throws IOException, InterruptedException, URISyntaxException {
        URIBuilder builder = new URIBuilder(uri);
        uri = builder.setParameter("id", String.valueOf(user.getId())).build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .header("Content-type", "application/json")
                .build();
        final HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        List<Post> posts = GSON.fromJson(response.body(), new TypeToken<List<Post>>() {}.getType());
        return posts;
    }


}
