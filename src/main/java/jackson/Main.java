package jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jackson.dto.Car;
import jackson.dto.User;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        User user = new User("KKK", 34);

        Car car1 = new Car();
        car1.setName("K5");
        car1.setCarNumber("0161");
        car1.setType("SUV");

        Car car2 = new Car();
        car2.setName("K7");
        car2.setCarNumber("1111");
        car2.setType("SEDAN");

        List<Car> carsList = Arrays.asList(car1, car2);
        user.setCars(carsList);

        String json = objectMapper.writeValueAsString(user);    //Object -> JSON
        System.out.println(json);

        //text -> Object
        //Object Mapper는 default 생성자 필요
        var objectUser = objectMapper.readValue(json, User.class);
        System.out.println(objectUser);
        System.out.println("User.cars : " + objectUser.getCars());

        JsonNode jsonNode = objectMapper.readTree(json);        //JSON -> Object
        String _name = jsonNode.get("name").asText();
        int _age = jsonNode.get("age").asInt();
        System.out.println("name : " + _name);
        System.out.println("age : " + _age);

        String _carList = jsonNode.get("cars").asText();    //null
        System.out.println(_carList);

        JsonNode cars = jsonNode.get("cars");       // 객체에 접근할 때는 노드 객체로 불러와서
        ArrayNode arrayNode = (ArrayNode)cars;      // ArrayNode 변환
        List<Car> _cars = objectMapper.convertValue(arrayNode, new TypeReference<List<Car>>() {});      // object mapper 로 형변환
        System.out.println(_cars);

        ObjectNode objectNode = (ObjectNode) jsonNode;
        objectNode.put("name", "ghm");      //json 값 변경
        objectNode.put("age", 37);
        System.out.println(objectNode.toPrettyString());

        //ObjectMapper 설정
        ObjectMapper objectMapper2 = new ObjectMapper();
        objectMapper2.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        Car car3 = new Car();
        car3.setName("GV80");
        car3.setCarNumber("");
        System.out.println("car3 : " + car3);   //String carNumber = "", String type = null, int createdYear = 0

        String car3Json = objectMapper2.writeValueAsString(car3);
        System.out.println("car3Json : " + car3Json);

        objectMapper2.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);   //객체에 없는 Json 필드는 역직렬화 무시
        String json2 = "{\"name\":\"GV80\",\"unknown\":\"9999\"}";
        Car car3Deserialization = objectMapper2.readValue(json2, Car.class);  //String carNumber = null, String type = null, int createdYear = 0
        System.out.println("car3Deserialization : " + car3Deserialization);
    }
}
