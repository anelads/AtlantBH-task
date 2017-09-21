package test;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static com.jayway.restassured.RestAssured.get;
import org.json.JSONArray;
import org.json.JSONException;

import com.jayway.restassured.response.Response;

import java.sql.SQLException;


public class RestAPITest {

    Response resp;
    JSONObject object;

    @BeforeClass
    public  void beforeMethod() throws SQLException, JSONException {
        resp = get("https://maps.googleapis.com/maps/api/geocode/json?address=Bosnia%20and%20Herzegovina");
        object = new JSONObject(resp.asString());
    }


    @Test(description = "validate response status")
    public void validateStatus() throws JSONException {
        String status = object.getString("status");
        Assert.assertEquals(status, "OK");
    }

    @Test(dependsOnMethods = "validateStatus",description = "validate location coordinates values")
    public void validateLocation() throws JSONException {

        JSONArray array = new JSONArray(object.getString("results"));
        for (int i = 0; i < array.length(); i++) {
            JSONObject rec = array.getJSONObject(i);

//            JSONArray address_components = rec.getJSONArray("address_components");
//            System.out.println(address_components.get(0));
//            System.out.println(address_components.get(1));
//            System.out.println(address_components.get(2));
//            System.out.println(address_components.get(3));
//            System.out.println(address_components.get(4));
//            System.out.println(address_components.get(5));
//            System.out.println(address_components.get(6));
//            System.out.println(address_components.get(7));

//            String formatted_address = rec.getString("formatted_address");
//            System.out.println(formatted_address);

            JSONObject geometry = rec.getJSONObject("geometry");
            Double lng = Double.valueOf(geometry.getJSONObject("location").getString("lng"));
            Double lat = Double.valueOf(geometry.getJSONObject("location").getString("lat"));
            Assert.assertTrue((lng == 17.679076) && (lat  == 43.915886));

            System.out.println(lng);
            System.out.println(lat) ;

        }


    }


}