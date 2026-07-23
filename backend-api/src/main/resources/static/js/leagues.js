const API_BASE_URL = "https://league-finder-backendapi.onrender.com";
const leagueList = document.getElementById("league-list");
const leagueSearchForm = document.getElementById("league-search-form");
const leagueSearchInput = document.getElementById("league-search-input");
const sportFilter = document.getElementById("sport-filter");
const searchMessage = document.getElementById("league-search-message");
const initialParameters = new URLSearchParams(window.location.search);

sportFilter.value = initialParameters.get("sport") || "";
leagueSearchInput.value = initialParameters.get("search") || "";

leagueSearchForm.addEventListener("submit", (event) => {
    event.preventDefault();
    loadLeagues();
});

async function loadLeagues() {
    const sport = sportFilter.value;
    const endpoint = sport
        ? `${API_BASE_URL}/leagues/sport/${encodeURIComponent(sport)}`
        : `${API_BASE_URL}/leagues`;

    searchMessage.textContent = "Loading leagues...";

    try {
        const response = await fetch(endpoint);
        if (!response.ok) throw new Error("Leagues could not be loaded.");

        const searchTerm = leagueSearchInput.value.trim().toLowerCase();
        const leagues = (await response.json()).filter((league) =>
            !searchTerm ||
            (league.name || "").toLowerCase().includes(searchTerm) ||
            (league.location || "").toLowerCase().includes(searchTerm)
        );

        renderLeagues(leagues);
        searchMessage.textContent = leagues.length
            ? `${leagues.length} league${leagues.length === 1 ? "" : "s"} found.`
            : "No leagues matched your search.";
    } catch (error) {
        leagueList.replaceChildren();
        searchMessage.textContent = error.message;
    }
}

function renderLeagues(leagues) {
    leagueList.replaceChildren();

    leagues.forEach((league) => {
        const card = document.createElement("article");
        card.className = "league-card";

        const visual = document.createElement("div");
        visual.className = `league-image ${sportClass(league.sport)}`;
        visual.textContent = sportIcon(league.sport);

        const info = document.createElement("div");
        info.className = "league-info";

        const sport = document.createElement("span");
        sport.className = "league-sport-badge";
        sport.textContent = league.sport || "Recreation League";

        const name = document.createElement("h2");
        name.textContent = league.name;

        const location = document.createElement("p");
        location.className = "league-location";
        location.textContent = `Location: ${league.location || "To be announced"}`;

        const description = document.createElement("p");
        description.className = "league-description";
        description.textContent = league.description || "League details coming soon.";

        const actions = document.createElement("div");
        actions.className = "league-actions";

        const detailsLink = document.createElement("a");
        detailsLink.className = "btn primary";
        detailsLink.href = `league-details.html?id=${league.id}`;
        detailsLink.textContent = "View Details";

        const joinLink = document.createElement("a");
        joinLink.className = "btn secondary";
        joinLink.href = `join-league.html?leagueId=${league.id}`;
        joinLink.textContent = "Join League";

        actions.append(detailsLink, joinLink);
        info.append(sport, name, location, description, actions);
        card.append(visual, info);
        leagueList.append(card);
    });
}

function sportClass(sport = "") {
    const normalizedSport = sport.toLowerCase();
    if (normalizedSport.includes("basketball")) return "basketball";
    if (normalizedSport.includes("football")) return "football";
    if (normalizedSport.includes("volleyball")) return "volleyball";
    if (normalizedSport.includes("soccer")) return "soccer";
    return "recreation";
}

function sportIcon(sport = "") {
    const normalizedSport = sport.toLowerCase();
    if (normalizedSport.includes("basketball")) return "🏀";
    if (normalizedSport.includes("football")) return "🏈";
    if (normalizedSport.includes("volleyball")) return "🏐";
    if (normalizedSport.includes("soccer")) return "⚽";
    return "🏆";
}

loadLeagues();
