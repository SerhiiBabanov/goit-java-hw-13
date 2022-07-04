package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Comment;
import model.Post;
import model.Todos;
import model.User;
import org.apache.http.client.utils.URIBuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class HttpUtil {
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
    private static final Gson GSON = new Gson();
    private final String URL;

    public HttpUtil(String url) {
        URL = url;
    }

    public User sendGet(URI uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        List<User> users = GSON.fromJson(response.body(), new TypeToken<List<User>>() {
        }.getType());
        return users.get(0);
    }

    public List<User> sendGetWithListOfResults(URI uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), new TypeToken<List<User>>() {
        }.getType());
    }

    public User sendPost(URI uri, User user) throws IOException, InterruptedException {
        final String requestBody = GSON.toJson(user);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Content-type", "application/json")
                .build();
        final HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), User.class);
    }

    public User sendPut(URI uri, User user) throws IOException, InterruptedException {
        final String requestBody = GSON.toJson(user);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Content-type", "application/json")
                .build();
        final HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), User.class);
    }

    public boolean sendDelete(URI uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .DELETE()
                .build();
        HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return true;
    }

    public List<Comment> getCommentsFromLastPost(User user) throws IOException, InterruptedException, URISyntaxException {
        URIBuilder URI_BUILDER = new URIBuilder(URI.create(URL));
        int idLastPost = getPostsByUser(user).stream().mapToInt(Post::getId).max().orElse(0);
        URI uri = URI_BUILDER.setPath("comments").setParameter("postId", String.valueOf(idLastPost)).build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .header("Content-type", "application/json")
                .build();
        final HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        List<Comment> comments = GSON.fromJson(response.body(), new TypeToken<List<Comment>>() {
        }.getType());
        String fileName = String.format("user-%d-post-%d-comments.json", user.getId(), idLastPost);
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)
        ) {
            fileOutputStream.write(response.body().getBytes(StandardCharsets.UTF_8));
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        return comments;
    }

    public List<Todos> getOpenTask(User user) throws URISyntaxException, IOException, InterruptedException {
        URIBuilder URI_BUILDER = new URIBuilder(URI.create(URL));
        URI uri = URI_BUILDER.setPath("users/" + user.getId() + "/todos").setParameter("completed", "false").build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .header("Content-type", "application/json")
                .build();
        final HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), new TypeToken<List<Todos>>() {
        }.getType());

    }

    public List<Post> getPostsByUser(User user) throws IOException, InterruptedException, URISyntaxException {
        URIBuilder URI_BUILDER = new URIBuilder(URI.create(URL));
        URI uri = URI_BUILDER.setPath("users/" + user.getId() + "/posts").build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .header("Content-type", "application/json")
                .build();
        final HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), new TypeToken<List<Post>>() {
        }.getType());
    }


}
