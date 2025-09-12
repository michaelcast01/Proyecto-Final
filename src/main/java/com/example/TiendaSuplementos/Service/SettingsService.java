package com.example.TiendaSuplementos.Service;

import com.example.TiendaSuplementos.Model.Settings;
import com.example.TiendaSuplementos.Repository.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SettingsService {

    @Autowired
    private SettingsRepository repository;

    public List<Settings> get() { return repository.findAll();}
    public Optional<Settings> getById(Long id) {
        return repository.findById(id);
    }
    public Settings save(Settings settings) { return repository.save(settings);}
    public Settings update(Long id, Settings settings) {
        return repository.findById(id)
                .map(existing -> {
                    if (settings.getName() != null) {
                        existing.setName(settings.getName());
                    }
                    if (settings.getNickname() != null) {
                        existing.setNickname(settings.getNickname());
                    }
                    if (settings.getCity() != null) {
                        existing.setCity(settings.getCity());
                    }
                    if (settings.getPhone() != null) {
                        existing.setPhone(settings.getPhone());
                    }
                    if (settings.getAddress() != null) {
                        existing.setAddress(settings.getAddress());
                    }
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Settings not found"));
    }

    public void delete(Long id) { repository.deleteById(id);}

}
