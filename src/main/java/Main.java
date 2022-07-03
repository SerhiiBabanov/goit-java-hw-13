import com.google.gson.Gson;
import model.Address;
import model.Company;
import model.Geo;
import model.User;
import repisitory.Repository;
import repisitory.UserRepository;
import service.HttpUtil;
import service.UserService;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Main {
    private static final String CREATE_USER_URL = "https://jsonplaceholder.typicode.com/users";
    private static final String UPDATE_USER_URL = "https://jsonplaceholder.typicode.com/users";
    private static final String GET_USER_URL = "https://jsonplaceholder.typicode.com/users";
    private static final String GET_POST_URL = "https://jsonplaceholder.typicode.com/posts";
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
//        Repository<User> repository = new UserRepository("https://jsonplaceholder.typicode.com/users");
//        UserService userService = new UserService(repository);
//
//        User defaultUser = userService.getDefaultUser();
//        System.out.println("Default user: \n" + defaultUser);
//
//        User createdUser = userService.create(defaultUser);
//        System.out.println("Created user: \n" + createdUser);
//
//        defaultUser.setName("Serhii Babanov Olechovith");
//        User updatedUser = userService.update(defaultUser);
//        System.out.println("Updated user: \n" + updatedUser);
//
//        System.out.println("Delete user: \n" + userService.delete(defaultUser));
//
//        System.out.println("Get all users: \n" + userService.getAll());
//
//        System.out.println("Get user id = 1: \n" + userService.getUserById(1));
//
//        System.out.println("Get user username = Antonette: \n" + userService.getByParameter("Antonette"));

        User defaultUser = UserService.getDefaultUser();
        System.out.println("Default user: \n" + defaultUser);

        User createdUser = HttpUtil.sendPost(URI.create(CREATE_USER_URL), defaultUser);
        System.out.println("Created user: \n" + createdUser);

        defaultUser.setName("Serhii Babanov Olechovith");
        User updatedUser = HttpUtil.sendPut(URI.create(UPDATE_USER_URL), defaultUser);
        System.out.println("Updated user: \n" + updatedUser);

        System.out.println("Delete user: \n" + HttpUtil.sendDelete(URI.create(UPDATE_USER_URL), defaultUser));

        System.out.println("Get all users: \n" + HttpUtil.sendGetWithListOfResults(URI.create(GET_USER_URL)));

        System.out.println("Get user id = 1: \n" + HttpUtil.sendGetWithParameters(URI.create(GET_USER_URL),
                "id", String.valueOf(1)));

        System.out.println("Get user username = Antonette: \n" + HttpUtil.sendGetWithParameters(URI.create(GET_USER_URL),
                "username", "Antonette"));


    }
}
