package service;

import model.Address;
import model.Company;
import model.Geo;
import model.User;


public class UserService {


    public UserService() {

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
