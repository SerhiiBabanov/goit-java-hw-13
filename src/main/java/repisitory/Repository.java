package repisitory;

import java.util.List;

public interface Repository<T> {
    T put(T user) throws Exception;

    T post(T user) throws Exception;

    T getById(int id) throws Exception;

    T getByParameter(String parameterName, String parameter) throws Exception;

    void delete(T user) throws Exception;

    List<T> get() throws Exception;

}
