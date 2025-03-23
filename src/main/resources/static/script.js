document.addEventListener("DOMContentLoaded", showLoginScreen);

//Login
function showLoginScreen() {
    const display = document.getElementById("functionDisplay");
    display.innerHTML = `
        <div class="login-container">
            <h3>Login</h3>
            <label>Identity Number: <input type="text" id="identityNumber" autocomplete="off"></label>
            <label>Password: <input type="password" id="password" autocomplete="off"></label>
            <button onclick="handleLogin()">Login</button>
        </div>
    `;

    document.getElementById("staffFunctions").style.display = "none";
    document.getElementById("managerFunctions").style.display = "none";
}

function handleLogin() {
    const identityNumber = document.getElementById("identityNumber").value.trim();
    const password = document.getElementById("password").value.trim();

    if (!identityNumber || !password) {
        alert("Please enter both identity number and password.");
        return;
    }

    fetch("http://localhost:8080/api/auth/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            identityNumber: identityNumber,
            password: password
        })
    })
    .then(response => {
        if (!response.ok) throw new Error("Invalid credentials");
        return response.json();
    })
    .then(data => {
        localStorage.setItem("userRole", data.role);
        localStorage.setItem("identityNumber", identityNumber);
        updateUI();
    })
    .catch(error => {
        console.error("Login error:", error);
        alert("Invalid identity number or password.");
    });
}

function checkLoginStatus() {
    const userRole = localStorage.getItem("userRole");

    if (!userRole) {
        showLoginScreen();
    } else {
        updateUI();
    }
}

function updateUI() {
    const userRole = localStorage.getItem("userRole");

    if (userRole === "staff") {
        document.getElementById("staffFunctions").style.display = "block";
        document.getElementById("managerFunctions").style.display = "none";
        document.getElementById("functionDisplay").innerHTML = "Please check your attendance";
    } else if (userRole === "manager") {
        document.getElementById("staffFunctions").style.display = "none";
        document.getElementById("managerFunctions").style.display = "block";
        document.getElementById("functionDisplay").innerHTML = "Check the staff attendance first";
    }

}

function showStaffFunctions() {
    document.getElementById("staffFunctions").style.display = "flex";
    document.getElementById("managerFunctions").style.display = "none";
}

function showManagerFunctions() {
    document.getElementById("staffFunctions").style.display = "none";
    document.getElementById("managerFunctions").style.display = "flex";
}

function toggleForm(formId) {
    document.querySelectorAll(".form-container").forEach(section => {
        section.classList.add("hidden");
    });

    document.getElementById(formId).classList.remove("hidden");
    document.getElementById("backButton").classList.remove("hidden");
}

