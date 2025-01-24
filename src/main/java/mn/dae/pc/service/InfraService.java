package mn.dae.pc.service;

import mn.dae.pc.model.Infra;
import mn.dae.pc.repository.InfraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class InfraService {

    @Autowired
    private InfraRepository infraRepository;

    public List<Infra> findAll() {
        return infraRepository.findAll();
    }

    public Optional<Infra> findById(Long id) {
        return infraRepository.findById(id);
    }

    public Infra save(Infra infra) {
        return infraRepository.save(infra);
    }

    public void deleteById(Long id) {
        infraRepository.deleteById(id);
    }

    public List<Infra> findByTypeAndName(String type, String name) {
        return infraRepository.findByTypeAndName(type, name);
    }
}
