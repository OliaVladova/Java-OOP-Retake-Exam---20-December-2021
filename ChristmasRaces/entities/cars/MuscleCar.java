package christmasRaces.entities.cars;

import christmasRaces.common.ExceptionMessages;

public class MuscleCar extends BaseCar{
    private static final double CUBBIC_CM = 5000;
    private static final double MIN_HORSEPOWER = 400;
    private static final double MAX_HORSEPOWER = 600;

    public MuscleCar(String model, int horsePower) {
        super(model,horsePower, CUBBIC_CM);
    }


    @Override
    protected void checkHorsePower(int horsePower) {
        if (horsePower<MIN_HORSEPOWER || horsePower>MAX_HORSEPOWER){
            throw new IllegalArgumentException(String.format(ExceptionMessages.INVALID_HORSE_POWER,horsePower));
        }
    }
}
