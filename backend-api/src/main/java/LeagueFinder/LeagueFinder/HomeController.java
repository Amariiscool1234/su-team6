package LeagueFinder.LeagueFinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeController {

     @GetMapping("/")
      public String home() {
        return "The League Finder API is running!";
      }
    
}
