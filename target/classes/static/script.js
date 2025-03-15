document.addEventListener("DOMContentLoaded", function () {
    checkLoginStatus();
});

//Login
function showLoginScreen() {
    const display = document.getElementById("functionDisplay");
    display.innerHTML = `
        <div class="form-container">
            <h3>Login</h3>
            <label>Enter your ID: <input type="text" id="userId" autocomplete="off"></label>
            <label>Select Role: 
                <select id="userRole">
                    <option value="staff">Staff</option>
                    <option value="manager">Manager</option>
                </select>
            </label>
            <button onclick="handleLogin()">Login</button>
        </div>
    `;

    document.getElementById("staffFunctions").style.display = "none";
    document.getElementById("managerFunctions").style.display = "none";
}

function handleLogin() {
    const userId = document.getElementById("userId").value.trim();
    const userRole = document.getElementById("userRole").value;

    if (!userId) {
        alert("Please enter your ID.");
        return;
    }

    localStorage.setItem("userRole", userRole);
    localStorage.setItem("userId", userId);

    checkLoginStatus();
}

function checkLoginStatus() {
    const userRole = localStorage.getItem("userRole");
    const userId = localStorage.getItem("userId");

    if (!userRole || !userId) {
        showLoginScreen();
        return;
    }

    // Hide/show elements based on role
    document.getElementById("staffFunctions").style.display = userRole === "staff" ? "block" : "none";
    document.getElementById("managerFunctions").style.display = userRole === "manager" ? "block" : "none";

    // Show default screen
    if (userRole === "staff") {
        showFunction("viewSchedule");
    } else {
        showFunction("monitoring");
    }
}

