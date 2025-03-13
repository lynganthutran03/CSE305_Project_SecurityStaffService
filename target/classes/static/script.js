function showFunction(functionName) {
    const display = document.getElementById("functionDisplay");
    switch (functionName) {

        case "viewSchedule":
            display.innerHTML = `
                <h3>Viewing Schedule</h3>
                <table border="1">
                    <thead>
                        <tr>
                            <th>Staff ID</th>
                            <th>Place</th>
                            <th>Shift Time</th>
                            <th>Date</th>
                        </tr>
                    </thead>
                    <tbody id="scheduleList">
                        <tr><td colspan="4">Loading...</td></tr>
                    </tbody>
                </table>
            `;
            setTimeout(fetchSchedules, 0);
            break;

        case "requestLeave":
            display.innerHTML = `
                <h3>Request Leave</h3>
                <label>Staff ID: <input type="text" id="staffId"></label><br>
                <label>Start Date: <input type="date" id="startDate"></label><br>
                <label>End Date: <input type="date" id="endDate"></label><br>
                <label>Reason: <textarea id="reason"></textarea></label><br>
                <button onclick="requestLeave()">Submit Leave Request</button>
            `;
            break;

        case "checkLeaves":
            display.innerHTML = `
                <h3>Check Leaves</h3>
                <label>Staff ID: <input type="text" id="staffIdCheck"></label>
                <button onclick="fetchCheckLeaveRequestsForStaff()">Check</button>
                <table border="1">
                    <thead>
                        <tr>
                            <th>Leave ID</th>
                            <th>Start Date</th>
                            <th>End Date</th>
                            <th>Reason</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody id="myLeaveRequestsList">
                        <tr><td colspan="5">Enter Staff ID and click 'Check'.</td></tr>
                    </tbody>
                </table>
            `;
            break;

        case "showSalary":
            display.innerHTML = "<h3>Salary Details</h3><p>Salary information displayed here...</p>";
            break;

        case "createRoutine":
            display.innerHTML = `
                <h3>Creating Routine</h3>
                <label>Staff ID: <input type="text" id="staffId"></label><br>
                <label>Place: <input type="text" id="place"></label><br>
                <label>Shift Time: <input type="text" id="shiftTime"></label><br>
                <label>Date: <input type="date" id="date"></label><br>
                <button onclick="addRoutine()">Add Routine</button>
            `;
            break;

        case "checkLeaveRequests":
            display.innerHTML = `
                <h3>Check Leave Requests</h3>
                <table border="1">
                    <thead>
                        <tr>
                            <th>Leave ID</th>
                            <th>Staff ID</th>
                            <th>Start Date</th>
                            <th>End Date</th>
                            <th>Reason</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody id="leaveRequestsList">
                        <tr><td colspan="7">Loading...</td></tr>
                    </tbody>
                </table>
            `;
            fetchLeaveRequests();
            break;

        case "monitoring":
            display.innerHTML = `
                <h3>Monitoring</h3>
                <h4>Salary Monitoring</h4>
                <p>Salary details displayed here...</p>

                <h4>Routine Monitoring</h4>
                <p>Routine details displayed here...</p>
            `;
            break;

        default:
            display.innerHTML = "<h3>Unknown Action</h3><p>Please select a valid option.</p>";
    }
}

// Fetch schedules from the backend (list stored in-memory on the backend)
function fetchSchedules() {
    fetch("http://localhost:8080/api/schedules/view")
        .then(response => response.json())
        .then(data => {
            const scheduleList = document.getElementById("scheduleList");
            scheduleList.innerHTML = ""; // Clear the existing schedule list

            if (data.length === 0) {
                scheduleList.innerHTML = "<tr><td colspan='4'>No schedules available.</td></tr>";
                return;
            }

            data.forEach(schedule => {
                let row = `<tr>
                    <td>${schedule.staffId}</td>
                    <td>${schedule.place}</td>
                    <td>${schedule.shiftTime}</td>
                    <td>${schedule.date}</td>
                </tr>`;
                scheduleList.innerHTML += row;
            });
        })
        .catch(error => console.error("Error:", error));
}

