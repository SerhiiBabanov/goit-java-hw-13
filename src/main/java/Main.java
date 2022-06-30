import com.google.gson.Gson;
import model.Address;
import model.Company;
import model.Geo;
import model.User;
import repisitory.Repository;
import repisitory.UserRepository;
import service.UserService;

public class Main {
    public static void main(String[] args) {
        Repository<User> repository = new UserRepository("https://jsonplaceholder.typicode.com/users");
        UserService userService = new UserService(repository);

        User defaultUser = userService.getDefaultUser();
        System.out.println("Default user: \n" + defaultUser);

        User createdUser = userService.create(defaultUser);
        System.out.println("Created user: \n" + createdUser);

        defaultUser.setName("Serhii Babanov Olechovith");
        User updatedUser = userService.update(defaultUser);
        System.out.println("Updated user: \n" + updatedUser);

        System.out.println("Delete user: \n" + userService.delete(defaultUser));

        System.out.println("Get all users: \n" + userService.getAll());

        System.out.println("Get user id = 1: \n" + userService.getUserById(1));

        System.out.println("Get user username = Antonette: \n" + userService.getByParameter("Antonette"));


    }
}
