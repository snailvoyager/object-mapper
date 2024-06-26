package netsf;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jackson.dto.Car;
import jackson.dto.User;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class NetSfJson {
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

        JSONObject car1Json = JSONObject.fromObject(car1);
        System.out.println("## Object -> JSON Car : " + car1Json);

        JSONObject userJson = JSONObject.fromObject(user);
        System.out.println("## Object -> JSON User : " + userJson);

        String jsonString = userJson.toString();
        System.out.println("## JSON String : " + jsonString);

        JSONObject object2 = JSONObject.fromObject(jsonString);
        System.out.println("## String -> JSONObject : " + object2);

        JSONArray carListJson = object2.getJSONArray("cars");
        System.out.println("## JSONArray Cars : " + carListJson);

        JSONObject carObject = carListJson.getJSONObject(0);    //첫번째 인덱스
        System.out.println("## JSONObject Car : " + carObject);

        Car carClass = new Car();
        carClass.setCarNumber(carObject.getString("carNumber"));
        carClass.setName((String) carObject.get("name"));
        carClass.setType(carObject.getString("type"));
        System.out.println("## JSONObject -> Class : " + carClass);

        Car carBean = (Car) JSONObject.toBean(carObject, Car.class);
        System.out.println(carBean);

        JSONObject object = new JSONObject();
        object.put("carNumber", "0987");
        object.put("name", "{}");       //문자열 "{}"을 빈 객체로 변환
        System.out.println("## JSON : " + object);

        try {
            Car jsonToCar = (Car) JSONObject.toBean(object, Car.class);
            log.info(jsonToCar.toString());
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            throw e;
        }
        //Jackson Text Json -> Object
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);   //Json에 없는 필드는 역직렬화 무시

        try {
            Car jacksonCar = objectMapper.readValue(object.toString(), Car.class);
            log.info(jacksonCar.toString());
        } catch (JsonProcessingException e) {
            log.info(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
