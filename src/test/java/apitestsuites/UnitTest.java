package apitestsuites;

import apiautomation.GitHubServiceGenerator;
import apiautomation.User;
import apiautomation.UserService;
import commonutilities.LogConfig;
import okhttp3.OkHttpClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class UnitTest extends LogConfig {
    private Retrofit retrofit;
    private UserService service;
    private User user = null;
    private Logger logger;

    @Before
    public void setUp() {
        //Manual creation of the REST service using retrofit client
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        service = retrofit.create(UserService.class);
        logger = getLogger();
    }

    @Test
    public void test() {
        //Call<User> object is used for executing the request to the GitHub API
        Call<User> callSync = service.getUser("eugenp");
        try {
            //To get response by executing a call synchronously i.e. by blocking the current thread while transferring the data
            Response<User> response = callSync.execute();
            user = response.body();
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        logger.info("Response body: " + user);
        assertThat("Id is not matching", user.getId(), is(equalTo(1022859L)));
    }

    @Test
    public void asyncTest() {
        UserService service = GitHubServiceGenerator.createService(UserService.class);
        Call<User> callAsync = service.getUser("eugenp");
        //A non-blocking asynchronous request
        //enqueue method – which takes a Callback<User>interface as a parameter
        // to handle the success or failure of the request. Note that this will execute in a separate thread.
        callAsync.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                user = response.body();
                logger.info("Response body: " + user);
                assertThat("Id is not matching", user.getId(), is(equalTo(1022859L)));
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {
                logger.error(throwable.toString());
            }
        });
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
    }

    @After
    public void tearDown() {

    }
}
