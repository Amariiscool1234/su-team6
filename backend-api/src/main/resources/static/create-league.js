const API_BASE_URL = "";
const leagueForm = document.getElementById("league-form");
const leagueMessage = document.getElementById("league-form-message");
const leagueSubmitButton = document.getElementById("league-submit-button");
const editLeagueId = new URLSearchParams(window.location.search).get("id");

function buildDescription() {
    const details = [
        document.getElementById("league-description").value.trim(),
        `Skill level: ${document.getElementById("skill-level").value}`,
        `Season: ${document.getElementById("start-date").value} through ${document.getElementById("end-date").value}`,
        `Registration fee: ${document.getElementById("registration-fee").value.trim()}`,
        `Maximum players/teams: ${document.getElementById("max-players").value}`,
        `Game days: ${document.getElementById("game-days").value.trim()}`,
        `Organizer: ${document.getElementById("organizer-name").value.trim()} (${document.getElementById("organizer-email").value.trim()})`
    ];
    return details.join("\n");
}

leagueForm.addEventListener("submit", async (event) => {
    event.preventDefault();
    const id = document.getElementById("league-id").value;
    const league = {
        name: document.getElementById("league-name").value.trim(),
        sport: document.getElementById("sport").value,
        location: document.getElementById("location").value.trim(),
        description: buildDescription()
    };

    try {
        leagueSubmitButton.disabled = true;
        const response = await fetch(`${API_BASE_URL}${id ? `/leagues/${id}` : "/leagues"}`, {
            method: id ? "PUT" : "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(league)
        });
        if (!response.ok) throw new Error(await response.text() || "League could not be saved.");
        const savedLeague = await response.json();
        showLeagueMessage(id ? "League updated successfully." : "League created successfully.");
        window.setTimeout(() => {
            window.location.href = `league-details.html?id=${savedLeague.id}`;
        }, 700);
    } catch (error) {
        showLeagueMessage(error.message, true);
    } finally {
        leagueSubmitButton.disabled = false;
    }
});

async function loadLeagueForEditing() {
    if (!editLeagueId) return;
    const response = await fetch(`${API_BASE_URL}/leagues/${editLeagueId}`);
    if (!response.ok) throw new Error("The selected league could not be loaded.");
    const league = await response.json();
    document.getElementById("league-id").value = league.id;
    document.getElementById("league-name").value = league.name || "";
    document.getElementById("sport").value = league.sport || "";
    document.getElementById("location").value = league.location || "";
    document.getElementById("league-description").value = league.description || "";
    leagueSubmitButton.textContent = "Update League";
    document.querySelector(".create-hero h1").textContent = "Update League";
}

function showLeagueMessage(text, isError = false) {
    leagueMessage.textContent = text;
    leagueMessage.className = isError ? "prototype-message settings-error" : "prototype-message settings-success";
}

loadLeagueForEditing().catch(error => showLeagueMessage(error.message, true));
