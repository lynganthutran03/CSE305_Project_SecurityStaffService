function showFunction(functionName) {
    const display = document.getElementById("functionDisplay");
    switch (functionName) {
        case "viewSchedule":
            display.innerHTML = "<h3>Viewing Schedule</h3><p>Schedule details go here...</p>";
            break;
        case "requestLeave":
            display.innerHTML = "<h3>Requesting Leave</h3><p>Leave request form goes here...</p>";
            break;
        case "checkLeaves":
            display.innerHTML = "<h3>Checking Leaves</h3><p>Leave history displayed here...</p>";
            break;
        case "showSalary":
            display.innerHTML = "<h3>Salary Details</h3><p>Salary information displayed here...</p>";
            break;
        case "viewRoutine":
            display.innerHTML = "<h3>Viewing Routine</h3><p>Routine details displayed here...</p>";
            break;
        case "createRoutine":
            display.innerHTML = "<h3>Creating Routine</h3><p>Routine management form goes here...</p>";
            break;
        case "checkLeaveRequests":
            display.innerHTML = "<h3>Checking Leave Requests</h3><p>Manager can approve/reject leaves here...</p>";
            break;
        case "monitoring":
            display.innerHTML = "<h3>Monitoring</h3><p>Live monitoring of staff displayed here...</p>";
            break;
        default:
            display.innerHTML = "<h3>Unknown Action</h3><p>Please select a valid option.</p>";
    }
}

function logout() {
    alert("Logging out...");
    window.location.reload();
}