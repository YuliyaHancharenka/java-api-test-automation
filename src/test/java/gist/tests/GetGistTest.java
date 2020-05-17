package gist.tests;

import com.jayway.restassured.response.Response;
import gist.model.Gist;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;

public class GetGistTest extends BaseTest {

    private final String gistId = "a52ef150f5e06a9e94025b3ee998961a";

    @Test(description = "Checks status code for getting a user's gists")
    public void checkGetGistsStatusCodeTest() {
        Response response = getGivenAuth().get().andReturn();
        Assert.assertEquals(response.statusCode(), STATUS_CODE_OK);
    }

    @Test(description = "List a user's gists")
    public void listUsersGistsTest() {
        Response response = getGivenAuth().get().andReturn();
        Gist[] gists = response.as(Gist[].class);
        Assert.assertEquals(response.statusCode(), STATUS_CODE_OK);
        Assert.assertTrue(gists.length > 0);
    }

    @Test(description = "List all public gists")
    public void listAllPublicGistsTest() {
        Response response = getGivenAuth().get("/public").andReturn();
        Gist[] gists = response.as(Gist[].class);
        Assert.assertEquals(response.statusCode(), STATUS_CODE_OK);
        Assert.assertTrue(Arrays.stream(gists).allMatch(Gist::isPublic));
    }

    @Test(description = "List gist commits")
    public void listGistCommits() {
        Response response = getGivenAuth().get("/" + gistId + "/commits").andReturn();
        Assert.assertEquals(response.statusCode(), STATUS_CODE_OK);
    }
}