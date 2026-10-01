package tests;

import com.jayway.jsonpath.JsonPath;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class APITest {

    Playwright playwright;

    @Test
    public void e2eApiTest() {

        HashMap<Object, Object> loginPayload = new HashMap<>();
        loginPayload.put("email", "student@example.com");
        loginPayload.put("password", "secret123");

        playwright = Playwright.create();
        APIRequestContext apiRequest = playwright.request().newContext();
        APIResponse apiResponse = apiRequest.post("https://api.eventhub.rahulshettyacademy.com/api/auth/login",
                RequestOptions.create().setData(loginPayload));
        assertTrue(apiResponse.ok());
        System.out.println("apiResponse = " + apiResponse.text());

        String token = JsonPath.read(apiResponse.text(), "$.token");
        System.out.println("token = " + token);

        //Create Event
        HashMap<Object, Object> eventPayload = new HashMap<>();
        eventPayload.put("title", "My Event");
        eventPayload.put("description", "My Event Description");
        eventPayload.put("category", "Concert");
        eventPayload.put("venue", "My Venue");
        eventPayload.put("city", "My City");
        eventPayload.put("eventDate", "2026-10-30T19:49:00.000Z");
        eventPayload.put("price", 1200);
        eventPayload.put("totalSeats", 400);

        APIResponse eventResponse = apiRequest.post("https://api.eventhub.rahulshettyacademy.com/api/events",
                RequestOptions.create().setHeader("Authorization", "Bearer "+token)
                        .setData(eventPayload));

        assertTrue(eventResponse.ok(), "Event creation succeeded");
        System.out.println("eventResponse = " + eventResponse.text());
        int eventId = JsonPath.read(eventResponse.text(), "$.data.id");
        System.out.println("eventId = " + eventId);

        // Get Events
        APIResponse getEventsResponse = apiRequest.get("https://api.eventhub.rahulshettyacademy.com/api/events",
                RequestOptions.create().setQueryParam("page", "1").setQueryParam("limit", "12")
                        .setHeader("Authorization", "Bearer "+token));

        assertTrue(getEventsResponse.ok(), "Get Events succeeded");
        System.out.println("getEventsResponse = " + getEventsResponse.text().toString());

        List<Integer> allEvents = JsonPath.read(getEventsResponse.text(), "$.data.*.id");
        assertTrue(allEvents.contains(eventId), "Event ID found in list of events");

        //Delete event
         APIResponse deleteEventResponse = apiRequest.delete("https://api.eventhub.rahulshettyacademy.com/api/events/"+eventId,
                 RequestOptions.create().setHeader("Authorization", "Bearer "+token));

         assertTrue(deleteEventResponse.ok(), "Delete event succeeded");

         //Verify event is deleted

        APIResponse getEventsResponseAfterDelete = apiRequest.get("https://api.eventhub.rahulshettyacademy.com/api/events",
                RequestOptions.create()
                        .setQueryParam("page", "1")
                        .setQueryParam("limit", "12")
                        .setHeader("Authorization", "Bearer "+token));

        List<Integer> allEventsAfterDelete = JsonPath.read(getEventsResponseAfterDelete.text(), "$.data.*.id");
        assertFalse(allEventsAfterDelete.contains(eventId), "Event ID not found in list of events");

    }
}