function showFunction(functionName) {
    const display = document.getElementById("functionDisplay");
    switch (functionName) {

        case "viewSchedule":
            display.innerHTML = `
                <h3>Viewing Schedule</h3>
                <div class="table-container">
                    <table class="styled-table">
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
                </div>
            `;
            setTimeout(fetchSchedules, 0);
            break;

        case "requestLeave":
            display.innerHTML = `
                <div class="form-container">
                    <h3>Request Leave</h3>
                    <label>Staff ID: <input type="text" id="staffId"></label><br>
                    <label>Start Date: <input type="date" id="startDate"></label><br>
                    <label>End Date: <input type="date" id="endDate"></label><br>
                    <label>Reason: <textarea id="reason"></textarea></label><br>
                    <button onclick="requestLeave()">Submit Leave Request</button>
                </div>
            `;
            break;

        case "checkLeaves":
            display.innerHTML = `
            <div class="form-container">
                <h3>Check Leaves</h3>
                <label>Staff ID: <input type="text" id="staffIdCheck"></label>
                <button onclick="fetchCheckLeaveRequestsForStaff()">Check</button>
            </div>
                <div class="table-container">
                    <table class="styled-table">
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
                </div>
            `;
            break;

        case "showSalary":
            display.innerHTML = `
                <div class="form-container">
                    <h3>Show Salary</h3>
                    <label>Enter your staff Id: <input type="text" id="salaryStaffId"></label>
                    <button onclick="fetchSalaryStaff()">Check Salary</button>
                    <p id="salaryResult"></p>
                </div>
            `;
            break;

        case "createRoutine":
            display.innerHTML = `
                <div class="form-container">
                    <h3>Creating Routine</h3>
                    <label>Staff ID: <input type="text" id="staffId"></label><br>
                    <label>Place: <input type="text" id="place"></label><br>
                    <label>Shift Time: <input type="text" id="shiftTime"></label><br>
                    <label>Date: <input type="date" id="date"></label><br>
                    <button onclick="addRoutine()">Add Routine</button>
                </div>
            `;
            break;

        case "checkLeaveRequests":
            display.innerHTML = `
            <div class="form-container">
                <h3>Check Leave Requests</h3>
            </div>
                <div class="table-container">
                    <table class="styled-table>
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
                </div>
            `;
            fetchLeaveRequests();
            break;

        case "monitoring":
            display.innerHTML = `
                <div class="form-container">
                    <h3>Monitoring</h3>
                    <h4>Salary Monitoring</h4>
                    <label>Enter staff Id: <input type="text" id="salaryStaffId" autocomplete="off"></label>
                    <button onclick="fetchSalaryManager()">Check</button>
                    <p id="leaveCount"></p>
                    <p id="salaryResult"></p>
                </div>

                <div class="form-container">
                    <h4>Routine Monitoring</h4>
                    <label>Staff ID: <input type="text" id="staffId" autocomplete="off"></label>
                    <label>Date: <input type="date" id="date"></label>
                    <label>Place: <input type="text" id="place"></label>
                    <button onclick="setTimeout(fetchRoutineMonitoring, 100)">Filter</button>
                </div>
                
                <div class="table-container">
                    <table class="styled-table">
                        <thead>
                            <tr>
                                <th>Staff ID</th>
                                <th>Place</th>
                                <th>Shift Time</th>
                                <th>Date</th>
                            </tr>
                        </thead>
                        <tbody id="routineMonitoringList">
                            <tr><td colspan="4">Enter filters and click 'Filter'.</td></tr>
                        </tbody>
                    </table>
                </div>
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
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(schedule)
    })
    .then(response => response.text())
    .then(data => {
        alert("Routine added successfully!");
        showFunction("monitoring");
        setTimeout(fetchRoutineMonitoring, 300);
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
        fetchLeaveRequests(); 
    })
    .catch(error => console.error("Error updating leave status:", error));
}

//Check leave request for staff
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

//Fetch salary for staff
function fetchSalaryStaff() {
    const salaryStaffId = document.getElementById("salaryStaffId").value;
    if(!salaryStaffId) {
        alert("PLease enter your Id");
        return;
    }

    fetch(`http://localhost:8080/api/salary/${salaryStaffId}`)
        .then(response => {
            if(!response.ok) throw new Error("Error fetching salary.");
            return response.json();
        })
        .then(data => {
            document.getElementById("salaryResult").innerHTML = `<strong> Your salary: $${data.toFixed(2)}</strong>`;
        })
        .catch(error => {
            document.getElementById("salaryResult").innerHTML = `<p style="color:red;">Error fetching salary</p>`;
            console.error("Error: ", error);
        });
}

//Fetch salary for manager
function fetchSalaryManager() {
    let salaryStaffId = document.getElementById("salaryStaffId").value;
    if(!salaryStaffId) {
        alert("Please enter a staff Id.");
        return;
    }

    fetch(`http://localhost:8080/api/leaves/count/${salaryStaffId}`)
        .then(response => {
            if(!response.ok) throw new Error("Error fetching leave count.");
            return response.json();
        })
        .then(leaveCount => {
            document.getElementById("leaveCount").innerHTML = `<strong>Leaves taken: ${leaveCount}</strong>`;
            return fetch(`http://localhost:8080/api/salary/${staffId}`);
        })
        .then(response => {
            if(!response.ok) throw new Error("Error calculating salary.");
            return response.json();
        })
        .then(data => {
            document.getElementById("calculateSalaryResult").innerHTML = `<strong>Calculated salary: $${data.toFixed(2)}</strong>`;
        })
        .catch(error => {
            document.getElementById("calculateSalaryResult").innerHTML = `<p style="color:red;">Error calculating salary.</p>`;
            console.error("Error: ", error);
        });
}

function fetchRoutineMonitoring() {
    setTimeout(() => {
        const staffIdField = document.getElementById("staffId"); 
        const dateField = document.getElementById("date"); 
        const placeField = document.getElementById("place"); 

        if (!staffIdField || !dateField || !placeField) {
            console.error("One or more input fields are missing in the JavaScript-generated form.");
            return;
        }

        const staffId = staffIdField.value;
        const date = dateField.value.trim() || null;
        const place = placeField.value;

        fetch(`http://localhost:8080/api/schedules/filter?staffId=${staffId}&date=${date}&place=${place}`)
            .then(response => response.json())
            .then(data => {
                const routineMonitoringList = document.getElementById("routineMonitoringList");
                if (!routineMonitoringList) {
                    console.error("Routine Monitoring Table not found!");
                    return;
                }
                routineMonitoringList.innerHTML = "";

                if (data.length === 0) {
                    routineMonitoringList.innerHTML = "<tr><td colspan='4'>No matching schedules found.</td></tr>";
                    return;
                }

                data.forEach(schedule => {
                    const row = `<tr>
                        <td>${schedule.staffId}</td>
                        <td>${schedule.place}</td>
                        <td>${schedule.shiftTime}</td>
                        <td>${schedule.date}</td>
                    </tr>`;
                    routineMonitoringList.innerHTML += row;
                });
            })
            .catch(error => console.error("Error fetching routine monitoring data:", error));
    }, 200);
}

// Logout function
function logout() {
    localStorage.removeItem("userRole");
    localStorage.removeItem("userId");

    document.getElementById("staffFunctions").style.display = "none";
    document.getElementById("managerFunctions").style.display = "none";
    
    showLoginScreen();
}