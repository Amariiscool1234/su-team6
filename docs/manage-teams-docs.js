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

    const deleteButton = createDeleteButton();

    card.append(heading, details, deleteButton);
    document.getElementById("prototype-team-list").append(card);
    event.target.reset();

    document.getElementById("team-prototype-message").textContent =
        "Team added successfully.";
});

function createDeleteButton() {
    const button = document.createElement("button");
    button.type = "button";
    button.textContent = "Delete Team";
    button.className = "danger-button";
    button.addEventListener("click", () => {
        if (window.confirm("Are you sure you want to delete this team?")) {
            button.closest(".prototype-team-card").remove();
            document.getElementById("team-prototype-message").textContent =
                "Team deleted successfully.";
        }
    });
    return button;
}

document.querySelectorAll(".prototype-team-card").forEach((card) => {
    card.append(createDeleteButton());
});
