package model.optional;

import java.util.Optional;

/**
 * Person
 *
 * @author huifei.liu@hand-china.com
 * @date 2018/4/23
 * @Description: Optional 人
 */
public class Person {

    private Optional<Car> car;

    public Optional<Car> getCar() {
        return car;
    }
}
