package gist.tests;

import com.jayway.restassured.response.Response;
import gist.model.Gist;
import gist.model.GistFile;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CRUDGistTest extends BaseTest {

    private String gistId;
    private static final String FILE_NAME = "test_file.txt";

    @Test(description = "Create a gist")
    public void createGistTest() {
        Response response  = getGivenAuth().body(getGist()).with().contentType("application/json").post().andReturn();
        Assert.assertEquals(response.statusCode(), STATUS_CODE_CREATED);
        gistId = response.as(Gist.class).getId();
    }

    @Test(dependsOnMethods = "createGistTest", description = "Get a single gist")
    public void checkGistTest() {
        Response response = getGivenAuth().get("/" + gistId).andReturn();
        Gist gist = response.as(Gist.class);
        Assert.assertEquals(response.statusCode(), STATUS_CODE_OK);
        Assert.assertTrue(gist.getFiles().containsKey(FILE_NAME));
    }

    @Test(dependsOnMethods = "checkGistTest", description = "Edit a gist")
    public void updateGistTest() {
        Gist gist = getGist();
        String oldDescription = gist.getDescription();
        String newDescription = "Updated description";
        String actualDescription;
        Response response;

        gist.setDescription(newDescription);
        response = getGivenAuth().body(gist).with().contentType("application/json").patch("/" + gistId).andReturn();
        actualDescription = response.as(Gist.class).getDescription();

        Assert.assertEquals(response.statusCode(), STATUS_CODE_OK);
        Assert.assertNotEquals(actualDescription, oldDescription);
        Assert.assertEquals(actualDescription, newDescription);
    }

    @Test(dependsOnMethods = "updateGistTest", description = "Delete a gist")
    public void removeGistTest() {
        Response response = getGivenAuth().delete("/" + gistId).andReturn();
        Assert.assertEquals(response.statusCode(), STATUS_CODE_NO_CONTENT);
    }

    private Gist getGist() {
        Gist gist = new Gist();
        gist.setDescription("The description for this gist");
        gist.setPublic(true);
        gist.setFile(FILE_NAME, new GistFile("String file content"));
        return gist;
    }
}
