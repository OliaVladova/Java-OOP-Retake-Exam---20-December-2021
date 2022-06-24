package christmasRaces.repositories.interfaces;

import christmasRaces.entities.races.Race;

import java.util.*;

public class RaceRepository implements Repository<Race> {
    private Map<String, Race> models;

    public RaceRepository() {
        this.models = new HashMap<>();
    }

    @Override
    public Race getByName(String name) {
        return this.models.get(name);
    }

    @Override
    public Collection<Race> getAll() {
        return Collections.unmodifiableCollection(this.models.values());
    }

    @Override
    public void add(Race model) {
        this.models.put(model.getName(), model);
    }

    @Override
    public boolean remove(Race model) {
        return this.models.remove(model.getName(), model);
    }
}