function goBack() {
    document.querySelectorAll(".form-container, .table-container, .hidden-content").forEach(el => {
        el.classList.add("hidden");
    });

    const routineTable = document.getElementById("routineMonitoringTable");
    if (routineTable) {
        routineTable.classList.add("hidden");
        routineTable.style.display = "none";
    }

    document.querySelector(".form-container:first-child").classList.remove("hidden");
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
                                <th>Mark Attendance</th>
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
                    <label>Identity Number: <input type="text" id="identityNumber"></label><br>
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
                <label>Staff ID: <input type="text" id="identityNumberCheck"></label>
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
                            <tr><td colspan="5">Enter staff ID and click 'Check'.</td></tr>
                        </tbody>
                    </table>
                </div>
            `;
            break;

        case "showSalary":
            display.innerHTML = `
                <div class="form-container">
                    <h3>Show Salary</h3>
                    <button onclick="fetchSalaryStaff()">Check Salary</button>
                    <p id="salaryResult"></p>
                </div>
            `;
            break;
        
        //FOR MANAGER SIDE ONLY
        case "viewAllAttendance":
            display.innerHTML = `
                <h3>Staff Attendance</h3>
                <div class="table-container">
                    <table class="styled-table">
                        <thead>
                            <tr>
                                <th>Staff ID</th>
                                <th>Shift Time</th>
                                <th>Date</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody id="attendanceResult">
                            <tr><td colspan="4">Loading attendance records...</td></tr>
                        </tbody>
                    </table>
                </div>
            `;
            fetchAttendance();
            break;

        case "createRoutine":
            display.innerHTML = `
                <div class="form-container">
                    <h3>Creating Routine</h3>
                    <label>Staff ID: <input type="text" id="identityNumber"></label><br>
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
                    <table class="styled-table">
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
                <div id="formSelection" class="form-container">
                    <button onclick="toggleForm('salaryMonitoring')">Staff Leaves Monitoring</button>
                    <button onclick="toggleForm('routineMonitoring')">Routine Monitoring</button>
                    <button onclick="toggleForm('updateSchedule')">Update Schedule</button>
                    <button onclick="toggleForm('deleteSchedule')">Delete Schedule</button>
                </div>

                <div id="salaryMonitoring" class="form-container hidden">
                    <button id="backButton" class="hidden" onclick="goBack()">← Back</button>
                    <h4>Staff Leaves Monitoring</h4>
                    <label>Enter staff Id: <input type="text" id="salaryIdentityNumber" autocomplete="off"></label>
                    <button onclick="fetchSalaryManager()">Check</button>
                    <p id="leaveCount"></p>
                </div>

                <div id="routineMonitoring" class="form-container hidden">  
                    <button id="backButton" class="hidden" onclick="goBack()">← Back</button>
                    <h4>Routine Monitoring</h4>
                    <label>Staff ID: <input type="text" id="identityNumber" autocomplete="off"></label>
                    <button onclick="fetchRoutineMonitoring()">Check Schedule</button>
                    <div id="routineMonitoringTable" class="table-container">
                        <h4>Routine Schedule</h4>
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
                                <tr><td colspan="4">Enter staff ID and click 'Check Schedule'.</td></tr>
                            </tbody>
                        </table>
                    </div>
                </div>

                <div id="updateSchedule" class="form-container hidden">
                    <button id="backButton" class="hidden" onclick="goBack()">← Back</button>
                    <h4>Update Schedule</h4>
                    <label>Staff ID: <input type="text" id="updateIdentityNumber"></label>
                    <label>Date: <input type="date" id="updateDate"></label>
                    <label>Shift Time: <input type="text" id="updateShiftTime"></label>
                    <label>Place: <input type="text" id="updatePlace"></label>
                    <button onclick="updateSchedule()">Update</button>
                    <p id="updateResult"></p>
                </div>

                <div id="deleteSchedule" class="form-container hidden">
                    <button id="backButton" class="hidden" onclick="goBack()">← Back</button>
                    <h4>Delete Schedule</h4>
                    <label>Enter Staff ID: <input type="text" id="deleteIdentityNumber"></label>
                    <button onclick="deleteSchedule()">Delete</button>
                    <p id="deleteResult"></p>
                </div>

            `;
            break;

        default:
            display.innerHTML = "<h3>Unknown Action</h3><p>Please select a valid option.</p>";
    }
}

//Attendance
function markAttendance(identityNumber, shiftTime) {
    const statusCell = document.getElementById(`attendanceStatus-${identityNumber}`);
    statusCell.innerHTML = `<p class="success">Marked</p>`;

    const normalizedShiftTime = shiftTime.replace(/\s/g, "").toUpperCase();

    fetch(`http://localhost:8080/api/attendance/mark?identityNumber=${identityNumber}&shiftTime=${normalizedShiftTime}`, {
        method: "POST"
    })
    .then(response => response.text())
    .then(data => {
        alert(`Attendance marked for Staff ID: ${identityNumber}`);
    })
    .catch(error => {
        alert("Error marking attendance.");
        console.error("Error:", error);
    });
}

function fetchAttendance() {
    fetch("http://localhost:8080/api/attendance/view")
        .then(response => {
            if (!response.ok) throw new Error("Failed to fetch attendance.");
            return response.json();
        })
        .then(data => {
            const resultDiv = document.getElementById("attendanceResult");

            if (!resultDiv) {
                console.error("Error: attendanceResult element not found.");
                return;
            }

            if (!data.length) {
                resultDiv.innerHTML = "<tr><td colspan='4'>No attendance records found.</td></tr>";
                return;
            }

            resultDiv.innerHTML = data.map(record => `
                <tr>
                    <td>${record.identityNumber}</td>
                    <td>${formatShiftTime(record.shiftTime) || "N/A"}</td>
                    <td>${record.date}</td>
                    <td>${record.status}</td>
                </tr>
            `).join("");
        })
        .catch(error => {
            console.error("Error fetching attendance:", error);
            alert("Failed to fetch attendance.");
        });
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
                const formattedTime = formatShiftTime(schedule.shiftTime);
                let row = `<tr>
                    <td>${schedule.identityNumber}</td>
                    <td>${schedule.place}</td>
                    <td>${formattedTime}</td>
                    <td>${schedule.date}</td>
                    <td id="attendanceStatus-${schedule.identityNumber}">
                        <button onclick="markAttendance('${schedule.identityNumber}', '${schedule.shiftTime}')">
                            Mark Present
                        </button>
                    </td>
                </tr>`;
                scheduleList.innerHTML += row;
            });
        })
        .catch(error => console.error("Error:", error));
}

