package christmasRaces.core;

import christmasRaces.common.ExceptionMessages;
import christmasRaces.common.OutputMessages;
import christmasRaces.core.interfaces.Controller;
import christmasRaces.entities.cars.Car;
import christmasRaces.entities.cars.MuscleCar;
import christmasRaces.entities.cars.SportsCar;
import christmasRaces.entities.drivers.Driver;
import christmasRaces.entities.drivers.DriverImpl;
import christmasRaces.entities.races.Race;
import christmasRaces.entities.races.RaceImpl;
import christmasRaces.repositories.interfaces.Repository;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerImpl implements Controller {
    private Repository<Driver> driverRepository;
    private Repository<Car> carRepository;
    private Repository<Race> raceRepository;


    public ControllerImpl(Repository<Driver> driverRepository, Repository<Car> carRepository, Repository<Race> raceRepository) {
        this.driverRepository = driverRepository;
        this.carRepository = carRepository;
        this.raceRepository = raceRepository;
    }

    @Override
    public String createDriver(String driver) {

        Driver existingDiver = this.driverRepository.getByName(driver);
        if (existingDiver != null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.DRIVER_EXISTS, driver));
        }
        Driver createdDiver = new DriverImpl(driver);

        this.driverRepository.add(createdDiver);
        return String.format(OutputMessages.DRIVER_CREATED, driver);
    }

    @Override
    public String createCar(String type, String model, int horsePower) {
      Car existing = this.carRepository.getByName(model);
        if (existing!=null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.CAR_EXISTS, model));
        }
        Car car = null;
        switch (type) {
            case "Muscle":
                car = new MuscleCar(model, horsePower);
                break;
            case "Sports":
                car = new SportsCar(model, horsePower);
                break;
        }

        this.carRepository.add(car);
        return String.format(OutputMessages.CAR_CREATED, type, model);
    }

    @Override
    public String addCarToDriver(String driverName, String carModel) {
        Driver driver = this.driverRepository.getByName(driverName);
        Car car = this.carRepository.getByName(carModel);
        if (driver == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.DRIVER_NOT_FOUND, driverName));
        }
        if (car == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.CAR_NOT_FOUND, carModel));
        }
        driver.addCar(car);
        return String.format(OutputMessages.CAR_ADDED, driverName, carModel);
    }

    @Override
    public String addDriverToRace(String raceName, String driverName) {
        Race race = this.raceRepository.getByName(raceName);
        Driver driver = this.driverRepository.getByName(driverName);
        if (race == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.RACE_NOT_FOUND, raceName));
        }
        if (driver == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.DRIVER_NOT_FOUND, driverName));
        }
        race.addDriver(driver);
        return String.format(OutputMessages.DRIVER_ADDED, driverName, raceName);
    }

    @Override
    public String startRace(String raceName) {
        Race race = this.raceRepository.getByName(raceName);
        if (race == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.RACE_NOT_FOUND, raceName));
        }
        Collection<Driver> drivers = race.getDrivers();
        if (drivers.size() < 3) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.RACE_INVALID, raceName, 3));
        }
        int laps = race.getLaps();
        List<Driver> topDrivers = drivers.stream().sorted((driver1, driver2) -> Double.compare(driver2.getCar().calculateRacePoints(laps), driver1.getCar().calculateRacePoints(laps)))
                .limit(3)
                .collect(Collectors.toList());
        this.raceRepository.remove(race);
        Driver drierFirst = topDrivers.get(0);
        Driver drierSecond = topDrivers.get(1);
        Driver drierThird = topDrivers.get(2);
        StringBuilder builder = new StringBuilder();
        builder.append(String.format(OutputMessages.DRIVER_FIRST_POSITION, drierFirst.getName(), race.getName())).append(System.lineSeparator())
                .append(String.format(OutputMessages.DRIVER_SECOND_POSITION, drierSecond.getName(), race.getName())).append(System.lineSeparator())
                .append(String.format(OutputMessages.DRIVER_THIRD_POSITION, drierThird.getName(), race.getName())).append(System.lineSeparator());
        return builder.toString().trim();
    }

    @Override
    public String createRace(String name, int laps) {
        Race race = new RaceImpl(name, laps);
        Race fromRepository = this.raceRepository.getByName(name);
        if (fromRepository != null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.RACE_EXISTS, name));
        }
        this.raceRepository.add(race);
        return String.format(OutputMessages.RACE_CREATED, name);
    }
}
