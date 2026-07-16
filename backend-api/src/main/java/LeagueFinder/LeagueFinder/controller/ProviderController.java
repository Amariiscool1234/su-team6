package LeagueFinder.LeagueFinder.controller;

import LeagueFinder.LeagueFinder.entity.Provider;
import LeagueFinder.LeagueFinder.service.ProviderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/providers")
@CrossOrigin(origins = "*")
public class ProviderController {

    private final ProviderService providerService;

    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @GetMapping
    public List<Provider> getAllProviders() {
        return providerService.getAllProviders();
    }

    @GetMapping("/providers/{id}")
    public ResponseEntity<?> getProviderById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(
                    providerService.getProviderById(id)
            );
        } catch (RuntimeException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createProvider(
            @RequestBody Provider provider) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(providerService.createProvider(provider));
        } catch (RuntimeException exception) {
            return ResponseEntity
                    .badRequest()
                    .body(exception.getMessage());
        }
    }
     

    @PutMapping("/providers/{id}")
    public ResponseEntity<?> updateProvider(
            @PathVariable Long id,
            @RequestBody Provider provider
    ) {
        try {
            return ResponseEntity.ok(
                    providerService.updateProvider(id, provider)
            );
        } catch (RuntimeException exception) {
            return ResponseEntity
                    .badRequest()
                    .body(exception.getMessage());
        }
    }

    @DeleteMapping("/providers/{id}")
    public ResponseEntity<Void> deleteProvider(@PathVariable Long id) {
        try {
            providerService.deleteProvider(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}