const API_BASE_URL = "";
const gameForm = document.getElementById("game-form");
const gameList = document.getElementById("game-list");
const rosterList = document.getElementById("roster-request-list");
const leagueSelect = document.getElementById("game-league");
const gameMessage = document.getElementById("game-message");
const rosterMessage = document.getElementById("roster-message");
const cancelGameEdit = document.getElementById("cancel-game-edit");
let providerId = Number(localStorage.getItem("leagueFinderProviderId")) || null;
let providerTeams = [];
let providerLeagueIds = new Set();

async function initializeProviderManagement() {
    const providersResponse = await fetch(`${API_BASE_URL}/providers`);
    if (!providersResponse.ok) throw new Error("Could not load provider information.");
    const providers = await providersResponse.json();
    if (providers.length === 0) throw new Error("Create a provider profile first.");
    if (!providers.some(provider => provider.id === providerId)) {
        providerId = providers[0].id;
        localStorage.setItem("leagueFinderProviderId", providerId);
    }

    const [teamsResponse, leaguesResponse] = await Promise.all([
        fetch(`${API_BASE_URL}/teams/provider/${providerId}`),
        fetch(`${API_BASE_URL}/leagues`)
    ]);
    if (!teamsResponse.ok || !leaguesResponse.ok) throw new Error("Could not load provider teams and leagues.");
    providerTeams = await teamsResponse.json();
    const leagues = await leaguesResponse.json();
    providerLeagueIds = new Set(providerTeams.map(team => team.league?.id).filter(Boolean));
    const selectableLeagues = providerLeagueIds.size
        ? leagues.filter(league => providerLeagueIds.has(league.id))
        : leagues;
    leagueSelect.replaceChildren(new Option("Choose a league", ""));
    selectableLeagues.forEach(league => leagueSelect.add(new Option(`${league.name} — ${league.location}`, league.id)));

    await Promise.all([loadRosterRequests(), loadGames()]);
}

async function loadRosterRequests() {
    const lists = await Promise.all(providerTeams.map(async team => {
        const response = await fetch(`${API_BASE_URL}/team-registrations/team/${team.id}`);
        if (!response.ok) return [];
        return (await response.json()).map(registration => ({...registration, providerTeam: team}));
    }));
    renderRosterRequests(lists.flat());
}

function renderRosterRequests(registrations) {
    rosterList.replaceChildren();
    const pendingCount = registrations.filter(item => item.status === "PENDING").length;
    document.getElementById("roster-summary").textContent = `${registrations.length} total • ${pendingCount} pending`;
    if (registrations.length === 0) {
        rosterList.textContent = "No roster requests have been submitted for your teams.";
        return;
    }
    registrations.forEach(registration => {
        const card = document.createElement("article");
        card.className = "management-card";
        const info = document.createElement("div");
        const customerName = registration.customer?.name || registration.customer?.email || "Customer";
        info.innerHTML = `<h3>${escapeHtml(customerName)}</h3><p>${escapeHtml(registration.providerTeam.name)} • <span class="status-pill status-${registration.status.toLowerCase()}">${escapeHtml(registration.status)}</span></p>`;
        const actions = document.createElement("div");
        actions.className = "management-actions";
        if (registration.status !== "APPROVED") actions.append(makeStatusButton(registration.id, "APPROVED", "Approve"));
        if (registration.status !== "REJECTED") actions.append(makeStatusButton(registration.id, "REJECTED", "Decline", true));
        card.append(info, actions);
        rosterList.append(card);
    });
}

function makeStatusButton(id, status, label, danger = false) {
    const button = document.createElement("button");
    button.type = "button";
    button.textContent = label;
    if (danger) button.className = "danger-button";
    button.addEventListener("click", () => updateRegistrationStatus(id, status));
    return button;
}

async function updateRegistrationStatus(id, status) {
    try {
        const response = await fetch(`${API_BASE_URL}/team-registrations/${id}/status`, {
            method: "PUT",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({status})
        });
        if (!response.ok) throw new Error(await response.text() || "Roster request could not be updated.");
        showMessage(rosterMessage, `Request marked ${status.toLowerCase()}.`);
        await loadRosterRequests();
    } catch (error) {
        showMessage(rosterMessage, error.message, true);
    }
}

async function loadGames() {
    const response = await fetch(`${API_BASE_URL}/games`);
    if (!response.ok) throw new Error("Could not load game schedules.");
    const games = await response.json();
    const providerGames = providerLeagueIds.size
        ? games.filter(game => providerLeagueIds.has(game.league?.id))
        : games;
    renderGames(providerGames);
}

