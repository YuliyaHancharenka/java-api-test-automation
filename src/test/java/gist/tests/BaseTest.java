package gist.tests;


import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.specification.RequestSpecification;
import gist.utils.PropertyManager;

import static com.jayway.restassured.RestAssured.given;

class BaseTest {

    static final int STATUS_CODE_OK = 200;
    static final int STATUS_CODE_CREATED = 201;
    static final int STATUS_CODE_NO_CONTENT = 204;
    static final int STATUS_CODE_NOT_FOUND = 404;

    private final String token = PropertyManager.get("git.token");
    private static final String BASE_URL = "https://api.github.com/gists";
    private final RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri(BASE_URL).build();

    RequestSpecification getGivenAuth() {
        return given().spec(requestSpecification).auth().oauth2(token);
    }
}