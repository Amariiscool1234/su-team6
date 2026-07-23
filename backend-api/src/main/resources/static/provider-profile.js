const providerForm = document.getElementById("provider-form");
const providerMessage = document.getElementById("provider-message");
let providerId = Number(localStorage.getItem("leagueFinderProviderId")) || null;

async function loadProvider() {
    try {
        if (providerId !== null) {
            const savedProviderResponse = await fetch(`/providers/${providerId}`);
            if (savedProviderResponse.ok) {
                displayProvider(await savedProviderResponse.json());
                return;
            }

            providerId = null;
            localStorage.removeItem("leagueFinderProviderId");
        }

        const response = await fetch("/providers");
        if (!response.ok) throw new Error("Could not load providers.");
        const providers = await response.json();
        if (providers.length === 0) {
            showMessage("Enter your information to create a profile.");
            return;
        }

        providerId = providers[0].id;
        localStorage.setItem("leagueFinderProviderId", providerId);
        displayProvider(providers[0]);
    } catch (error) {
        showMessage(error.message, true);
    }
}

providerForm.addEventListener("submit", async (event) => {
    event.preventDefault();
    const provider = Object.fromEntries(new FormData(providerForm));
    const method = providerId === null ? "POST" : "PUT";
    const url = providerId === null ? "/providers" : `/providers/${providerId}`;

    try {
        const response = await fetch(url, {
            method,
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(provider)
        });
        if (!response.ok) throw new Error(await response.text() || "Provider could not be saved.");

        const savedProvider = await response.json();
        providerId = savedProvider.id;
        localStorage.setItem("leagueFinderProviderId", providerId);
        displayProvider(savedProvider);
        showMessage(method === "POST" ? "Provider profile created." : "Provider profile updated.");
    } catch (error) {
        showMessage(error.message, true);
    }
});

function displayProvider(provider) {
    for (const field of ["name", "email", "phoneNumber", "organizationName", "sportType", "location"]) {
        providerForm.elements[field].value = provider[field] ?? "";
    }
    document.getElementById("display-name").textContent = provider.name;
    document.getElementById("display-email").textContent = provider.email;
    document.getElementById("display-phone").textContent = provider.phoneNumber;
    document.getElementById("display-organization").textContent = provider.organizationName;
    document.getElementById("display-sport").textContent = provider.sportType;
    document.getElementById("display-location").textContent = provider.location;
    document.getElementById("provider-name").textContent = provider.name;
    document.getElementById("provider-summary").textContent =
        `${provider.organizationName} • ${provider.sportType} • ${provider.location}`;
    document.getElementById("provider-initials").textContent = provider.name
        .split(/\s+/).slice(0, 2).map(part => part[0]).join("").toUpperCase();
    document.getElementById("provider-form-title").textContent = "Update Profile";
    document.getElementById("provider-save-button").textContent = "Update Profile";
}

function showMessage(text, isError = false) {
    providerMessage.textContent = text;
    providerMessage.className = isError ? "message error" : "message success";
}

loadProvider();
