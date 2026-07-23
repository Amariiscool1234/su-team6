document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("register-form");
    const message = document.getElementById("message");

    if (!form || !message) {
        return;
    }

    message.textContent = "";

    form.addEventListener("submit", async (event) => {
        event.preventDefault();

        message.textContent = "Creating account...";
        message.style.color = "black";

        const password = document.getElementById("password").value;
        const confirmPassword =
            document.getElementById("confirm-password").value;

        if (password !== confirmPassword) {
            message.textContent = "Passwords do not match.";
            message.style.color = "red";
            return;
        }

        const accountData = {
            name: document.getElementById("name").value.trim(),
            email: document.getElementById("email").value.trim(),
            location: document.getElementById("location").value.trim(),
            favoriteSport:
                document.getElementById("favorite-sport").value,
            skillLevel:
                document.getElementById("skill-level").value,
            password: password,
            role: document.getElementById("account-type").value
        };

        try {
            const response = await fetch("/auth/register", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Accept": "application/json"
                },
                body: JSON.stringify(accountData)
            });

            const rawResponse = await response.text();

            let result;

            try {
                result = JSON.parse(rawResponse);
            } catch {
                result = rawResponse;
            }

            if (response.ok) {
                const successMessage =
                    typeof result === "object"
                        ? result.message
                        : result;

                message.textContent =
                    successMessage ||
                    "Account created successfully. Redirecting to login...";

                message.style.color = "green";
                form.reset();

                setTimeout(() => {
                    window.location.replace("/login.html");
                }, 1500);

                return;
            }

            let errorMessage;

            if (typeof result === "string") {
                errorMessage = result;
            } else {
                errorMessage =
                    result.message ||
                    result.error ||
                    JSON.stringify(result);
            }

            message.textContent =
                `Registration failed (${response.status}): ${errorMessage}`;

            message.style.color = "red";

        } catch (error) {
            message.textContent =
                "Could not connect to the backend: " + error.message;

            message.style.color = "red";
        }
    });
});