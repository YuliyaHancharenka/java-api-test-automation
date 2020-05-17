package gist.tests;

import com.jayway.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class StarGistTest extends BaseTest {

    private final String gistId = "a52ef150f5e06a9e94025b3ee998961a";
    private final String url = "/" + gistId + "/star";

    @Test(description = "Star a gist")
    public void starGistTest() {
        Response response  = getGivenAuth().put(url).andReturn();
        Assert.assertEquals(response.statusCode(), STATUS_CODE_NO_CONTENT);
    }

    @Test(dependsOnMethods = "starGistTest", description = "Check if a gist is starred")
    public void checkGistStaredTest() {
        Response response  = getGivenAuth().get(url).andReturn();
        Assert.assertEquals(response.statusCode(), STATUS_CODE_NO_CONTENT);
    }

    @Test(dependsOnMethods = "checkGistStaredTest", description = "Unstar a gist")
    public void unstarGistTest() {
        Response response  = getGivenAuth().delete(url).andReturn();
        Assert.assertEquals(response.statusCode(), STATUS_CODE_NO_CONTENT);
    }

    @Test(dependsOnMethods = "unstarGistTest", description = "Check if a gist is starred")
    public void checkGistUnstarredTest() {
        Response response  = getGivenAuth().get(url).andReturn();
        Assert.assertEquals(response.statusCode(), STATUS_CODE_NOT_FOUND);
    }

}
