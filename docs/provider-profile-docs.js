const API_BASE_URL = "https://league-finder-backendapi.onrender.com";
const profileForm = document.getElementById("provider-prototype-form");
const profileMessage = document.getElementById("provider-prototype-message");
let providerId = Number(localStorage.getItem("leagueFinderProviderId")) || null;

async function loadProvider() {
    try {
        if (!providerId) {
            const response = await fetch(`${API_BASE_URL}/providers`);
            if (!response.ok) throw new Error("Could not load provider information.");
            const providers = await response.json();
            if (providers.length === 0) return;
            providerId = providers[0].id;
            localStorage.setItem("leagueFinderProviderId", providerId);
        }

        const response = await fetch(`${API_BASE_URL}/providers/${providerId}`);
        if (!response.ok) throw new Error("Could not load the provider profile.");
        displayProfile(await response.json());
    } catch (error) {
        showMessage(error.message, true);
    }
}

profileForm.addEventListener("submit", async (event) => {
    event.preventDefault();

    const provider = {
        name: document.getElementById("provider-name").value.trim(),
        email: document.getElementById("provider-email").value.trim(),
        phoneNumber: document.getElementById("provider-phone").value.trim(),
        organizationName: document.getElementById("provider-organization").value.trim(),
        sportType: document.getElementById("provider-sport").value.trim(),
        location: document.getElementById("provider-location").value.trim()
    };

    const method = providerId ? "PUT" : "POST";
    const endpoint = providerId ? `/providers/${providerId}` : "/providers";

    try {
        const response = await fetch(`${API_BASE_URL}${endpoint}`, {
            method,
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(provider)
        });
        if (!response.ok) throw new Error(await response.text() || "Profile could not be saved.");

        const savedProvider = await response.json();
        providerId = savedProvider.id;
        localStorage.setItem("leagueFinderProviderId", providerId);
        displayProfile(savedProvider);
        showMessage(method === "POST" ? "Profile created successfully." : "Profile updated successfully.");
    } catch (error) {
        showMessage(error.message, true);
    }
});

function displayProfile(provider) {
    const profile = {
        name: provider.name || "",
        email: provider.email || "",
        phone: provider.phoneNumber || "",
        organization: provider.organizationName || "",
        sport: provider.sportType || "",
        location: provider.location || ""
    };

    document.getElementById("provider-name").value = profile.name;
    document.getElementById("provider-email").value = profile.email;
    document.getElementById("provider-phone").value = profile.phone;
    document.getElementById("provider-organization").value = profile.organization;
    document.getElementById("provider-sport").value = profile.sport;
    document.getElementById("provider-location").value = profile.location;
    document.getElementById("display-name").textContent = profile.name;
    document.getElementById("display-email").textContent = profile.email;
    document.getElementById("display-phone").textContent = profile.phone;
    document.getElementById("display-organization").textContent = profile.organization;
    document.getElementById("display-sport").textContent = profile.sport;
    document.getElementById("display-location").textContent = profile.location;
    document.getElementById("profile-heading").textContent = profile.name;
    document.getElementById("profile-summary").textContent = `${profile.organization} • ${profile.location}`;
    document.getElementById("profile-initials").textContent = profile.name
        .split(/\s+/).filter(Boolean).slice(0, 2).map(part => part[0].toUpperCase()).join("") || "LF";
}

function showMessage(text, isError = false) {
    profileMessage.textContent = text;
    profileMessage.className = isError ? "prototype-message settings-error" : "prototype-message settings-success";
}

loadProvider();
