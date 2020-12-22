package com.jeff.study.reactive;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class TestUtils {

  public static String toJson(Object obj) {
    ObjectMapper mapper = new ObjectMapper();
    String result;
    try {
      result = mapper.writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return result;
  }

  public static <T> T toObject(String str, Class<?> clazz) {
    ObjectMapper mapper = new ObjectMapper();
    T result;
    try {
      result = (T) mapper.readValue(str, clazz);
    } catch (Exception e) {
      throw new RuntimeException("json convert error..", e);
    }
    return result;
  }

}
