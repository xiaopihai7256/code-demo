package demo;

import model.Insurance;
import model.optional.Car;
import model.optional.Person;

import java.util.Optional;

/**
 * ElseDemo
 *
 * @author huifei.liu@hand-china.com
 * @date 2018/4/27
 * @Description: TODO
 */
public class ElseDemo {

    public String getName(Person person) {
        Optional<Person> optPerson = Optional.ofNullable(person);
        return optPerson.flatMap(Person::getCar)
                .flatMap(Car::getInsurance)
                .map(Insurance::getName)
                .orElse("Unknown");
    }
}
