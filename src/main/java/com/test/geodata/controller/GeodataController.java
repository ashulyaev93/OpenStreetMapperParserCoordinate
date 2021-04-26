package com.test.geodata.controller;

import com.test.geodata.model.GeoData;
import com.test.geodata.model.LatLon;
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
    private Map<String,List<GeoData>> cash = new HashMap<>();

    CloseableHttpClient client = HttpClientBuilder.create().build();

    @RequestMapping(value = "/osm")
    public List<GeoData> getRegion(@RequestParam String region /*,@RequestParam String district*/) throws IOException {

        //Условие кэширования;
        if(cash.get(region)!=null)return cash.get(region);

        //TODO: сделать ещё один вид запроса
//        if(district == null) System.out.println(true);
//        else System.out.println(false);

        //Клиент запрашивает данные и преобразует их в формат String;
        CloseableHttpResponse response = client.execute(new HttpGet("https://nominatim.openstreetmap.org/search?state="+ URLEncoder.encode(region, StandardCharsets.UTF_8.toString())+"&country=russia&format=json&polygon_geojson=1"));
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity);

        //Создаём список на основе структуры json
        List<GeoData> geoDataList = new ArrayList<>();

        JSONArray array = new JSONArray(result);

        if(array == null || array.length()<1) return null;

        //Из JSONObject, который находится в массиве, достаём параметры: название региона и координаты его центра;
        for (int i = 0; i < array.length(); i++) {

            JSONObject jsonobjectFirst = array.getJSONObject(i);//работаем с каждым элементом массива в JsonObject;

            double lat = jsonobjectFirst.getDouble("lat");
            double lon = jsonobjectFirst.getDouble("lon");
            String displayName = jsonobjectFirst.getString("display_name");

            //В первом JSONObject содержится ещё один JSONObject "geojson", разбираем его, в нём находятся координаты;
            JSONObject geojson = jsonobjectFirst.getJSONObject("geojson");

            String type = geojson.getString("type");

            GeoData geoData = new GeoData(lat, lon, displayName);

            JSONArray latLons = null;

            if(type.equals ("MultiPolygon")){

                latLons = geojson.getJSONArray("coordinates")
                    .getJSONArray(0)
                    .getJSONArray(0);
            } else{

                latLons = geojson.getJSONArray("coordinates")
                    .getJSONArray(0);
            }

            for(int j = 0; j<latLons.length(); j++){

                //Парсим массив координат;
                JSONArray jsonobjectSecond = latLons.getJSONArray(j);

                double lat1 = jsonobjectSecond.getDouble(0);
                double lon1 = jsonobjectSecond.getDouble(1);

                geoData.addLatLon(new LatLon(lat1, lon1));
            }

            geoDataList.add(geoData);
        }

        cash.put(region,geoDataList);
        return geoDataList;
    }
}