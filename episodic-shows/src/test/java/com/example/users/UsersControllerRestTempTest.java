package com.example.users;

import com.google.gson.*;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsersControllerRestTempTest {

  @Autowired
  JdbcTemplate jdbcTemplate;

  @Autowired
  TestRestTemplate template;

  @Before
  public void clearDatabase() {
    this.jdbcTemplate.execute("SET @@foreign_key_checks = 0;");

    String query = "SELECT table_name FROM information_schema.tables WHERE TABLE_SCHEMA IN (SELECT DATABASE())";
    this.jdbcTemplate.queryForList(query).forEach(row -> {
      this.jdbcTemplate.execute("TRUNCATE TABLE " + row.get("TABLE_NAME"));
    });

    this.jdbcTemplate.execute("SET @@foreign_key_checks = 1");
  }

  @Test
  public void testListUsers() {
    // CREATE USER
    JsonObject user = new JsonObject();
    user.addProperty("email", "email_test@email.com");

    String url = "/users";
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    Gson builder = new GsonBuilder().create();
    String jsonString = builder.toJson(user);
    HttpEntity<String> requestEntity = new HttpEntity<>(jsonString, headers);
    ResponseEntity<String> responseEntity = template.exchange(url, HttpMethod.POST, requestEntity, String.class);

    assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));

    // CHECK LIST OF USERS INCLUDES USER
    JsonObject realUser = new JsonParser().parse(responseEntity.getBody()).getAsJsonObject();
    Long realUserId = realUser.get("id").getAsLong();

    HttpEntity<String> entity = new HttpEntity<>(headers);
    responseEntity = template.exchange(url, HttpMethod.GET, entity, String.class);

    assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));

    JsonArray userList = new JsonParser().parse(responseEntity.getBody()).getAsJsonArray();
    Long userIdFromList = userList.get(0).getAsJsonObject().get("id").getAsLong();

    assertThat(realUserId, equalTo(userIdFromList));
  }
}