// Add routine (send data to the backend to store in the list)
function addRoutine() {
    let identityNumber = document.getElementById("identityNumber").value;
    let place = document.getElementById("place").value;
    let shiftTime = document.getElementById("shiftTime").value;
    let date = document.getElementById("date").value;

    let schedule = { identityNumber, place, shiftTime, date };

    fetch("http://localhost:8080/api/schedules/add", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(schedule)
    })
    .then(response => response.text())
    .then(data => {
        alert("Routine added successfully!");
        setTimeout(fetchRoutineMonitoring, 0);
    })
    .catch(error => {
        console.error("Error:", error);
        alert("Failed to add routine: " + error.message);
    });
}

//Update schedule
function updateSchedule() {
    const identityNumber = document.getElementById("updateIdentityNumber").value;
    const date = document.getElementById("updateDate").value;
    const shiftTime = document.getElementById("updateShiftTime").value;
    const place = document.getElementById("updatePlace").value;

    if (!identityNumber || !date || !shiftTime || !place) {
        document.getElementById("updateResult").innerHTML = "<p style='color: red;'>All fields are required!</p>";
        return;
    }

    const updatedSchedule = {
        identityNumber,
        date,
        shiftTime,
        place
    };

    fetch(`http://localhost:8080/api/schedules/update/${identityNumber}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(updatedSchedule)
    })
    .then(response => response.text())
    .then(data => {
        document.getElementById("updateResult").innerHTML = `<p style='color: green;'>${data}</p>`;
    })
    .catch(error => {
        document.getElementById("updateResult").innerHTML = "<p style='color: red;'>Error updating schedule</p>";
        console.error("Error:", error);
    });
}

// Delete schedule
function deleteSchedule() {
    const identityNumber = document.getElementById("deleteIdentityNumber").value;

    if (!identityNumber) {
        alert("Please enter a Staff ID to delete.");
        return;
    }

    fetch(`http://localhost:8080/api/schedules/delete/${identityNumber}`, {
        method: "DELETE"
    })
    .then(response => response.text())
    .then(data => {
        document.getElementById("deleteResult").innerText = data;
    })
    .catch(error => {
        console.error("Error deleting schedule:", error);
        document.getElementById("deleteResult").innerText = "Error deleting schedule.";
    });
}

// Request leave (send data to the backend)
function requestLeave() {
    const identityNumber = document.getElementById("identityNumber").value;
    const startDate = document.getElementById("startDate").value;
    const endDate = document.getElementById("endDate").value;
    const reason = document.getElementById("reason").value;

    if (!identityNumber || !startDate || !endDate || !reason) {
        alert("Please fill all fields");
        return;
    }

    fetch("http://localhost:8080/api/leaves/request", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            identityNumber: identityNumber,
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
                    <tr id="leave-${leave.leaveId}">
                        <td>${leave.leaveId}</td>
                        <td>${leave.identityNumber}</td>
                        <td>${leave.startDate}</td>
                        <td>${leave.endDate}</td>
                        <td>${leave.reason}</td>
                        <td id="status-${leave.leaveId}">${leave.status}</td>
                        <td id="action-buttons-${leave.leaveId}">
                            ${leave.status === 'PENDING' ? `
                                <button id="approve-${leave.leaveId}" onclick="updateLeaveStatus(${leave.leaveId}, 'APPROVED')">Approve</button>
                                <button id="reject-${leave.leaveId}" onclick="updateLeaveStatus(${leave.leaveId}, 'REJECTED')">Reject</button>
                            ` : ''}
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
        const actionButtonsCell = document.getElementById(`action-buttons-${leaveId}`);
        const statusCell = document.getElementById(`status-${leaveId}`);
        
        actionButtonsCell.innerHTML = '';
        statusCell.innerHTML = status;

        alert(`Leave request ${status.toLowerCase()} successfully!`);
    })
    .catch(error => console.error("Error updating leave status:", error));
}

