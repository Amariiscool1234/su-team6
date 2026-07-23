const API_BASE_URL = "https://league-finder-backendapi.onrender.com";

async function loadProviderDashboard() {
    const providersResponse = await fetch(`${API_BASE_URL}/providers`);
    if (!providersResponse.ok) throw new Error("Could not load provider information.");
    const providers = await providersResponse.json();
    if (providers.length === 0) throw new Error("Create a provider profile before using the dashboard.");

    let providerId = Number(localStorage.getItem("leagueFinderProviderId"));
    let provider = providers.find(item => item.id === providerId);
    if (!provider) {
        provider = providers[0];
        providerId = provider.id;
        localStorage.setItem("leagueFinderProviderId", providerId);
    }

    document.getElementById("dashboard-provider-name").textContent = `${provider.name || "Provider"}'s Dashboard`;

    const [teamsResponse, gamesResponse, leaguesResponse] = await Promise.all([
        fetch(`${API_BASE_URL}/teams/provider/${providerId}`),
        fetch(`${API_BASE_URL}/games`),
        fetch(`${API_BASE_URL}/leagues`)
    ]);
    if (!teamsResponse.ok || !gamesResponse.ok || !leaguesResponse.ok) {
        throw new Error("Could not load provider dashboard information.");
    }

    const teams = await teamsResponse.json();
    const games = await gamesResponse.json();
    const leagues = await leaguesResponse.json();
    const registrationLists = await Promise.all(teams.map(async team => {
        const response = await fetch(`${API_BASE_URL}/team-registrations/team/${team.id}`);
        return response.ok ? response.json() : [];
    }));
    const registrations = registrationLists.flat();

    const approved = registrations.filter(item => item.status === "APPROVED").length;
    const pending = registrations.filter(item => item.status === "PENDING").length;
    const rejected = registrations.filter(item => item.status === "REJECTED").length;
    document.getElementById("dashboard-team-count").textContent = teams.length;
    document.getElementById("dashboard-registration-count").textContent = registrations.length;
    document.getElementById("dashboard-approved-count").textContent = approved;
    document.getElementById("dashboard-pending-count").textContent = pending;
    document.getElementById("dashboard-rejected-count").textContent = rejected;

    const providerLeagueIds = new Set(teams.map(team => team.league?.id).filter(Boolean));
    const providerGames = games.filter(game => providerLeagueIds.has(game.league?.id));
    document.getElementById("dashboard-game-count").textContent = providerGames.length;
    const nextGame = providerGames
        .filter(game => new Date(`${game.gameDate}T${game.gameTime}`) >= new Date())
        .sort((a, b) => `${a.gameDate}T${a.gameTime}`.localeCompare(`${b.gameDate}T${b.gameTime}`))[0];
    if (nextGame) {
        document.getElementById("dashboard-next-game").textContent =
            `Next: ${nextGame.homeTeam} vs ${nextGame.awayTeam} on ${formatDate(nextGame.gameDate)} at ${formatTime(nextGame.gameTime)}.`;
    }

    const primaryLeague = teams[0]?.league || leagues[0];
    if (primaryLeague) {
        document.getElementById("dashboard-league-name").textContent = primaryLeague.name;
        document.getElementById("dashboard-league-location").textContent = `${primaryLeague.sport} • ${primaryLeague.location}`;
        document.getElementById("dashboard-edit-league").href = `create.html?id=${primaryLeague.id}`;
    } else {
        document.getElementById("dashboard-league-name").textContent = "No league created yet.";
        document.getElementById("dashboard-league-location").textContent = "Create your first listing to get started.";
        document.getElementById("dashboard-edit-league").textContent = "Create League";
    }
}

function formatDate(value) {
    return new Date(`${value}T00:00:00`).toLocaleDateString(undefined, {month: "short", day: "numeric", year: "numeric"});
}

function formatTime(value) {
    return new Date(`1970-01-01T${value}`).toLocaleTimeString(undefined, {hour: "numeric", minute: "2-digit"});
}

loadProviderDashboard().catch(error => {
    const message = document.getElementById("dashboard-message");
    message.textContent = error.message;
    message.className = "prototype-message settings-error";
});
