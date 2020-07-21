package model;

/**
 * Car
 *
 * @author huifei.liu@hand-china.com
 * @date 2018/4/22
 * @Description: 车
 */
public class Car {

    private Insurance insurance;

    public Insurance getInsurance() {
        return insurance;
    }

    public Car(Insurance insurance) {
        this.insurance = insurance;
    }

    public Car() {}
}
