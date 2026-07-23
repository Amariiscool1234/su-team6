const GAME_API_URL = "https://league-finder-backendapi.onrender.com";
const scheduleList = document.getElementById("schedule-list");
const scheduleMessage = document.getElementById("schedule-message");
const leagueId = new URLSearchParams(window.location.search).get("id") || "1";

async function loadLeagueDetails() {
    try {
        const response = await fetch(`${GAME_API_URL}/leagues/${leagueId}`);
        if (!response.ok) throw new Error("League details could not be loaded.");

        const league = await response.json();
        document.title = `${league.name} | League Finder`;
        document.getElementById("league-name").textContent = league.name;
        document.getElementById("league-sport").textContent = league.sport;
        document.getElementById("league-summary").textContent =
            `${league.sport} recreation league in ${league.location}.`;
        document.getElementById("league-description").textContent =
            league.description || "Organized recreation league with weekly games.";
        document.getElementById("league-location").textContent = league.location;
        document.getElementById("venue-name").textContent = `${league.name} Home Court`;
        document.getElementById("venue-address").textContent = league.location;
        document.getElementById("join-league-link").href =
            `join-league.html?leagueId=${league.id}`;
    } catch (error) {
        document.getElementById("league-name").textContent = "League unavailable";
        document.getElementById("league-summary").textContent = error.message;
    }
}

async function loadSchedule() {
    try {
        const response = await fetch(`${GAME_API_URL}/games/league/${leagueId}`);
        if (!response.ok) throw new Error("The schedule could not be loaded.");

        const games = await response.json();
        renderSchedule(games);
        scheduleMessage.textContent = games.length
            ? `${games.length} upcoming game${games.length === 1 ? "" : "s"}`
            : "No games are scheduled yet.";
    } catch (error) {
        scheduleMessage.textContent = error.message;
    }
}

function renderSchedule(games) {
    scheduleList.replaceChildren();

    games.forEach((game) => {
        const item = document.createElement("div");
        item.className = "schedule-item";

        const matchup = document.createElement("div");
        const teams = document.createElement("strong");
        teams.textContent = `${game.homeTeam} vs. ${game.awayTeam}`;

        const venue = document.createElement("p");
        venue.textContent = `${game.venue}${game.court ? ` · ${game.court}` : ""}`;

        const dateAndTime = document.createElement("span");
        dateAndTime.textContent = formatGameDate(game.gameDate, game.gameTime);

        matchup.append(teams, venue);
        item.append(matchup, dateAndTime);
        scheduleList.append(item);
    });
}

function formatGameDate(date, time) {
    const gameDate = new Date(`${date}T${time}`);
    return new Intl.DateTimeFormat("en-US", {
        weekday: "short",
        month: "short",
        day: "numeric",
        hour: "numeric",
        minute: "2-digit"
    }).format(gameDate);
}

loadLeagueDetails();
loadSchedule();
