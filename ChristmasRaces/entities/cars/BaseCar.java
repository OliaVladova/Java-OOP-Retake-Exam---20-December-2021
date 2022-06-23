package christmasRaces.entities.cars;

import christmasRaces.common.ExceptionMessages;

public abstract class BaseCar implements Car{
    private String model;
    private int horsePower;
    private double cubicCentimeters;

    public BaseCar(String model, int horsePower, double cubicCentimeters) {
        this.setModel(model);
        this.setHorsePower(horsePower);
        this.setCubicCentimeters(cubicCentimeters);
    }

    @Override
    public String getModel() {
        return this.model;
    }

    protected void setModel(String model) {
        if (model==null||model.trim().isEmpty()||model.length()<4){
            throw new IllegalArgumentException(String.format(ExceptionMessages.INVALID_MODEL,model,4));
        }else {
            this.model = model;
        }
    }

    @Override
    public int getHorsePower() {
        return this.horsePower;
    }

    private void setHorsePower(int horsePower){
        this.checkHorsePower(horsePower);
        this.horsePower = horsePower;
    }

    protected abstract void checkHorsePower(int horsePower);

    @Override
    public double getCubicCentimeters() {
        return this.cubicCentimeters;
    }

    public void setCubicCentimeters(double cubicCentimeters) {
        this.cubicCentimeters = cubicCentimeters;
    }

    @Override
    public double calculateRacePoints(int laps) {
        return this.cubicCentimeters / this.horsePower * laps;
    }
}
