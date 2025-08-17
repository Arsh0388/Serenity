package Wesco.APITest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static io.restassured.config.HttpClientConfig.httpClientConfig;

import static java.util.concurrent.CompletableFuture.anyOf;
import static org.hamcrest.Matchers.equalTo;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MainAPITest {

    protected static RequestSpecification req;
    private static String token;
    private static int bookId;

    // very important => By default, JUnit 5 creates a new instance of your test class for every @Test method.
    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com"; // API set
        // we can initialize the values for our requests here
        //        also we can reset the values using RestAssured.reset();


//        RestAssured.config = RestAssured.config()
//                .httpClient(httpClientConfig()
//                        // Milliseconds
//                        .setParam("http.connection.timeout", 5_000)          // connect timeout
//                        .setParam("http.socket.timeout", 10_000)             // read timeout
//                        .setParam("http.connection-manager.timeout", 5_000)  // connection manager
//                );

    }
    // [1, 2, 3]
    //    hasItems(1, 2, 3))
    // we can access items like this as well // {
    //    "odd": {
    //        "price": "1.30",
    //        "ck": 12.2,
    //        "name": "1"
    //    }
    //}
    //
    // get("/odd").then().assertThat().body("odd.ck", equalTo(12.2));  // with the . operator we can do this
    public void homePageUI(){
        Response response  = given()
            .contentType("application/json")
                .body("{\"username\":\"admin\",\"password\":\"password123\"}") // proper json format should be there  inside body strings
                .when()
                .post("/auth")
                .then()
                .statusCode(200)
                .extract().response(); // extracting response
        System.out.println("response " + response.asString());
        // retrieve token
        this.token = response.getBody().jsonPath().getString("token");
        // use this token to retrieve
        System.out.println("TOKEN value is " + token);
    }
//    to check response time
// .time(lessThan(5L),TimeUnit.SECONDS);although TimeUnit.SECONDS is optional but by default it is 5ooo miliseconds

