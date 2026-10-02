package tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class DataProviderDemoTest {
    @Tag("smoke")
    // Data written inline - each row runs the test once
    @ParameterizedTest
    @CsvSource({
            "student@example.com, secret123",
            "wrong@example.com,   badpass"
    })
    void printWithCsvSource(String email, String password) {
        System.out.println("email = " + email + ", password = " + password);
    }

    // Data comes from a static method - closest to TestNG's @DataProvider
    static Stream<Arguments> loginData() {
        return Stream.of(
                Arguments.of("student@example.com", "secret123"),
                Arguments.of("wrong@example.com", "badpass")
        );
    }

    @ParameterizedTest
    @MethodSource("loginData")
    void printWithMethodSource(String email, String password) {
        System.out.println("email = " + email + ", password = " + password);
    }

    // Each HashMap is one row - values are looked up by key instead of by position
    static Stream<Map<String, String>> loginDataAsMap() {
        Map<String, String> validUser = new HashMap<>();
        validUser.put("email", "student@example.com");
        validUser.put("password", "secret123");

        Map<String, String> invalidUser = new HashMap<>();
        invalidUser.put("email", "wrong@example.com");
        invalidUser.put("password", "badpass");

        return Stream.of(validUser, invalidUser);
    }

    @ParameterizedTest
    @MethodSource("loginDataAsMap")
    void printWithHashMap(Map<String, String> data) {
        System.out.println("email = " + data.get("email") + ", password = " + data.get("password"));
    }

    // JUnit has no JSON source, so read the file into a list of maps ourselves
    static Stream<Map<String, String>> loginDataFromJson() throws IOException {
        String json = Files.readString(Path.of("src/test/resources/testData_TC1.json"));
        List<Map<String, String>> rows = new Gson().fromJson(json,
                new TypeToken<List<Map<String, String>>>() {}.getType());
        return rows.stream();
    }

    @ParameterizedTest
    @MethodSource("loginDataFromJson")
    void printWithJson(Map<String, String> data) {
        System.out.println("email = " + data.get("email") + ", password = " + data.get("password"));
    }
}
