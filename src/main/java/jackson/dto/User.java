package jackson.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class User {
    private String name;
    private int age;
    private List<Car> cars;

    public User() {
        this.name = null;
        this.age = 0;
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
