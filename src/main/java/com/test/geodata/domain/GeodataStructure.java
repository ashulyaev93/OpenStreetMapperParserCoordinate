package com.test.geodata.domain;

/**Вид json object
 * [
 * {
 * "place_id":258871342,
 * "licence":"Data © OpenStreetMap contributors, ODbL 1.0. https://osm.org/copyright",
 * "osm_type":"relation",
 * "osm_id":1075831,
 * "boundingbox":["49.8012132","61.663234","41.7764767","61.6897852"],
 * "lat":"55.7359267","lon":"50.77496358049139",
 * "display_name":"Приволжский федеральный округ, Россия",
 * "class":"boundary",
 * "type":"administrative",
 * "importance":0.5388848335696471,
 * "icon":"https://nominatim.openstreetmap.org/ui/mapicons//poi_boundary_administrative.p.20.png",
 * "geojson":{
 *           "type":"MultiPolygon",
 *           "coordinates":[
 *                         [
 *                         [
 *                         [41.7764767,55.0986437],[41.7838358,55.0905618]
 *                         ]
 *                         ]
 *                         ]
 *            }
 *  }
 *  ]*/

public class GeodataStructure {
    public double lat;
    public double lon;
    public String displayName;
    //сюда бы включить Geojson;

    public GeodataStructure (double latitude, double longitude, String displayName) {
        this.lat = latitude;
        this.lon = longitude;
        this.displayName = displayName;

    }
}
