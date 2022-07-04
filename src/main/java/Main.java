import model.User;
import service.HttpUtil;
import service.UserService;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Main {
    private static final String SITE_URL = "https://jsonplaceholder.typicode.com";
    private static final String CREATE_USER_URL = "https://jsonplaceholder.typicode.com/users";
    private static final String UPDATE_USER_URL = "https://jsonplaceholder.typicode.com/users";
    private static final String DELETE_USER_URL = "https://jsonplaceholder.typicode.com/users";
    private static final String GET_USER_URL = "https://jsonplaceholder.typicode.com/users";

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        HttpUtil httpUtil = new HttpUtil(SITE_URL);
        User defaultUser = UserService.getDefaultUser();
        System.out.println("Default user: \n" + defaultUser + "\n");

        User createdUser = httpUtil.sendPost(URI.create(CREATE_USER_URL), defaultUser);
        System.out.println("Created user: \n" + createdUser + "\n");

        defaultUser.setName("Serhii Babanov Olechovith");
        User updatedUser = httpUtil.sendPut(URI.create(String.format("%s/%d", UPDATE_USER_URL, defaultUser.getId())), defaultUser);
        System.out.println("Updated user: \n" + updatedUser + "\n");

        System.out.println("Delete user: \n" + httpUtil.sendDelete(URI.create(String.format("%s/%d", DELETE_USER_URL, defaultUser.getId()))) + "\n");

        System.out.println("Get all users: \n" + httpUtil.sendGetWithListOfResults(URI.create(GET_USER_URL)) + "\n");

        System.out.println("Get user id = 1: \n" + httpUtil.sendGet(URI.create(String.format("%s?id=%d", GET_USER_URL, defaultUser.getId()))) + "\n");

        System.out.println("Get user username = Antonette: \n"
                + httpUtil.sendGet(URI.create(String.format("%s?username=%s", GET_USER_URL, "Antonette"))) + "\n");

        System.out.println("Get comments from last post: " + httpUtil.getCommentsFromLastPost(defaultUser) + "\n");

        System.out.println("Get open tasks: " + httpUtil.getOpenTask(defaultUser) + "\n");

    }
}
