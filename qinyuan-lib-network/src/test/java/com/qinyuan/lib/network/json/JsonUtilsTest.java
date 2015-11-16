package com.qinyuan.lib.network.json;

import org.assertj.core.data.MapEntry;
import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonUtilsTest {
    @Test
    public void testGetJsonFromJsonp() throws Exception {
        String jsonp = "callback( {\"client_id\":\"101264653\",\"openid\":\"DS12697062EA02345E1288FE40090AA7\"} );";

        assertThat(JsonUtils.parseJsonFromJsonp(jsonp))
                .isEqualTo("{\"client_id\":\"101264653\",\"openid\":\"DS12697062EA02345E1288FE40090AA7\"}");

        assertThat(JsonUtils.parseJsonFromJsonp(" ")).isNull();

        jsonp = "callback( {\"client_id\":\"101264653\",\"openid\":\"DS12697062EA02345E1288FE40090AA7\"} ";
        assertThat(JsonUtils.parseJsonFromJsonp(jsonp)).isNull();
    }

    @Test
    public void testGetJsonObjectFromJsonp() {
        String jsonp = "callback( {\"client_id\":\"101264653\",\"openid\":\"DS12697062EA02345E1288FE40090AA7\"} );";
        Object jsonObject = JsonUtils.parseJsonObjectFromJsonp(jsonp);
        assertThat(jsonObject).isInstanceOf(Map.class);
        assertThat((Map) jsonObject).containsExactly(MapEntry.entry("client_id", "101264653"),
                MapEntry.entry("openid", "DS12697062EA02345E1288FE40090AA7"));

        assertThat(JsonUtils.parseJsonObjectFromJsonp(" ")).isNull();

        jsonp = "callback( {\"client_id\":\"101264653\",\"openid\":\"DS12697062EA02345E1288FE40090AA7\"} ";
        jsonObject = JsonUtils.parseJsonObjectFromJsonp(jsonp);
        assertThat(jsonObject).isNull();
    }
}
