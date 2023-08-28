package orgJson;

import jackson.dto.Car;
import jackson.dto.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class OrgJson {
    public static void main(String[] args) {
        User user = new User("Chris", 34);

        Car car1 = new Car();
        car1.setName("K5");
        car1.setCarNumber("1234");
        car1.setType("SUV");

        Car car2 = new Car();
        car2.setName("K7");
        car2.setCarNumber("1111");
        car2.setType("SEDAN");

        List<Car> carsList = Arrays.asList(car1, car2);
        user.setCars(carsList);

        JSONObject car1Json = new JSONObject(car1);
        System.out.println("## Object -> JSON Car : " + car1Json);
        //{"carNumber":"1234","name":"K5","type":"SUV"}

        JSONObject userJson = new JSONObject(user);
        System.out.println("## Object -> JSON User : " + userJson);
        //{"cars":[{"carNumber":"1234","name":"K5","type":"SUV"},{"carNumber":"1111","name":"K7","type":"SEDAN"}],"name":"Chris","age":34}

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("car1Json", car1Json.toString());        //toString 제거 필요
        System.out.println(jsonObject);
        //{"car1Json":"{\"carNumber\":\"1234\",\"name\":\"K5\",\"type\":\"SUV\"}"}

        String jsonString = jsonObject.toString();
        JSONObject jsonObject2 = new JSONObject(jsonString);
        JSONObject jsonObject3 = jsonObject2.getJSONObject("car1Json");
        System.out.println(jsonObject3);
        //org.json.JSONException JSONObject["car1Json"] is not a JSONObject


        String userJsonString = "{\"cars\":[{\"carNumber\":\"1234\",\"name\":\"K5\",\"type\":\"SUV\"},{\"carNumber\":\"1111\",\"name\":\"K7\",\"type\":\"SEDAN\"}],\"name\":\"Chris\",\"age\":34}";

        JSONObject userObject = new JSONObject(userJsonString);
        System.out.println("## String -> JSONObject : " + userObject);
        JSONArray carsArray = userObject.getJSONArray("cars");
        System.out.println("## String -> JSONArray : " + carsArray);
        //[{"carNumber":"1234","name":"K5","type":"SUV"},{"carNumber":"1111","name":"K7","type":"SEDAN"}]

        JSONObject carObject = carsArray.getJSONObject(0);
        System.out.println("## JSONArray -> JSONObject : " + carObject);
        System.out.println("carNumber : " + carObject.get("carNumber"));
        //{"carNumber":"1234","name":"K5","type":"SUV"}

    }
}
