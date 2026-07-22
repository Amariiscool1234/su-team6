const providerId = Number(localStorage.getItem("leagueFinderProviderId"));
const teamForm = document.getElementById("team-form");
const teamMessage = document.getElementById("team-message");
const teamList = document.getElementById("team-list");
const cancelEditButton = document.getElementById("cancel-edit-button");

async function initialize() {
    if (!providerId) {
        showMessage("Create a provider profile before managing teams.", true);
        teamForm.querySelector("button[type='submit']").disabled = true;
        return;
    }
    await loadLeagues();
    await loadTeams();
}

async function loadLeagues() {
    const response = await fetch("/leagues");
    if (!response.ok) throw new Error("Could not load leagues.");
    const leagues = await response.json();
    const select = document.getElementById("league-id");
    select.replaceChildren();
    leagues.forEach(league => {
        const option = document.createElement("option");
        option.value = league.id;
        option.textContent = `${league.name} — ${league.location}`;
        select.append(option);
    });
    if (leagues.length === 0) showMessage("Create a league before creating a team.", true);
}

async function loadTeams() {
    const response = await fetch(`/teams/provider/${providerId}`);
    if (!response.ok) throw new Error("Could not load teams.");
    renderTeams(await response.json());
}

teamForm.addEventListener("submit", async (event) => {
    event.preventDefault();
    const teamId = document.getElementById("team-id").value;
    const request = {
        name: document.getElementById("team-name").value.trim(),
        sport: document.getElementById("team-sport").value.trim(),
        skillLevel: document.getElementById("skill-level").value.trim(),
        maxPlayers: Number(document.getElementById("max-players").value),
        leagueId: Number(document.getElementById("league-id").value)
    };
    const response = await fetch(teamId ? `/teams/${teamId}` : `/teams/provider/${providerId}`, {
        method: teamId ? "PUT" : "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(request)
    });
    if (!response.ok) {
        showMessage(await response.text() || "Team could not be saved.", true);
        return;
    }
    resetForm();
    showMessage(teamId ? "Team updated." : "Team created.");
    await loadTeams();
});

function renderTeams(teams) {
    teamList.replaceChildren();
    if (teams.length === 0) {
        teamList.textContent = "No teams created yet.";
        return;
    }
    teams.forEach(team => {
        const card = document.createElement("div");
        card.className = "team-item";
        card.innerHTML = `<h3></h3><p></p>`;
        card.querySelector("h3").textContent = team.name;
        card.querySelector("p").textContent =
            `${team.sport} • ${team.skillLevel} • Max ${team.maxPlayers} players`;
        const editButton = document.createElement("button");
        editButton.textContent = "Edit";
        editButton.addEventListener("click", () => editTeam(team));
        const deleteButton = document.createElement("button");
        deleteButton.textContent = "Delete";
        deleteButton.className = "danger-button";
        deleteButton.addEventListener("click", () => deleteTeam(team.id));
        card.append(editButton, deleteButton);
        teamList.append(card);
    });
}

function editTeam(team) {
    document.getElementById("team-id").value = team.id;
    document.getElementById("team-name").value = team.name;
    document.getElementById("team-sport").value = team.sport;
    document.getElementById("skill-level").value = team.skillLevel;
    document.getElementById("max-players").value = team.maxPlayers;
    document.getElementById("league-id").value = team.league.id;
    document.getElementById("team-form-title").textContent = "Update Team";
    cancelEditButton.hidden = false;
}

async function deleteTeam(id) {
    if (!confirm("Delete this team?")) return;
    const response = await fetch(`/teams/${id}`, {method: "DELETE"});
    if (!response.ok) {
        showMessage("Team could not be deleted.", true);
        return;
    }
    showMessage("Team deleted.");
    await loadTeams();
}

cancelEditButton.addEventListener("click", resetForm);

function resetForm() {
    teamForm.reset();
    document.getElementById("team-id").value = "";
    document.getElementById("team-form-title").textContent = "Create Team";
    cancelEditButton.hidden = true;
}

function showMessage(text, isError = false) {
    teamMessage.textContent = text;
    teamMessage.className = isError ? "message error" : "message success";
}

initialize().catch(error => showMessage(error.message, true));
