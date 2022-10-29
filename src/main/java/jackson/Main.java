package jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
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
        System.out.println("main");

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
    }
}
