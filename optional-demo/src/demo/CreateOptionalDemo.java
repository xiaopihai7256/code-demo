package demo;

import model.optional.Car;

import java.util.Optional;

/**
 * CreateOptionalDemo 创建Optional的demo
 *
 * @author huifei.liu@hand-china.com
 * @date 2018/4/27
 * @Description: 创建Optional Demo
 */
public class CreateOptionalDemo {

    /**
     * 创建Optional的三种方式
     */
    public void createOptional() {

        // 创建一个空的Optional
        Optional<Car> optCar = Optional.empty();
        // 依据非空的对象，创建一个Optional对象，如果传度的对象为空，会报NPE
        Optional<Car> optCar1 = Optional.of(new Car());
        // 依据允许为空的对象，创建一个Optional对象
        Optional<Car> optCar2 = Optional.ofNullable(null);

    }
}