//Check leave request for staff
function fetchCheckLeaveRequestsForStaff() {
    const identityNumber = document.getElementById("identityNumberCheck").value.trim();
    if (!identityNumber) {
        alert("Please enter a Staff ID.");
        return;
    }

    fetch(`http://localhost:8080/api/leaves/staff/${identityNumber}`)
        .then(response => {
            if (!response.ok) throw new Error("HTTP error! Status: " + response.status);
            return response.json();
        })
        .then(data => {
            const myLeaveRequestsList = document.getElementById("myLeaveRequestsList");
            myLeaveRequestsList.innerHTML = "";

            let myLeaves = [];
            if (Array.isArray(data)) {
                myLeaves = data.filter(leave => leave.identityNumber == identityNumber);
            }

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
function fetchSalaryStaff(identityNumber) {
    fetch(`http://localhost:8080/api/salary/${identityNumber}`)
        .then(response => {
            if(!response.ok) throw new Error("Error fetching salary.");
            return response.json();
        })
        .then(data => {document.getElementById("salaryResult").innerHTML = `<strong> Your salary: $${data.toFixed(2)}</strong>`})
        .catch(error => {
            document.getElementById("salaryResult").innerHTML = `<p style="color:red;">Error fetching salary</p>`;
            console.error("Error: ", error);
        });
}

//Fetch salary for manager
function fetchSalaryManager() {
    let salaryIdentityNumber = document.getElementById("salaryIdentityNumber").value;
    if(!salaryIdentityNumber) {
        alert("Please enter a staff Id.");
        return;
    }

    fetch(`http://localhost:8080/api/leaves/count/${salaryIdentityNumber}`)
        .then(response => {
            if(!response.ok) throw new Error("Error fetching leave count.");
            return response.json();
        })
        .then(leaveCount => {
            document.getElementById("leaveCount").innerHTML = `<strong>Leaves taken: ${leaveCount}</strong>`;
            return fetch(`http://localhost:8080/api/salary/${salaryIdentityNumber}`);
        })
        .then(response => {
            if(!response.ok) throw new Error("Error calculating salary.");
            return response.json();
        })
        .then(data => {
            let salaryElement = document.getElementById("calculateSalaryResult");
            if (salaryElement) {
                salaryElement.innerHTML = `<strong>Calculated salary: $${data.toFixed(2)}</strong>`;
            } else {
                console.error("Element 'calculateSalaryResult' not found.");
            }
        })
        .catch(error => {
            document.getElementById("calculateSalaryResult").innerHTML = `<p style="color:red;">Error calculating salary.</p>`;
            console.error("Error: ", error);
        });
}

function fetchRoutineMonitoring() {
    const identityNumberField = document.getElementById("identityNumber"); 
    if (!identityNumberField) {
        console.error("Staff ID input is missing.");
        return;
    }

    const identityNumber = identityNumberField.value.trim();
    if (!identityNumber) {
        showMessage(identityNumberField, "Please enter a Staff ID.");
        return;
    }

    const routineMonitoringTable = document.getElementById("routineMonitoringTable");
    if (routineMonitoringTable) {
        routineMonitoringTable.style.display = "block"; // Ensure table is visible
    } else {
        console.error("Routine Monitoring Table not found!");
        return; // Exit if the table doesn't exist
    }

    fetch(`http://localhost:8080/api/schedules/filter?identityNumber=${identityNumber}`)
    .then(response => {
        if (!response.ok) throw new Error("Failed to fetch schedule data.");
        return response.json();
    })
    .then(data => {
        const routineMonitoringList = document.getElementById("routineMonitoringList");
        if (!routineMonitoringList) {
            console.error("Routine Monitoring List not found!");
            return;
        }

        routineMonitoringList.innerHTML = "";

        if (data.length === 0) {
            routineMonitoringList.innerHTML = "<tr><td colspan='4'>No schedules found for this staff.</td></tr>";
            return;
        }

        data.forEach(schedule => {
            const row = `
                <tr>
                    <td>${schedule.identityNumber}</td>
                    <td>${schedule.place}</td>
                    <td>${schedule.shiftTime}</td>
                    <td>${schedule.date}</td>
                </tr>`;
            routineMonitoringList.innerHTML += row;
        });
    })
    .catch(error => console.error("Error fetching routine monitoring data:", error));
}

//Timezone setting
function formatShiftTime(timeString) {
    if (!timeString) return "N/A";

    const [hours, minutes] = timeString.split(":");
    const date = new Date();
    date.setHours(hours, minutes);

    return date.toLocaleTimeString("en-US", {
        hour: '2-digit',
        minute: '2-digit',
        hour12: true,
        timeZone: 'Asia/Bangkok'
    });
}

// Logout function
function logout() {
    localStorage.removeItem("userRole");
    localStorage.removeItem("identityNumber");
    location.reload(); // Refresh to reset UI
}