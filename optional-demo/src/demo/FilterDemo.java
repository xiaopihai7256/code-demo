package demo;

import model.Insurance;
import model.optional.Car;
import model.optional.Person;

import java.util.Optional;

/**
 * FilterDemo
 *
 * @author huifei.liu@hand-china.com
 * @date 2018/4/27
 * @Description: TODO
 */
public class FilterDemo {

    public void doSomethingToRenshou(Person person) {
        Optional<Person> optPerson = Optional.ofNullable(person);

        optPerson.flatMap(Person::getCar)
                .flatMap(Car::getInsurance)
                .map(Insurance::getName)
                .filter(insuranceName -> "人寿保险".equals(insuranceName))
                .ifPresent(insurance -> {
                    //如果满足上面的谓词逻辑，则进行一些操作
                    System.out.println("ok， i`m fine!");
                });

    }
}