// Add routine (send data to the backend to store in the list)
function addRoutine() {
    let staffId = document.getElementById("staffId").value;
    let place = document.getElementById("place").value;
    let shiftTime = document.getElementById("shiftTime").value;
    let date = document.getElementById("date").value;

    let schedule = { staffId, place, shiftTime, date };

    fetch("http://localhost:8080/api/schedules/add", {
        method: "POST",
        headers: { 
            "Content-Type": "application/json"
        },
        body: JSON.stringify(schedule)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.text();
    })
    .then(data => {
        alert("Routine added successfully!");
        fetchSchedules(); // Refresh schedule list
    })
    .catch(error => {
        console.error("Error:", error);
        alert("Failed to add routine: " + error.message);
    });
}

// Request leave (send data to the backend)
function requestLeave() {
    const staffId = document.getElementById("staffId").value;
    const startDate = document.getElementById("startDate").value;
    const endDate = document.getElementById("endDate").value;
    const reason = document.getElementById("reason").value;

    if (!staffId || !startDate || !endDate || !reason) {
        alert("Please fill all fields");
        return;
    }

    fetch("http://localhost:8080/api/leaves/request", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            staffId: staffId,
            startDate: startDate,
            endDate: endDate,
            reason: reason
        })
    })
    .then(response => {
        if (!response.ok) throw new Error("HTTP error! Status: " + response.status);
        return response.json();  // Parse JSON response
    })
    .then(data => {
        alert(data.message);  // Access the message from the response JSON
        showFunction('viewSchedule');
    })
    .catch(error => console.error("Error requesting leave:", error));
}

// Fetch leave requests (from the backend)
function fetchLeaveRequests() {
    fetch("http://localhost:8080/api/leaves")
        .then(response => {
            if (!response.ok) throw new Error("HTTP error! Status: " + response.status);
            return response.json();
        })
        .then(data => {
            const leaveRequestsList = document.getElementById("leaveRequestsList");
            leaveRequestsList.innerHTML = ""; // Clear existing data

            if (data.length === 0) {
                leaveRequestsList.innerHTML = "<tr><td colspan='7'>No leave requests found.</td></tr>";
                return;
            }

            data.forEach(leave => {
                leaveRequestsList.innerHTML += `
                    <tr>
                        <td>${leave.leaveId}</td>
                        <td>${leave.staffId}</td>
                        <td>${leave.startDate}</td>
                        <td>${leave.endDate}</td>
                        <td>${leave.reason}</td>
                        <td>${leave.status}</td>
                        <td>
                            <button onclick="updateLeaveStatus(${leave.leaveId}, 'APPROVED')">Approve</button>
                            <button onclick="updateLeaveStatus(${leave.leaveId}, 'REJECTED')">Reject</button>
                        </td>
                    </tr>
                `;
            });
        })
        .catch(error => console.error("Error fetching leave requests:", error));
}

// Update leave status (send the update to the backend)
function updateLeaveStatus(leaveId, status) {
    fetch(`http://localhost:8080/api/leaves/${leaveId}/status`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ status: status })
    })
    .then(response => {
        if (!response.ok) throw new Error("Error updating leave status: " + response.status);
        return response.json();
    })
    .then(data => {
        alert(`Leave request ${status.toLowerCase()} successfully!`);
        fetchLeaveRequests();  // Refresh the list of leave requests
    })
    .catch(error => console.error("Error updating leave status:", error));
}

function fetchCheckLeaveRequestsForStaff() {
    const staffId = document.getElementById("staffIdCheck").value;
    if (!staffId) {
        alert("Please enter a Staff ID.");
        return;
    }

    fetch(`http://localhost:8080/api/leaves/staff/${staffId}`)
        .then(response => {
            if (!response.ok) throw new Error("HTTP error! Status: " + response.status);
            return response.json();
        })
        .then(data => {
            const myLeaveRequestsList = document.getElementById("myLeaveRequestsList");
            myLeaveRequestsList.innerHTML = "";

            // Filter leave requests for the given staff ID
            const myLeaves = data.filter(leave => leave.staffId == staffId);

            if (myLeaves.length === 0) {
                myLeaveRequestsList.innerHTML = "<tr><td colspan='5'>No leave requests found.</td></tr>";
                return;
            }

            myLeaves.forEach(leave => {
                myLeaveRequestsList.innerHTML += `
                    <tr>
                        <td>${leave.leaveId}</td>
                        <td>${leave.startDate}</td>
                        <td>${leave.endDate}</td>
                        <td>${leave.reason}</td>
                        <td>${leave.status}</td>
                    </tr>
                `;
            });
        })
        .catch(error => console.error("Error fetching leave requests:", error));
}

// Logout function
function logout() {
    alert("Logging out...");
    window.location.reload();
}