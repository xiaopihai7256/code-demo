package demo;

import model.Insurance;
import model.optional.Car;
import model.optional.Person;

import java.util.Optional;

/**
 * FlatDemo
 *
 * @author huifei.liu@hand-china.com
 * @date 2018/4/27
 * @Description: TODO
 */
public class FlatDemo {

    /**
     * 一个错误的示范
     * @param person
     * @return
     */
    /*public static Optional<String> getName(Person person) {
        Optional<Person> optperson = Optional.ofNullable(person);
        return optperson
                .map(Person::getCar)
                .map(Car::getInsurance)
                .map(Insurance::getName);
    }*/

    public Optional<String> getName2(Person person) {
        Optional<Person> optPerson = Optional.ofNullable(person);

        return optPerson.flatMap(Person::getCar)
                .flatMap(Car::getInsurance)
                .map(Insurance::getName);
    }

    /**
     * 一个错误的示范2
     * @return
     */
    /*public Optional<String> getName3() {
        Optional<Optional<Optional<Person>>> opt3Person = Optional.of(Optional.of(Optional.of(new Person())));
        opt3Person.flatMap(Person::getCar)
                .flatMap(Car::getInsurance)
                .map(Insurance::getName);
    }*/

}
