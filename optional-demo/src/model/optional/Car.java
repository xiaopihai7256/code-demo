package model.optional;

import model.Insurance;

import java.util.Optional;

/**
 * Car
 *
 * @author huifei.liu@hand-china.com
 * @date 2018/4/23
 * @Description: Optional 车
 */
public class Car {

    private Optional<Insurance> insurance;

    public Optional<Insurance> getInsurance() {
        return insurance;
    }
}
