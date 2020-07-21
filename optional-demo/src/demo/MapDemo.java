package demo;

import model.Car;
import model.Insurance;
import model.Person;

import java.util.Objects;
import java.util.Optional;

/**
 * MapDemo map demo
 *
 * @author huifei.liu@hand-china.com
 * @date 2018/4/27
 * @Description: TODO
 */
public class MapDemo {

    public String getName2(Insurance insurance) {
        if (Objects.nonNull(insurance)) {
            return insurance.getName();
        }
        return null;
    }

    public Optional<String> getName(Insurance insurance) {
        Optional<Insurance> optInsurance = Optional.ofNullable(insurance);
        Optional<String> optName = optInsurance.map(Insurance::getName);
        return optName;
    }

    public static Optional<String> getName(Person person) {
        Optional<Person> optPerson = Optional.ofNullable(person);
        return optPerson
                .map(Person::getCar)
                .map(Car::getInsurance)
                .map(Insurance::getName);
    }

    public static void main(String[] args) {

        Person person = new Person(new Car(new Insurance("123")));
        Optional<String> name = getName(person);
        System.out.println(name);
    }

}
