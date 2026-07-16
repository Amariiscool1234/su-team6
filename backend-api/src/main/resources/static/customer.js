const customerForm = document.getElementById("customer-form");
const customerMessage = document.getElementById("customer-message");
const saveButton = document.getElementById("save-button");
const editButton = document.getElementById("edit-button");
const formCard = document.getElementById("profile-form-card");
let customerId = null;

async function loadCustomer() {
    try {
        const response = await fetch("/customers");
        if (!response.ok) {
            throw new Error("Could not load the customer profile.");
        }

        const customers = await response.json();
        if (customers.length === 0) {
            return;
        }

        const customer = customers[0];
        customerId = customer.id;
        customerForm.name.value = customer.name ?? "";
        customerForm.email.value = customer.email ?? "";
        customerForm.location.value = customer.location ?? "";
        customerForm.favoriteSport.value = customer.favoriteSport ?? "";
        saveButton.textContent = "Update Profile";
        document.getElementById("form-title").textContent = "Update Profile";
        displayCustomer(customer);
    } catch (error) {
        showMessage(error.message, true);
    }
}

customerForm.addEventListener("submit", async (event) => {
    event.preventDefault();

    const customer = {
        name: customerForm.name.value.trim(),
        email: customerForm.email.value.trim(),
        location: customerForm.location.value.trim(),
        favoriteSport: customerForm.favoriteSport.value.trim()
    };

    const url = customerId === null ? "/customers" : `/customers/${customerId}`;
    const method = customerId === null ? "POST" : "PUT";

    try {
        const response = await fetch(url, {
            method,
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(customer)
        });

        if (!response.ok) {
            throw new Error("The profile could not be saved. Check that the email is unique.");
        }

        const savedCustomer = await response.json();
        customerId = savedCustomer.id;
        saveButton.textContent = "Update Profile";
        document.getElementById("form-title").textContent = "Update Profile";
        displayCustomer(savedCustomer);
        showMessage(method === "POST" ? "Profile created successfully." : "Profile updated successfully.");
    } catch (error) {
        showMessage(error.message, true);
    }
});

editButton.addEventListener("click", () => {
    formCard.scrollIntoView({behavior: "smooth"});
    customerForm.name.focus();
});

function displayCustomer(customer) {
    const name = customer.name || "Not set";
    const location = customer.location || "Not set";
    const favoriteSport = customer.favoriteSport || "Not set";

    document.getElementById("profile-name").textContent = name;
    document.getElementById("profile-summary").textContent = `${favoriteSport} player • ${location}`;
    document.getElementById("display-name").textContent = name;
    document.getElementById("display-email").textContent = customer.email || "Not set";
    document.getElementById("display-location").textContent = location;
    document.getElementById("display-sport").textContent = favoriteSport;
    document.getElementById("profile-initials").textContent = name
        .split(/\s+/)
        .filter(Boolean)
        .slice(0, 2)
        .map((part) => part[0].toUpperCase())
        .join("") || "LF";
}

function showMessage(message, isError = false) {
    customerMessage.textContent = message;
    customerMessage.className = isError ? "message error" : "message success";
}

loadCustomer();