//    Field	Type	Description
//    firstname optional	String
//    Return bookings with a specific firstname
//
//    lastname optional	String
//    Return bookings with a specific lastname
//
//    checkin optional	date
//    Return bookings that have a checkin date greater than or equal to the set checkin date. Format must be CCYY-MM-DD
//
//    checkout optional	date
//    Return bookings that have a checkout date greater than or equal to the set checkout date. Format must be CCYY-MM-DD
    @Test
    @Order(1)
    public void getBookings() {
        homePageUI(); // we can also set cookie using header field => .header("Cookie ", "token=" + token)
        Response response = given()
                .cookie("Bearer",token)
                .when()
                .get("/booking")
                .then()
                .statusCode(200) // verifies status code.
                .extract().response();

        List<Map<String, Object>> bodyTracker = response.getBody().as(List.class); // get the body as json list

        System.out.println("bookingid is " + bookId);
        int count = 0;
        for (Map<String, Object> booking : bodyTracker) {
            System.out.println(booking.toString());
            count += 1;
            if (count == 10) break;
        }
    }

    @Test
    @Order(2)
    public void createBooking() {
        // either we can pass this data as map or we can pass it as plain old java object

        Map<String, Object> bookingData = getObjectData();
        // options we have in given field => formParam, queryParam, pathParam, header, port(no) or multipart form data uploading file
        // .multiPart("file", new File("sample.pdf"))
//        .multiPart("description", "Quarterly report")    // simple text field
//                .multiPart("userId", "12345")
        Response response = given()
                        .cookie("token=", this.token)
                        .accept("application/json") // data in json format only
                        .contentType("application/json") // sending data in json format
                        .body(bookingData)
                        .when() // this supports variousa methods such as get , post , delete , put , patch
                        .post("/booking")
                        .then()
                        .statusCode(200)
                        .log().ifValidationFails()
                        .extract().response();
        // .log().all() → logs request & response always
        //
        //.log().body() → logs body only
        //
        //.log().ifError() → logs only if server returns 4xx/5xx
        //
        //.log().ifValidationFails() → logs only if your assertions fail
        //
        //.log().ifStatusCodeIsEqualTo(404)
        bookId = response.jsonPath().getInt("bookingid"); // this  returns an object so we need to cast it to int
        System.out.println("response " + response.asString());
    }
    public Map<String,Object> getObjectData() {
        Map<String,Object> bookingData = new HashMap<>();
        bookingData.put("firstname", "Jim");
        bookingData.put("lastname", "Brown");
        bookingData.put("totalprice", 111);
        bookingData.put("depositpaid", true);

        Map<String, String> bookingDates = new HashMap<>();
        bookingDates.put("checkin", "2025-01-01");
        bookingDates.put("checkout", "2025-01-05");

        bookingData.put("bookingdates", bookingDates);
        bookingData.put("additionalneeds", "Breakfast");
        return bookingData;
    }

    @Test
    @Order(2)
    public void getTheSpecificBooking() {
        given()
                .accept("application/json")
                .pathParam("id", bookId)
                .when()
                .get("/booking/{id}")
                .then()
                .statusCode(200)
                .assertThat()
                .body("firstname", equalTo("Jim"))
                .body("lastname", equalTo("Brown"));

    }

    // test for patch request updating some fields not all
    @Test
    @Order(3)
    public void updateBooking() {
        // update the booking
        System.out.println("bookingid is " + bookId);
        System.out.println("token is " + this.token);
        given()
                .contentType("application/json")
                .accept("application/json")
                .cookie("token", this.token)
                .pathParam("id", bookId)
                .body("{\"firstname\":\"Jim\",\"lastname\":\"Arora\"}")
        .when()
                .patch("/booking/{id}")
        .then()
                .statusCode(200)
                .assertThat()
                .body("firstname", equalTo("Jim"), // we can insert assert statement inside this such that
                        "lastname", equalTo("Arora")); // we can check it here as well in the body whether fields mastch as well or not
        // we can also match the pojo ..
        // extract data as .extract().as(Booking.class);   // ✅ parse into POJO
        // to match the fields we an just create a function which checks whether the object is actually there or not .


    }

    @Test
    @Order(4)
    public void updateBookingWithPut() {
        // full booking payload
        Map<String, Object> bookingData = new HashMap<>();
        bookingData.put("firstname", "Jim");
        bookingData.put("lastname", "Arora");
        bookingData.put("totalprice", 150);
        bookingData.put("depositpaid", true);

        Map<String, String> bookingDates = new HashMap<>();
        bookingDates.put("checkin", "2025-02-01");
        bookingDates.put("checkout", "2025-02-05");
        bookingData.put("bookingdates", bookingDates);

        bookingData.put("additionalneeds", "Lunch");

        given()
                .contentType("application/json")
                .accept("application/json")
                .cookie("token", token)           // correct cookie
                .pathParam("id", bookId)          // bookingId from POST
                .body(bookingData)                // full object
                .when()
                .put("/booking/{id}")
                .then()
                .statusCode(200)
                .body("firstname", equalTo("Jim"))
                .body("lastname", equalTo("Arora"))
                .body("totalprice", equalTo(150))
                .body("additionalneeds", equalTo("Lunch"));
    }

    @Test
    @Order(5)
    public void deleteBooking() {
        // Assumes: token set earlier, bookId created by POST and still exists
        given()
                .cookie("token", token)         // required for delete
                .pathParam("id", bookId)
                .when()
                .delete("/booking/{id}")
                .then()
                // Restful-Booker often returns 201 on successful delete, but can be 200/204
                .statusCode(201);
    }

    @Test
    @Order(6)
    public void verifyBookingDeleted() {
        given()
                .accept("application/json")
                .pathParam("id", bookId)
                .when()
                .get("/booking/{id}")
                .then()
                .statusCode(404);               // resource no longer exists
    }

    // (Optional) Negative test to show auth matters
    @Test
    @Order(7)
    public void deleteBooking_withoutToken_should403() {
        // Recreate a booking to delete
        Map<String, Object> booking = new HashMap<>();
        booking.put("firstname", "Jane");
        booking.put("lastname", "Doe");
        booking.put("totalprice", 123);
        booking.put("depositpaid", true);
        Map<String, String> dates = new HashMap<>();
        dates.put("checkin", "2025-03-01");
        dates.put("checkout", "2025-03-05");
        booking.put("bookingdates", dates);
        booking.put("additionalneeds", "Breakfast");

        int tempId =
                given()
                        .log().all() // log request details..
                        .accept("application/json").contentType("application/json").body(booking)
                        .when()
                        .post("/booking")
                        .then()
                        .log().ifStatusCodeIsEqualTo(500) // conditional logging
                        .statusCode(200)
                        .extract().jsonPath().getInt("bookingid");

        // Try deleting without token -> should be 403
        given()
                .pathParam("id", tempId)
                .when()
                .delete("/booking/{id}")
                .then()
                .log().body() // log body
                .statusCode(403);
    }

    // verifiying cml response
//    <employees>
//      <employee category="skilled">
//        <first-name>Jane</first-name>
//        <last-name>Daisy</last-name>
//        <sex>f</sex>
//      </employee>
//    </employees>
    // .body("employees.employee.first-name", equalTo("Jane"));
    // xml response validation hasXPath("/employees/employee/first-name", containsString("Ja"))
}

