document.addEventListener("DOMContentLoaded", function () {
    // Check if the user is logged in
    const user = JSON.parse(localStorage.getItem("user"));
    if (user) {
        showDashboard(user.role);
    } else {
        showLogin();
    }
});

function showLogin() {
    document.getElementById("app").innerHTML = `
        <div class="login-container">
            <h2>Login</h2>
            <input type="text" id="username" placeholder="ID">
            <input type="password" id="password" placeholder="Password">
            <button onclick="handleLogin()">Login</button>
        </div>
    `;
}

async function handleLogin() {
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    try {
        const response = await fetch("http://localhost:8080/auth/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ username, password })
        });

        if (!response.ok) throw new Error("Login failed");

        const data = await response.json();
        localStorage.setItem("user", JSON.stringify({ username, role: data.role, token: data.token }));
        showDashboard(data.role);
    } catch (error) {
        alert("Invalid login");
    }
}

function showDashboard(role) {
    let buttons = "";
    
    if (role === "STAFF") {
        buttons = `
            <button onclick="loadContent('View Schedule')">View Schedule</button>
            <button onclick="loadContent('Request Leave')">Request Leave</button>
            <button onclick="loadContent('Check Leaves')">Check Leaves</button>
        `;
    } else if (role === "MANAGER") {
        buttons = `
            <button onclick="loadContent('Create Routine')">Create Routine</button>
            <button onclick="loadContent('Check Leave Requests')">Check Leave Requests</button>
            <button onclick="loadContent('Monitoring')">Monitoring</button>
        `;
    }

    document.getElementById("app").innerHTML = `
        <h2>Dashboard (${role})</h2>
        <div class="buttons">${buttons}</div>
        <button onclick="handleLogout()">Logout</button>
        <div id="content"></div>
    `;
}

function loadContent(content) {
    document.getElementById("content").innerHTML = `<h3>${content}</h3><p>Loading data...</p>`;
}

function handleLogout() {
    localStorage.removeItem("user");
    showLogin();
}