function renderGames(games) {
    gameList.replaceChildren();
    if (games.length === 0) {
        gameList.textContent = "No games scheduled yet.";
        return;
    }
    games.sort((a, b) => `${a.gameDate}T${a.gameTime}`.localeCompare(`${b.gameDate}T${b.gameTime}`));
    games.forEach(game => {
        const card = document.createElement("article");
        card.className = "management-card";
        const info = document.createElement("div");
        info.innerHTML = `<h3>${escapeHtml(game.homeTeam)} vs ${escapeHtml(game.awayTeam)}</h3><p>${escapeHtml(game.gameDate)} at ${escapeHtml(game.gameTime)} • ${escapeHtml(game.venue)}${game.court ? ` • ${escapeHtml(game.court)}` : ""}</p>`;
        const actions = document.createElement("div");
        actions.className = "management-actions";
        const editButton = document.createElement("button");
        editButton.type = "button";
        editButton.textContent = "Edit";
        editButton.addEventListener("click", () => editGame(game));
        const deleteButton = document.createElement("button");
        deleteButton.type = "button";
        deleteButton.textContent = "Delete";
        deleteButton.className = "danger-button";
        deleteButton.addEventListener("click", () => deleteGame(game.id));
        actions.append(editButton, deleteButton);
        card.append(info, actions);
        gameList.append(card);
    });
}

gameForm.addEventListener("submit", async event => {
    event.preventDefault();
    const gameId = document.getElementById("game-id").value;
    const request = {
        gameDate: document.getElementById("game-date").value,
        gameTime: document.getElementById("game-time").value,
        homeTeam: document.getElementById("home-team").value.trim(),
        awayTeam: document.getElementById("away-team").value.trim(),
        venue: document.getElementById("game-venue").value.trim(),
        court: document.getElementById("game-court").value.trim()
    };
    const endpoint = gameId ? `/games/${gameId}` : `/games/league/${leagueSelect.value}`;
    try {
        const response = await fetch(`${API_BASE_URL}${endpoint}`, {
            method: gameId ? "PUT" : "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(request)
        });
        if (!response.ok) throw new Error(await response.text() || "Game could not be saved.");
        resetGameForm();
        showMessage(gameMessage, gameId ? "Game updated successfully." : "Game scheduled successfully.");
        await loadGames();
    } catch (error) {
        showMessage(gameMessage, error.message, true);
    }
});

function editGame(game) {
    document.getElementById("game-id").value = game.id;
    leagueSelect.value = game.league?.id || "";
    leagueSelect.disabled = true;
    document.getElementById("game-date").value = game.gameDate || "";
    document.getElementById("game-time").value = (game.gameTime || "").slice(0, 5);
    document.getElementById("home-team").value = game.homeTeam || "";
    document.getElementById("away-team").value = game.awayTeam || "";
    document.getElementById("game-venue").value = game.venue || "";
    document.getElementById("game-court").value = game.court || "";
    document.getElementById("game-submit-button").textContent = "Update Game";
    cancelGameEdit.hidden = false;
    gameForm.scrollIntoView({behavior: "smooth"});
}

async function deleteGame(id) {
    if (!window.confirm("Delete this scheduled game?")) return;
    try {
        const response = await fetch(`${API_BASE_URL}/games/${id}`, {method: "DELETE"});
        if (!response.ok) throw new Error("Game could not be deleted.");
        showMessage(gameMessage, "Game deleted successfully.");
        await loadGames();
    } catch (error) {
        showMessage(gameMessage, error.message, true);
    }
}

function resetGameForm() {
    gameForm.reset();
    document.getElementById("game-id").value = "";
    leagueSelect.disabled = false;
    document.getElementById("game-submit-button").textContent = "Schedule Game";
    cancelGameEdit.hidden = true;
}

function showMessage(element, text, isError = false) {
    element.textContent = text;
    element.className = isError ? "prototype-message settings-error" : "prototype-message settings-success";
}

function escapeHtml(value) {
    return String(value ?? "").replace(/[&<>"']/g, character => ({
        "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;", "'": "&#039;"
    })[character]);
}

cancelGameEdit.addEventListener("click", resetGameForm);
initializeProviderManagement().catch(error => {
    showMessage(rosterMessage, error.message, true);
    showMessage(gameMessage, error.message, true);
});
