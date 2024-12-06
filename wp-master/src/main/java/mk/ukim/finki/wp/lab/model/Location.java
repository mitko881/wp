package mk.ukim.finki.wp.lab.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String capacity;
    private String description;
    // edna lokacija ima povekje eventi
    @OneToMany(mappedBy = "location")
    private List<Event> event;
    public Location(String name, String address, String capacity, String description) {
        this.name = name;
        this.address = address;
        this.capacity = capacity;
        this.description = description;
        this.id = (long) (Math.random() * 1000);
        this.event = new ArrayList<>();
    }

    @Override
    public String toString() {
        return name;
    }
}
