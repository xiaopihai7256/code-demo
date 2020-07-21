package model;

/**
 * Person
 *
 * @author huifei.liu@hand-china.com
 * @date 2018/4/22
 * @Description: 人
 */
public class Person {

    private Car car;

    public Car getCar() {
        return car;
    }

    public Person(Car car) {
        this.car = car;
    }

    public Person() {}
}
