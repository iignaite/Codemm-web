package com.csit321g1.nightingailles.codemm;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.hasKey;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * End-to-end CRUD checks for the Increment 1 entities, run against an in-memory H2 database.
 */
@SpringBootTest
@AutoConfigureMockMvc
class CoreCrudFlowTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void userCrudHidesPasswordAndRejectsDuplicateEmail() throws Exception {
        String body = """
                {"name":"Ada Learner","email":"ada@cit.edu","password":"password123","role":"LEARNER"}
                """;
        long id = idOf(mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", not(hasKey("passwordHash"))))
                .andReturn().getResponse().getContentAsString(), "userId");

        mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isConflict());

        mockMvc.perform(put("/api/users/" + id).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Ada L.\",\"email\":\"ada@cit.edu\",\"role\":\"LEARNER\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ada L."));

        mockMvc.perform(delete("/api/users/" + id)).andExpect(status().isNoContent());
        mockMvc.perform(get("/api/users/" + id)).andExpect(status().isNotFound());
    }

    @Test
    void invalidBodyReturnsFieldErrors() throws Exception {
        mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors.email").exists());
    }

    @Test
    void goalRoadmapActivityAttemptFlow() throws Exception {
        long userId = idOf(postJson("/api/users",
                "{\"name\":\"Bea\",\"email\":\"bea@cit.edu\",\"password\":\"password123\",\"role\":\"LEARNER\"}"),
                "userId");

        long trackId = idOf(postJson("/api/tracks",
                "{\"title\":\"Java Flow\",\"language\":\"java\"}"), "trackId");
        long lessonId = idOf(postJson("/api/lessons",
                "{\"trackId\":%d,\"title\":\"Loops\",\"position\":1}".formatted(trackId)), "lessonId");

        long goalId = idOf(postJson("/api/goals", """
                {"userId":%d,"trackId":%d,"description":"Pass OOP","priorKnowledge":"basics","depth":"STANDARD"}
                """.formatted(userId, trackId)), "goalId");

        long roadmapId = idOf(postJson("/api/roadmaps",
                "{\"goalId\":%d,\"status\":\"ACTIVE\"}".formatted(goalId)), "roadmapId");

        postJson("/api/roadmap-items", "{\"roadmapId\":%d,\"lessonId\":%d,\"position\":1,\"status\":\"CURRENT\"}"
                .formatted(roadmapId, lessonId));
        mockMvc.perform(get("/api/roadmap-items").param("roadmapId", String.valueOf(roadmapId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        long activityId = idOf(postJson("/api/activities", """
                {"lessonId":%d,"statement":"Print 1 to 3","starterCode":"","referenceSolution":"...",
                 "difficulty":"EASY","status":"VALIDATED"}
                """.formatted(lessonId)), "activityId");

        long testId = idOf(postJson("/api/test-cases",
                "{\"activityId\":%d,\"input\":\"\",\"expectedOutput\":\"1 2 3\",\"hidden\":true}"
                        .formatted(activityId)), "testId");

        String attemptBody = "{\"userId\":%d,\"activityId\":%d,\"code\":\"print(1)\",\"score\":0}"
                .formatted(userId, activityId);
        postJson("/api/attempts", attemptBody);
        String second = postJson("/api/attempts", attemptBody);
        long attemptId = idOf(second, "attemptId");
        org.junit.jupiter.api.Assertions.assertEquals(2,
                objectMapper.readTree(second).get("attemptNo").asInt(), "second attempt should be attemptNo 2");

        postJson("/api/test-results", "{\"attemptId\":%d,\"testId\":%d,\"passed\":false,\"actualOutput\":\"1\"}"
                .formatted(attemptId, testId));

        // Deleting the activity cascades to its test cases, attempts and test results.
        mockMvc.perform(delete("/api/activities/" + activityId)).andExpect(status().isNoContent());
        mockMvc.perform(get("/api/attempts").param("activityId", String.valueOf(activityId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void trackCrudRejectsDuplicateTitle() throws Exception {
        long id = idOf(postJson("/api/tracks",
                "{\"title\":\"Python\",\"language\":\"PYTHON\",\"description\":\"Basics\"}"), "trackId");

        mockMvc.perform(get("/api/tracks/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.language").value("python"));

        mockMvc.perform(post("/api/tracks").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"python\",\"language\":\"python\"}"))
                .andExpect(status().isConflict());

        mockMvc.perform(put("/api/tracks/" + id).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Python 3\",\"language\":\"python\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Python 3"));

        mockMvc.perform(delete("/api/tracks/" + id)).andExpect(status().isNoContent());
        mockMvc.perform(get("/api/tracks/" + id)).andExpect(status().isNotFound());
    }

    @Test
    void lessonCrudFiltersByTrackInPositionOrder() throws Exception {
        long trackId = idOf(postJson("/api/tracks",
                "{\"title\":\"Web Development\",\"language\":\"javascript\"}"), "trackId");
        long second = idOf(postJson("/api/lessons",
                "{\"trackId\":%d,\"title\":\"CSS\",\"position\":2}".formatted(trackId)), "lessonId");
        postJson("/api/lessons", "{\"trackId\":%d,\"title\":\"HTML\",\"position\":1}".formatted(trackId));

        mockMvc.perform(get("/api/lessons").param("trackId", String.valueOf(trackId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title").value("HTML"));

        mockMvc.perform(post("/api/lessons").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"trackId\":999,\"title\":\"X\",\"position\":1}"))
                .andExpect(status().isNotFound());

        mockMvc.perform(put("/api/lessons/" + second).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"trackId\":%d,\"title\":\"CSS Basics\",\"position\":2}".formatted(trackId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("CSS Basics"));

        // Deleting the track cascades to its lessons.
        mockMvc.perform(delete("/api/tracks/" + trackId)).andExpect(status().isNoContent());
        mockMvc.perform(get("/api/lessons/" + second)).andExpect(status().isNotFound());
    }

    @Test
    void unknownForeignKeyReturnsNotFound() throws Exception {
        mockMvc.perform(post("/api/goals").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":999,\"trackId\":999,\"description\":\"x\",\"depth\":\"OVERVIEW\"}"))
                .andExpect(status().isNotFound());
    }

    private String postJson(String url, String json) throws Exception {
        return mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
    }

    private long idOf(String json, String field) throws Exception {
        JsonNode node = objectMapper.readTree(json);
        return node.get(field).asLong();
    }
}
