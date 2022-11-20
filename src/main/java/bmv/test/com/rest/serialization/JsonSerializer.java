package bmv.test.com.rest.serialization;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;

public final class JsonSerializer {

  public static final String DATETIME_SECONDS_FORMAT_PATTERN =
      "yyyy-MM-dd'T'HH:mm:ssZ";

  private final ObjectMapper simpleMapper;

  public JsonSerializer() {
    simpleMapper = init(new ObjectMapper());
  }

  public static String emptyJson() {
    return "{}";
  }

  private static ObjectMapper init(ObjectMapper mapper) {
    return mapper
        .setSerializationInclusion(JsonInclude.Include.NON_ABSENT)
        .setDefaultPropertyInclusion(JsonInclude.Include.NON_ABSENT)
        .registerModule(new JavaTimeModule())
        .registerModule(new Jdk8Module())
        .registerModule(new ParameterNamesModule())
        .configure(FAIL_ON_EMPTY_BEANS, false)
        .configure(FAIL_ON_UNKNOWN_PROPERTIES, false)
        .configure(WRITE_DATES_AS_TIMESTAMPS, false)
        .setDateFormat(
            new SimpleDateFormat(DATETIME_SECONDS_FORMAT_PATTERN));
  }

  public String toJson(Object object) {
    try {
      return simpleMapper.writeValueAsString(object);
    } catch (Exception jpe) {
      throw new RuntimeException(jpe);
    }
  }

  public byte[] toJsonAsBytes(Object object) {
    try {
      return simpleMapper.writeValueAsBytes(object);
    } catch (Exception jpe) {
      throw new RuntimeException(jpe);
    }
  }

  public <T> T fromJson(String jsonString, Class<T> clazz) {
    try {
      return simpleMapper.readValue(jsonString, clazz);
    } catch (IOException jpe) {
      throw new RuntimeException(jpe);
    }
  }

  public <T> T fromJson(InputStream is, Class<T> clazz) {
    try {
      return simpleMapper.readValue(is, clazz);
    } catch (IOException jpe) {
      throw new RuntimeException(jpe);
    }
  }

  public <T> T fromJsonWithTypeReference(String jsonString,
      JavaType resolvedType) {
    try {
      return simpleMapper.readValue(jsonString, resolvedType);
    } catch (IOException jpe) {
      throw new RuntimeException(jpe);
    }
  }

  public <T> List<T> fromJsonToList(String jsonInput, Class<T> clazz) {
    try {
      return simpleMapper.readValue(jsonInput,
          simpleMapper.getTypeFactory()
              .constructCollectionType(List.class, clazz));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public <T> List<T> fromJsonToList(String jsonInput,
      JavaType javaType) {
    try {
      return simpleMapper.readValue(jsonInput,
          simpleMapper.getTypeFactory()
              .constructCollectionType(List.class, javaType));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public ObjectMapper getSimpleMapper() {
    return simpleMapper;
  }
}
