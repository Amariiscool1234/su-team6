const API_URL = "/customers";

document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("profile-form");
    const message = document.getElementById("message");

    if (!form) {
        alert("The profile form was not found.");
        return;
    }

    form.addEventListener("submit", async function (event) {
        event.preventDefault();
        alert("Submit handler worked");
        message.textContent = "Saving profile...";

        const customerId =
            document.getElementById("customer-id").value;

        const customer = {
            name: document.getElementById("name").value,
            email: document.getElementById("email").value,
            location: document.getElementById("location").value,
            favoriteSport:
                document.getElementById("favorite-sport").value,
            skillLevel:
                document.getElementById("skill-level").value
        };

        const method = customerId ? "PUT" : "POST";

        const url = customerId
            ? `${API_URL}/${customerId}`
            : API_URL;

        try {
            const response = await fetch(url, {
                method: method,
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(customer)
            });

            if (!response.ok) {
                const errorText = await response.text();

                throw new Error(
                    errorText || "The profile could not be saved."
                );
            }

            const savedCustomer = await response.json();

            document.getElementById("customer-id").value =
                savedCustomer.id;

            localStorage.setItem(
                "customerId",
                savedCustomer.id
            );

            message.textContent = customerId
                ? "Profile updated successfully."
                : "Profile created successfully.";

        } catch (error) {
            message.textContent = error.message;
        }
    });

    loadProfile();

    async function loadProfile() {
        const customerId =
            localStorage.getItem("customerId");

        if (!customerId) {
            return;
        }

        try {
            const response = await fetch(
                `${API_URL}/${customerId}`
            );

            if (!response.ok) {
                localStorage.removeItem("customerId");
                return;
            }

            const customer = await response.json();

            document.getElementById("customer-id").value =
                customer.id;

            document.getElementById("name").value =
                customer.name || "";

            document.getElementById("email").value =
                customer.email || "";

            document.getElementById("location").value =
                customer.location || "";

            document.getElementById("favorite-sport").value =
                customer.favoriteSport || "Basketball";

            document.getElementById("skill-level").value =
                customer.skillLevel || "Beginner";

        } catch (error) {
            message.textContent = "Unable to load profile.";
        }
    }
});