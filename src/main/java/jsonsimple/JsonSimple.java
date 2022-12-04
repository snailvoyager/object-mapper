package jsonsimple;

import jsonsimple.dto.Car;
import jsonsimple.dto.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

public class JsonSimple {
    public static void main(String[] args) {
        //Encode
        JSONObject jsonObject = new JSONObject();

        User user = new User("KKK", 34);

        Car car1 = new Car();
        car1.setName("K5");
        car1.setCarNumber("0161");
        car1.setType("SUV");

        Car car2 = new Car();
        car2.setName("K7");
        car2.setCarNumber("1111");
        car2.setType("SEDAN");

        jsonObject.put("name", user.getName());
        jsonObject.put("age", user.getAge());

        JSONObject jsonCar1 = new JSONObject();
        jsonCar1.put("name", car1.getName());
        jsonCar1.put("carNumber", car1.getCarNumber());
        jsonCar1.put("type", car1.getType());

        JSONObject jsonCar2 = new JSONObject();
        jsonCar2.put("name", car2.getName());
        jsonCar2.put("carNumber", car2.getCarNumber());
        jsonCar2.put("type", car2.getType());

        JSONArray jsonArray = new JSONArray();
        jsonArray.add(jsonCar1);
        jsonArray.add(jsonCar2);

        jsonObject.put("cars", jsonArray);

        String jsonString = jsonObject.toJSONString();  //Object -> JSON
        System.out.println(jsonString);

        //Decode
        JSONParser jsonParser = new JSONParser();
        try {
            jsonObject = (JSONObject) jsonParser.parse(jsonString);
            User user1 = new User();
            user1.setName((String)jsonObject.get("name"));
            user1.setAge(Integer.parseInt(jsonObject.get("age").toString()));

            JSONArray jsonArray1 = (JSONArray) jsonParser.parse(jsonObject.get("cars").toString());
            List<Car> carList1 = new ArrayList<>();
            for (Object object : jsonArray1) {
                JSONObject jsonObject1 = (JSONObject) object;
                Car carObject = new Car();
                carObject.setName((String)jsonObject1.get("name"));
                carObject.setCarNumber((String)jsonObject1.get("carNumber"));
                carObject.setType((String)jsonObject1.get("type"));
                carList1.add(carObject);
            }
            user1.setCars(carList1);

            System.out.println(user1);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
