const passwordDialog = document.getElementById("password-dialog");
const notificationDialog = document.getElementById("notification-dialog");
const deleteDialog = document.getElementById("delete-dialog");

document.getElementById("change-password-button").addEventListener("click", () => {
    document.getElementById("password-message").textContent = "";
    passwordDialog.showModal();
});

document.getElementById("notification-settings-button").addEventListener("click", () => {
    loadNotificationSettings();
    document.getElementById("notification-message").textContent = "";
    notificationDialog.showModal();
});

document.getElementById("delete-account-button").addEventListener("click", () => {
    document.getElementById("delete-confirmation").value = "";
    document.getElementById("delete-message").textContent = "";
    deleteDialog.showModal();
});

document.querySelectorAll("[data-close-dialog]").forEach((button) => {
    button.addEventListener("click", () => {
        document.getElementById(button.dataset.closeDialog).close();
    });
});

document.getElementById("password-form").addEventListener("submit", (event) => {
    event.preventDefault();

    const currentPassword = document.getElementById("current-password").value;
    const newPassword = document.getElementById("new-password").value;
    const confirmPassword = document.getElementById("confirm-password").value;
    const message = document.getElementById("password-message");

    if (currentPassword === newPassword) {
        showMessage(message, "The new password must be different from the current password.", true);
        return;
    }

    if (newPassword !== confirmPassword) {
        showMessage(message, "The new passwords do not match.", true);
        return;
    }

    showMessage(message, "Password updated for the prototype.");
    event.target.reset();
});

document.getElementById("notification-form").addEventListener("submit", (event) => {
    event.preventDefault();

    const settings = {
        emailNotifications: document.getElementById("email-notifications").checked,
        gameReminders: document.getElementById("game-reminders").checked,
        leagueUpdates: document.getElementById("league-updates").checked
    };

    localStorage.setItem("leagueFinderNotificationSettings", JSON.stringify(settings));
    showMessage(document.getElementById("notification-message"), "Notification settings saved.");
});

document.getElementById("delete-form").addEventListener("submit", (event) => {
    event.preventDefault();

    const confirmation = document.getElementById("delete-confirmation").value.trim();
    const message = document.getElementById("delete-message");

    if (confirmation !== "DELETE") {
        showMessage(message, "Type DELETE exactly to confirm.", true);
        return;
    }

    localStorage.removeItem("leagueFinderNotificationSettings");
    localStorage.removeItem("leagueFinderCustomer");
    window.location.href = "index.html?accountDeleted=true";
});

function loadNotificationSettings() {
    const savedSettings = JSON.parse(
        localStorage.getItem("leagueFinderNotificationSettings") || "{}"
    );

    document.getElementById("email-notifications").checked =
        savedSettings.emailNotifications ?? true;
    document.getElementById("game-reminders").checked =
        savedSettings.gameReminders ?? true;
    document.getElementById("league-updates").checked =
        savedSettings.leagueUpdates ?? true;
}

function showMessage(element, text, isError = false) {
    element.textContent = text;
    element.className = isError
        ? "settings-message settings-error"
        : "settings-message settings-success";
}
