package com.test.geodata.controller;

import com.test.geodata.domain.GeodataStructure;
import com.test.geodata.domain.Geojson;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
public class GeodataController {
    private Map<String,List<GeodataStructure>> cash = new HashMap<>();

    CloseableHttpClient client = HttpClientBuilder.create().build();

    @RequestMapping(value = "/osm")
    public Object getRegion(@RequestParam String region) throws IOException {
        if(cash.get(region)!=null)return cash.get(region);//кэширование;

        //клиент запрашивает данные и преобразует их в формат String;
        CloseableHttpResponse response = client.execute(new HttpGet("https://nominatim.openstreetmap.org/search?state="+ URLEncoder.encode(region, StandardCharsets.UTF_8.toString())+"&country=russia&format=json&polygon_geojson=1"));
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity);

        //Создаём список на основе структуры json
        List<GeodataStructure> geodataStructures = new ArrayList<>();

        //Но json заключён в массив и нам нужно массив рапарсить чтобы достать json;
        JSONArray array = new JSONArray(result);
        for (int i = 0; i < array.length(); i++) {
            JSONObject jsonobjectFirst = array.getJSONObject(i);//работаем с каждым элементом массива в JsonObject;
            double lat = jsonobjectFirst.getDouble("lat");
            double lon = jsonobjectFirst.getDouble("lon");
            String displayName = jsonobjectFirst.getString("display_name");

            //Второй json. Не получилось создать сущность этого класса в GeodataStructure, но может и не нужно это,
            // но как-то не логично;
            List<Geojson> geojsons = new ArrayList<>();

            //Из второга json парсим параметры;
            JSONObject jsonobjectSecond = jsonobjectFirst.getJSONObject("geojson");
            String type = jsonobjectSecond.getString("type");

            //Но коордиаты во втором JsonObject это многомерный массив. Данные были видны при формате BigDecimal,
            //мне нужно добраться до полигонов, чтобы найти наибольший, поэтому BigDecimal не подходит и нужно парсить
            // дальше;
//            JSONArray arrayCoordinates = new JSONObject("geojson").getJSONArray("coordinates").getJSONArray(0);
//            System.out.println(arrayCoordinates.get(0).toString());

            //Добавляем geodataStructure и geojson в их списки, как добавить coorditates пока не ясно;
            geodataStructures.add(new GeodataStructure(lat,lon,displayName));
            geojsons.add(new Geojson(type));

            //Вывод GeodataStructure;
            System.out.println(lat + " " + lon + " " + displayName);
            //Вывод Geojson, но только в консоль;
            System.out.println(type);
        }

        cash.put(region,geodataStructures);//кэширование;
        return geodataStructures;
    }
}