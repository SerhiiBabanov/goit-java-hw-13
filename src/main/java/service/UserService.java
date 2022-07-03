package service;

import model.Address;
import model.Company;
import model.Geo;
import model.User;
import repisitory.Repository;

import java.util.Collections;
import java.util.List;

public class UserService {
    private final Repository repository;

    public UserService(Repository repository) {
        this.repository = repository;
    }

    public User create(User user) {
        try {
            return (User) repository.post(user);
        } catch (Exception e) {
            System.out.println(e);
        }
        return user;
    }

    public User update(User user) {
        try {
            return (User) repository.put(user);
        } catch (Exception e) {
            System.out.println(e);
        }
        return user;
    }

    public boolean delete(User user) {
        try {
            repository.delete(user);
        } catch (Exception e) {
            System.out.println(e);
        }
        return true;
    }

    public List getAll() {
        try {
            return repository.get();
        } catch (Exception e) {
            System.out.println(e);
        }
        return Collections.emptyList();
    }

    public User getUserById(int id) {
        try {
            return (User) repository.getById(id);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public User getByParameter(String username) {
        try {
            return (User) repository.getByParameter("username", username);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public static User getDefaultUser() {
        User newUser = new User();
        newUser.setId(1);
        newUser.setName("Serhii Babanov");
        newUser.setUsername("serhiibabanov");
        newUser.setEmail("example@google.com");

        Address address = new Address();
        address.setCity("Cambridge");
        address.setStreet("Main Ave");
        address.setSuite("Apt. 101");
        address.setZipcode("A1B2C3");

        Geo geo = new Geo();
        geo.setLat(11.11);
        geo.setLng(-22.22);
        address.setGeo(geo);

        newUser.setAddress(address);
        newUser.setPhone("555-555-5555");
        newUser.setWebsite("example.goit.com");

        Company company = new Company();
        company.setName("My Great Company");
        company.setCatchPhrase("It`s the greatest company in the world");
        company.setBs("do all in the best way");

        newUser.setCompany(company);
        return newUser;
    }
}
