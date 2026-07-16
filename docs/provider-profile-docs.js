const profileForm = document.getElementById("provider-prototype-form");
const storageKey = "leagueFinderPublicProfile";

profileForm.addEventListener("submit", (event) => {
    event.preventDefault();

    const profile = {
        name: document.getElementById("provider-name").value.trim(),
        email: document.getElementById("provider-email").value.trim(),
        phone: document.getElementById("provider-phone").value.trim(),
        organization: document.getElementById("provider-organization").value.trim(),
        sport: document.getElementById("provider-sport").value.trim(),
        location: document.getElementById("provider-location").value.trim()
    };

    localStorage.setItem(storageKey, JSON.stringify(profile));
    displayProfile(profile);

    document.getElementById("provider-prototype-message").textContent =
        "Profile updated successfully.";
});

function displayProfile(profile) {
    document.getElementById("display-name").textContent = profile.name;
    document.getElementById("display-email").textContent = profile.email;
    document.getElementById("display-phone").textContent = profile.phone;
    document.getElementById("display-organization").textContent = profile.organization;
    document.getElementById("display-sport").textContent = profile.sport;
    document.getElementById("display-location").textContent = profile.location;
    document.getElementById("profile-heading").textContent = profile.name;
    document.getElementById("profile-summary").textContent =
        `${profile.organization} • ${profile.location}`;
    document.getElementById("profile-initials").textContent = profile.name
        .split(/\s+/)
        .filter(Boolean)
        .slice(0, 2)
        .map((part) => part[0].toUpperCase())
        .join("") || "LF";
}

function loadSavedProfile() {
    const savedProfile = JSON.parse(localStorage.getItem(storageKey) || "null");
    if (!savedProfile) return;

    document.getElementById("provider-name").value = savedProfile.name;
    document.getElementById("provider-email").value = savedProfile.email;
    document.getElementById("provider-phone").value = savedProfile.phone;
    document.getElementById("provider-organization").value = savedProfile.organization;
    document.getElementById("provider-sport").value = savedProfile.sport;
    document.getElementById("provider-location").value = savedProfile.location;
    displayProfile(savedProfile);
}

loadSavedProfile();
