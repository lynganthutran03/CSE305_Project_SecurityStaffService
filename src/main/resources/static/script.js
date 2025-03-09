document.addEventListener("DOMContentLoaded", function () {
    // Check if the user is logged in
    const userId = localStorage.getItem("userId");
    const role = localStorage.getItem("role");
    if (userId && role) {
        showDashboard(role, userId);
    }
});

async function handleLogin() {
    const userId = document.getElementById("userId").value;
    const password = document.getElementById("password").value;

    try {
        const response = await fetch("http://localhost:9090/auth/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ userId, password })
        });

        if (!response.ok) {
            alert("Login failed!");
            return;
        };

        const data = await response.json();
        console.log("Login respond: " + data);

        localStorage.setItem("userId", data.id);
        localStorage.setItem("role", data.role);
        localStorage.setItem("token", data.token);

        document.getElementById("login-section").style.display = "none";
        document.getElementById("dashboard").style.display = "block";

        showDashboard(data.role, data.id);
    } catch (error) {
        console.error("Login error: ", error);
    }
}

function showDashboard(role, userId) {
    const navItems = document.getElementById("nav-items");
    navItems.innerHTML = "";
    document.getElementById("content-area").innerHTML = `<h3>Welcome, ID: ${userId}</h3>`

    let buttons = [];
    
    if (role === "STAFF") {
        buttons = [
            {label: "View schedule", action: "viewSchedule()"},
            {label: "Request leave", action: "requestLeave()"},
            {label: "Check leaves", action: "checkLeaves()"},
        ];
    } else if (role === "MANAGER") {
        buttons = [
            {label: "Create routine", action: "createRoutine()"},
            {label: "Check leave requests", action: "checkLeaveRequests()"},
            {label: "Monitoring", action: "monitoring()"},
        ];
    }

    buttons.forEach(btn => {
        const li = document.createElement("li");
        li.innerHTML = `<button onclick="${btn.action}">${btn.label}</button>`;
        navItems.appendChild(li);
    });
}

function handleLogout() {
    localStorage.clear();
    document.getElementById("login-section").style.display = "block";
    document.getElementById("dashboard").style.display = "none";
}