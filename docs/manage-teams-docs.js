document.getElementById("team-prototype-form").addEventListener("submit", (event) => {
    event.preventDefault();

    const name = document.getElementById("team-name").value.trim();
    const sport = document.getElementById("team-sport").value.trim();
    const skill = document.getElementById("team-skill").value;
    const size = document.getElementById("team-size").value;

    const card = document.createElement("article");
    card.className = "prototype-team-card";

    const heading = document.createElement("h3");
    heading.textContent = name;

    const details = document.createElement("p");
    details.textContent = `${sport} • ${skill} • ${size} players`;

    card.append(heading, details);
    document.getElementById("prototype-team-list").append(card);
    event.target.reset();

    document.getElementById("team-prototype-message").textContent =
        "Team added to this prototype. Database persistence is available in the Spring Boot version.";
});
