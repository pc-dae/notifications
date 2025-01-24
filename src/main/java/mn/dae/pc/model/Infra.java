package mn.dae.pc.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Infra {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    private Long id;
    @JsonProperty("type")
    private String type;
    @JsonProperty("name")
    private String name;

    // for JPA only, no use
    public Infra() {
    }

    public Infra(String type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Infra{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", name=" + name +
                '}';
    }

    public void setFields(Infra updates) {
        if (updates.getName() != null) {
            setName(updates.getName());
        }
        if (updates.getType() != null) {
            setType(updates.getType());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
