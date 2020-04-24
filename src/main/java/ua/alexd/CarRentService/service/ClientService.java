package ua.alexd.CarRentService.service;

import org.apache.commons.lang3.math.NumberUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ua.alexd.CarRentService.domain.Client;
import ua.alexd.CarRentService.repository.ClientRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getAllClients() {
        return (List<Client>) clientRepository.findAll();
    }

    public Optional<Client> getClientById(String id) {
        try {
            var longId = NumberUtils.createLong(id);
            return clientRepository.findById(longId);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public boolean addNewClient(Client newClient) {
        return saveClient(newClient);
    }

    public boolean updateClient(@NotNull Client updClient) {
        var clientFromDB = getClientById(String.valueOf(updClient.getId()));
        if (clientFromDB.isPresent()) {
            BeanUtils.copyProperties(updClient, clientFromDB.get(), "id");
            return saveClient(clientFromDB.get());
        }
        return false;
    }

    private boolean saveClient(Client saveClient) {
        try {
            clientRepository.save(saveClient);
        } catch (IllegalArgumentException | DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }

    public boolean deleteClient(String id) {
        var deletionClient = getClientById(id);
        if (deletionClient.isPresent()) {
            clientRepository.delete(deletionClient.get());
            return true;
        }
        return false;
    }

    public boolean changeRidesAmount(@NotNull Client client, boolean isAdding) {
        var rentClient = getClientById(String.valueOf(client.getId()));
        if (rentClient.isPresent()) {
            var ridesAmount = rentClient.get().getRidesAmount();
            if (isAdding)
                rentClient.get().setRidesAmount(++ridesAmount);
            else
                rentClient.get().setRidesAmount(--ridesAmount);
            return saveClient(rentClient.get());
        }
        return false;
    }
}