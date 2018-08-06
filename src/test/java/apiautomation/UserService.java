package apiautomation;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

/**
 * It represents Rest Services
 */
public interface UserService {

    //    Annotation Format- Http Method with relative resource(data to be requested) path as an argument
    @GET("/users")
    public Call<List<User>> getUsers(
            @Query("per_page") int per_page,
            @Query("page") int page);

    @GET("/users/{username}")
    public Call<User> getUser(@Path("username") String username);
}