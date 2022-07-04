import model.User;
import service.HttpUtil;
import service.UserService;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {


    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        HttpUtil httpUtil = new HttpUtil("https://jsonplaceholder.typicode.com/");
        User defaultUser = UserService.getDefaultUser();
        System.out.println("Default user: \n" + defaultUser + "\n");

        User createdUser = httpUtil.sendPost(defaultUser);
        System.out.println("Created user: \n" + createdUser + "\n");

        defaultUser.setName("Serhii Babanov Olechovith");
        User updatedUser = httpUtil.sendPut(defaultUser);
        System.out.println("Updated user: \n" + updatedUser + "\n");

        System.out.println("Delete user: \n" + httpUtil.sendDelete(defaultUser) + "\n");

        System.out.println("Get all users: \n" + httpUtil.sendGetWithListOfResults() + "\n");

        System.out.println("Get user id = 1: \n" + httpUtil.sendGetWithParameters("id", String.valueOf(1)) + "\n");

        System.out.println("Get user username = Antonette: \n"
                + httpUtil.sendGetWithParameters("username", "Antonette") + "\n");

        System.out.println("Get comments from last post: " + httpUtil.getCommentsFromLastPost(defaultUser) + "\n");

        System.out.println("Get open tasks: " + httpUtil.getOpenTask(defaultUser) + "\n");

    }
}
