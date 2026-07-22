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

        const info = document.createElement("div");
        info.className = "league-info";

        const name = document.createElement("h2");
        name.textContent = league.name;

        const sport = document.createElement("p");
        sport.textContent = league.sport;

        const location = document.createElement("p");
        location.textContent = league.location;

        const description = document.createElement("p");
        description.textContent = league.description || "League details coming soon.";

        const detailsLink = document.createElement("a");
        detailsLink.className = "btn primary";
        detailsLink.href = `league-details.html?id=${league.id}`;
        detailsLink.textContent = "View Details";

        info.append(name, sport, location, description, detailsLink);
        card.append(info);
        leagueList.append(card);
    });
}

loadLeagues();
