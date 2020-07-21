package demo;

import model.Car;
import model.Insurance;
import model.Person;

import java.util.Optional;

/**
 * Demo1 关于null和NPE的问题
 *
 * @author huifei.liu@hand-china.com
 * @date 2018/4/22
 * @Description: TODO
 */
public class NullDemo {

    /**
     * 获取人对应的车的保险的名称
     * @param person
     * @return
     */
    public String getName(Person person) {
        return person.getCar().getInsurance().getName();
    }

    /**
     * 简单的防御式检查
     * @param person
     * @return
     */
    public String getName2(Person person) {
        if (person != null) {
            Car car = person.getCar();
            if (car != null) {
                Insurance insurance = car.getInsurance();
                if (insurance != null) {
                    return insurance.getName();
                }
            }
        }
        return "Unknown";
    }

    /**
     * 获取name3
     * @param person
     * @return
     */
    public String getName3(Person person) {
        if (person == null) {
            return "Unknown";
        }
        Car car = person.getCar();
        if (car == null) {
            return "Unknown";
        }
        Insurance insurance = car.getInsurance();
        if (insurance == null) {
            return "Unknown";
        }
        return insurance.getName();
    }

    /**
     * 获取car
     * @param person
     * @return
     */
    public Car getCar(Optional<Person> person) {

        if (person.isPresent()) {
            return person.get().getCar();
        }
        return null;

    }

    public Optional<Car> getCar2(Optional<Person> person) {
        return person.map(Person::getCar);
    }
}
