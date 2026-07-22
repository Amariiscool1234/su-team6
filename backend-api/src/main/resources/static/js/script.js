const homeSearchForm = document.getElementById("home-search-form");

if (homeSearchForm) {
    homeSearchForm.addEventListener("submit", (event) => {
        event.preventDefault();

        const parameters = new URLSearchParams();
        const sport = document.getElementById("home-sport-filter").value;
        const location = document.getElementById("home-location-filter").value.trim();

        if (sport) parameters.set("sport", sport);
        if (location) parameters.set("search", location);

        const query = parameters.toString();
        window.location.href = `leagues.html${query ? `?${query}` : ""}`;
    });
}
