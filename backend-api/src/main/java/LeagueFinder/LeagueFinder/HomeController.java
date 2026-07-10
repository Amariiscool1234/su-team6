package LeagueFinder.LeagueFinder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return """
        <html>
        <head>
            <title>League Finder Backend API</title>
        </head>
        <body>
            <h1>League Finder Backend API</h1>
            <p>Explore League Finder API endpoints.</p>

            <h2>League Endpoints</h2>
            <ul>
                <li>GET /leagues - Retrieve all leagues</li>
                <li>POST /leagues - Create a league</li>
                <li>PUT /leagues/{id} - Update a league</li>
                <li>DELETE /leagues/{id} - Delete a league</li>
            </ul>

        </body>
        </html>
        """;
    }
}