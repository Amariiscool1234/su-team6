const API_BASE_URL = "https://league-finder-backendapi.onrender.com";
const teamForm = document.getElementById("team-prototype-form");
const teamList = document.getElementById("prototype-team-list");
const teamMessage = document.getElementById("team-prototype-message");
const leagueSelect = document.getElementById("team-league");
const cancelEditButton = document.getElementById("cancel-team-edit");
let providerId = Number(localStorage.getItem("leagueFinderProviderId")) || null;

async function initialize() {
    if (!providerId) {
        const response = await fetch(`${API_BASE_URL}/providers`);
        if (!response.ok) throw new Error("Could not load provider information.");
        const providers = await response.json();
        if (providers.length === 0) {
            throw new Error("Create a provider profile before managing teams.");
        }
        providerId = providers[0].id;
        localStorage.setItem("leagueFinderProviderId", providerId);
    }

    await loadLeagues();
    await loadTeams();
}

async function loadLeagues() {
    const response = await fetch(`${API_BASE_URL}/leagues`);
    if (!response.ok) throw new Error("Could not load leagues.");
    const leagues = await response.json();
    leagueSelect.replaceChildren(new Option("Choose a league", ""));
    leagues.forEach(league => leagueSelect.add(new Option(`${league.name} — ${league.location}`, league.id)));
}

async function loadTeams() {
    const response = await fetch(`${API_BASE_URL}/teams/provider/${providerId}`);
    if (!response.ok) throw new Error("Could not load teams.");
    renderTeams(await response.json());
}

teamForm.addEventListener("submit", async (event) => {
    event.preventDefault();
    const teamId = document.getElementById("team-id").value;
    const request = {
        name: document.getElementById("team-name").value.trim(),
        sport: document.getElementById("team-sport").value.trim(),
        skillLevel: document.getElementById("team-skill").value,
        maxPlayers: Number(document.getElementById("team-size").value),
        leagueId: Number(leagueSelect.value)
    };
    const endpoint = teamId ? `/teams/${teamId}` : `/teams/provider/${providerId}`;

    try {
        const response = await fetch(`${API_BASE_URL}${endpoint}`, {
            method: teamId ? "PUT" : "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(request)
        });
        if (!response.ok) throw new Error(await response.text() || "Team could not be saved.");
        resetForm();
        showMessage(teamId ? "Team updated successfully." : "Team created successfully.");
        await loadTeams();
    } catch (error) {
        showMessage(error.message, true);
    }
});

function renderTeams(teams) {
    teamList.replaceChildren();
    if (teams.length === 0) {
        teamList.textContent = "No teams created yet.";
        return;
    }

    teams.forEach(team => {
        const card = document.createElement("article");
        card.className = "prototype-team-card";
        const heading = document.createElement("h3");
        heading.textContent = team.name;
        const details = document.createElement("p");
        details.textContent = `${team.sport} • ${team.skillLevel} • Max ${team.maxPlayers} players`;
        const editButton = document.createElement("button");
        editButton.type = "button";
        editButton.textContent = "Edit Team";
        editButton.addEventListener("click", () => editTeam(team));
        const deleteButton = document.createElement("button");
        deleteButton.type = "button";
        deleteButton.textContent = "Delete Team";
        deleteButton.className = "danger-button";
        deleteButton.addEventListener("click", () => deleteTeam(team.id));
        card.append(heading, details, editButton, deleteButton);
        teamList.append(card);
    });
}

function editTeam(team) {
    document.getElementById("team-id").value = team.id;
    document.getElementById("team-name").value = team.name || "";
    document.getElementById("team-sport").value = team.sport || "";
    document.getElementById("team-skill").value = team.skillLevel || "";
    document.getElementById("team-size").value = team.maxPlayers || "";
    leagueSelect.value = team.league?.id || "";
    document.getElementById("team-submit-button").textContent = "Update Team";
    cancelEditButton.hidden = false;
    teamForm.scrollIntoView({behavior: "smooth"});
}

async function deleteTeam(id) {
    if (!window.confirm("Delete this team?")) return;
    try {
        const response = await fetch(`${API_BASE_URL}/teams/${id}`, {method: "DELETE"});
        if (!response.ok) throw new Error("Team could not be deleted.");
        showMessage("Team deleted successfully.");
        await loadTeams();
    } catch (error) {
        showMessage(error.message, true);
    }
}

function resetForm() {
    teamForm.reset();
    document.getElementById("team-id").value = "";
    document.getElementById("team-submit-button").textContent = "Create Team";
    cancelEditButton.hidden = true;
}

function showMessage(text, isError = false) {
    teamMessage.textContent = text;
    teamMessage.className = isError ? "prototype-message settings-error" : "prototype-message settings-success";
}

cancelEditButton.addEventListener("click", resetForm);
initialize().catch(error => showMessage(error.message, true));
