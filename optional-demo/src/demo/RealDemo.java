package demo;

import model.Insurance;
import model.Person;
import model.optional.Car;

import java.util.Map;
import java.util.Optional;

/**
 * RealDemo 虽然是demo，但是已经接近实际中的应用了
 *
 * @author huifei.liu@hand-china.com
 * @date 2018/4/27
 * @Description: TODO
 */
public class RealDemo {

    /**
     * 错误的示范
     * @param person
     * @param car
     * @return
     */
    public Optional<Insurance> doSomething(Optional<Person> person, Optional<Car> car) {
        if (person.isPresent() && car.isPresent()) {
            // 我也不知道拿到一个人和车能干啥
            // 假设这里进行了一了一些操作
            return this.findSomething(person.get() , car.get());
        }
        return Optional.empty();
    }

    /**
     * 正确的示范
     * @param person
     * @param car
     * @return
     */
    public Optional<Insurance> doSomething2(Optional<Person> person, Optional<Car> car) {
        return person.flatMap(realPerson -> car.flatMap(realCar-> this.findSomething(realPerson, realCar)));
    }

    public Optional<Insurance> findSomething(Person p, Car car) {
        //这里实现业务逻辑, 十分复杂的业务逻辑
        return Optional.of(new Insurance());
    }


    public Optional<Object> wrap(Map<String, Object> map) {

        Optional<Object> newObject = Optional.ofNullable(map.get("key"));
        return newObject;
    }
}
