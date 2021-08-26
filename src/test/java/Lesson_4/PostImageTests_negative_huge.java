package Lesson_4;

import Lesson_4.BaseTest;
import com.github.javafaker.Faker;
import img.Endpoints;
import img.Images;
import io.qameta.allure.Feature;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.FileEncodingUtils;

import static io.restassured.RestAssured.given;

@Feature("")
public class PostImageTests_negative_huge extends BaseTest {
    static final String filePath = Images.TOO_BIG.path;
    private String uploadedImageId;
    MultiPartSpecification multiPartSpec;
    RequestSpecification uploadReqSpec;
    Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        byte[] fileContent = FileEncodingUtils.getFileContent(filePath);
        multiPartSpec = new MultiPartSpecBuilder(fileContent)
                .controlName("image")
                .build();
        uploadReqSpec = reqSpec.multiPart(multiPartSpec)
                .formParam("title", faker.harryPotter().character())
                .formParam("description", faker.harryPotter().quote());
    }

    @Test
    void uploadFileTest() {
        given()
                .spec(uploadReqSpec)
                .expect()
                .when()
                .post(Endpoints.POST_IMAGE_REQUEST)
                .prettyPeek()
                .then()
                .spec(responseSpecNegative);
    }
}