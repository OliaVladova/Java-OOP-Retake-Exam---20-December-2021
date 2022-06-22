package garage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GarageTests {
    private Car carInGarage;
    private Car carToAdd;
    private Garage garage;

    @Before
    public void setUp(){
        this.carInGarage = new Car("Lamborghini",520,15);
        this.carToAdd = new Car("Tesla",320,10);
        this.garage = new Garage();
        this.garage.addCar(carInGarage);
    }
     @Test(expected = UnsupportedOperationException.class)
    public void testShouldThrowExceptionWhenModifying(){
        this.garage.getCars().clear();
     }
    @Test
    public  void testShouldReturnAllCarsWithMaxSpeedAbove(){
        this.garage.addCar(carToAdd);
        List<Car> returned = this.garage.findAllCarsWithMaxSpeedAbove(200);
        List<Car> expected = new ArrayList<>();
        expected.add(carInGarage);
        expected.add(carToAdd);
        Assert.assertEquals(expected,returned);
    }
    @Test
    public  void testShouldAddCarCorrectly(){
        this.garage.addCar(carToAdd);
        int size = this.garage.getCount();
        Assert.assertEquals(2,size);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testShouldThrowExceptionWhenCarIsNull(){
        this.garage.addCar(null);
    }
    @Test
    public  void testShouldReturnTheMostExpensiveCar(){
        this.garage.addCar(carToAdd);
        Car returned = this.garage.getTheMostExpensiveCar();
        Assert.assertEquals(carInGarage,returned);
    }
    @Test
    public  void testShouldReturnAllCarsFromBrand(){
        this.garage.addCar(carToAdd);
        Car newCar = new Car("Tesla",415,9);
        this.garage.addCar(newCar);
       List<Car> returned= this.garage.findAllCarsByBrand("Tesla");
        List<Car> expected = new ArrayList<>();
        expected.add(carToAdd);
        expected.add(newCar);
        Assert.assertEquals(expected,returned);
    }

}