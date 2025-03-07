document.addEventListener("DOMContentLoaded", function () {
    fetchStaffData();
});

async function fetchStaffData() {
    try {
        const response = await fetch("http://localhost:8080/staff/all");
        const staffList = await response.json();

        console.log("Staff Data:", staffList);

        const staffTableBody = document.getElementById("staffTableBody");
        staffTableBody.innerHTML = "";

        staffList.forEach(staff => {
            const row = `<tr>
                <td>${staff.id}</td>
                <td>${staff.name}</td>
                <td>${staff.role}</td>
                <td>${staff.shift}</td>
            </tr>`;
            staffTableBody.innerHTML += row;
        });
    } catch (error) {
        console.error("Error fetching staff data:", error);
    }
}
