package LeagueFinder.LeagueFinder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return """
        <!DOCTYPE html>
        <html>
        <head>
            <title>League Finder Backend API</title>
            <style>
                body {
                    font-family: Arial, sans-serif;
                    background: #f4f6f8;
                    color: #333;
                    margin: 0;
                    padding: 40px;
                }
                .container {
                    max-width: 700px;
                    margin: auto;
                    background: white;
                    padding: 30px;
                    border-radius: 10px;
                    box-shadow: 0 2px 10px #d1d5db;
                }
                h1 { color: #2563eb; margin-top: 0; }
                h2 { color: #444; margin-top: 30px; }
                li { margin: 12px 0; }
                a { color: #2563eb; text-decoration: none; }
                a:hover { text-decoration: underline; }
                .get {
                    background: #16a34a;
                    color: white;
                    padding: 3px 7px;
                    border-radius: 4px;
                    font-size: 12px;
                    font-weight: bold;
                }
            </style>
        </head>
        <body>
            <div class="container">
                <h1>League Finder Backend API</h1>
                <p>Explore League Finder API endpoints.</p>

                <h2>League Endpoints</h2>
                <ul>
                    <li><span class="get">GET</span> <a href="/leagues">/leagues</a> - Retrieve all leagues</li>
                    <li>POST /leagues - Create a league</li>
                    <li>PUT /leagues/{id} - Update a league</li>
                    <li>DELETE /leagues/{id} - Delete a league</li>
                </ul>
            </div>
        </body>
        </html>
        """;
    }
}