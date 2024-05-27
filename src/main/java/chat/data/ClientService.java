package chat.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    public Long newCreateClient() {
        Client client = new Client();
        repository.save(client);
        return client.getId();
    }
}
