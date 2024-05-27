package chat.data;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Client {

    @Id
    private Long id;

    public Long getId() {
        return id;
    }
}
