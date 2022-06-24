package christmasRaces.repositories.interfaces;

import christmasRaces.entities.drivers.Driver;

import java.util.*;

public class DriverRepository implements Repository<Driver> {
    private Map<String, Driver> models;

    public DriverRepository() {
        this.models = new HashMap<>();
    }

    @Override
    public Driver getByName(String name) {
        return this.models.get(name);
    }

    @Override
    public Collection<Driver> getAll() {
        return Collections.unmodifiableCollection(this.models.values());
    }

    @Override
    public void add(Driver model) {
        this.models.put(model.getName(), model);
    }

    @Override
    public boolean remove(Driver model) {
        return this.models.remove(model.getName()) != null;
    }
}
