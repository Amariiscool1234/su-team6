package LeagueFinder.LeagueFinder.service;

import LeagueFinder.LeagueFinder.entity.Provider;
import LeagueFinder.LeagueFinder.repository.ProviderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderService {

    private final ProviderRepository providerRepository;

    public ProviderService(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    public List<Provider> getAllProviders() {
        return providerRepository.findAll();
    }

    public Provider getProviderById(Long id) {
        return providerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Provider not found"));
    }

    public Provider createProvider(Provider provider) {
        if (providerRepository.existsByEmail(provider.getEmail())) {
            throw new RuntimeException(
                    "A provider with this email already exists"
            );
        }

        return providerRepository.save(provider);
    }

    public Provider updateProvider(Long id, Provider updatedProvider) {
        Provider provider = getProviderById(id);

        provider.setName(updatedProvider.getName());
        provider.setEmail(updatedProvider.getEmail());
        provider.setPhoneNumber(updatedProvider.getPhoneNumber());
        provider.setOrganizationName(
                updatedProvider.getOrganizationName()
        );
        provider.setSportType(updatedProvider.getSportType());
        provider.setLocation(updatedProvider.getLocation());

        return providerRepository.save(provider);
    }

    public void deleteProvider(Long id) {
        if (!providerRepository.existsById(id)) {
            throw new RuntimeException("Provider not found");
        }

        providerRepository.deleteById(id);
    }
}