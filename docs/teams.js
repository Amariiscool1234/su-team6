const API_BASE_URL = "https://league-finder-backendapi.onrender.com";
const teamList = document.getElementById("team-list");
const registrationList =
    document.getElementById("registration-list");
const message = document.getElementById("message");

async function loadTeams() {
    try {
        const response = await fetch(`${API_BASE_URL}/teams`);

        if (!response.ok) {
            throw new Error("Unable to load teams");
        }

        const teams = await response.json();

        teamList.innerHTML = "";

        if (teams.length === 0) {
            teamList.innerHTML = "<p>No teams are available.</p>";
            return;
        }

        teams.forEach((team) => {
            const card = document.createElement("section");
            card.classList.add("team-card");

            card.innerHTML = `
                <h2>${team.name}</h2>
                <p><strong>Sport:</strong> ${team.sport || "N/A"}</p>
                <p><strong>Skill level:</strong>
                    ${team.skillLevel || "All levels"}
                </p>
                <p><strong>Maximum players:</strong>
                    ${team.maxPlayers || "Not listed"}
                </p>
                <button data-team-id="${team.id}">
                    Sign Up
                </button>
            `;

            const button = card.querySelector("button");

            button.addEventListener("click", () => {
                registerForTeam(team.id);
            });

            teamList.appendChild(card);
        });
    } catch (error) {
        message.textContent = error.message;
    }
}

async function registerForTeam(teamId) {
    const customerId = localStorage.getItem("customerId");

    if (!customerId) {
        message.textContent =
            "Create your customer profile before signing up.";
        return;
    }

    const registration = {
        customerId: Number(customerId),
        teamId: Number(teamId)
    };

    try {
        const response = await fetch(`${API_BASE_URL}/team-registrations`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(registration)
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || "Registration failed");
        }

        message.textContent =
            "Your team registration request was submitted.";

        loadCustomerRegistrations();
    } catch (error) {
        message.textContent = error.message;
    }
}

async function loadCustomerRegistrations() {
    const customerId = localStorage.getItem("customerId");

    if (!customerId) {
        registrationList.innerHTML =
            "<p>Create a profile to view registrations.</p>";
        return;
    }

    try {
        const response = await fetch(
            `${API_BASE_URL}/team-registrations/customer/${customerId}`
        );

        if (!response.ok) {
            throw new Error("Unable to load registrations");
        }

        const registrations = await response.json();

        registrationList.innerHTML = "";

        if (registrations.length === 0) {
            registrationList.innerHTML =
                "<p>You have not signed up for a team.</p>";
            return;
        }

        registrations.forEach((registration) => {
            const item = document.createElement("article");

            item.innerHTML = `
                <h3>${registration.team.name}</h3>
                <p>Status: ${registration.status}</p>
            `;

            registrationList.appendChild(item);
        });
    } catch (error) {
        message.textContent = error.message;
    }
}

loadTeams();
loadCustomerRegistrations();
