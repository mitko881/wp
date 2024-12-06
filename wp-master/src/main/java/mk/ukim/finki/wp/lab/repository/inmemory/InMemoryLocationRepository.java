package mk.ukim.finki.wp.lab.repository.inmemory;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Location;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InMemoryLocationRepository {
    public List<Location> findAll() {
        return DataHolder.locations;
    }
}
