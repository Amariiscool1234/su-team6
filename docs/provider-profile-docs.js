document.getElementById("provider-prototype-form").addEventListener("submit", (event) => {
    event.preventDefault();
    document.getElementById("provider-prototype-message").textContent =
        "Profile preview updated. Database persistence is available in the Spring Boot version.";
});
