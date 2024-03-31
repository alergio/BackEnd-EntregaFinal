document.addEventListener("DOMContentLoaded", function () {
  const form = document.getElementById("search-byID");

  form.addEventListener("submit", function (e) {
    e.preventDefault();

    const searchInput = document.getElementById("search").value;
    findBy(searchInput);
  });
});

function findBy(id) {
  const url = "http://localhost:8081/appointment/" + id;
  const settings = {
    method: "GET",
  };

  fetch(url, settings)
    .then((response) => response.json())
    .then((data) => {
      let appointment = data.data.Appointment;

      const appointmentInfo = document.getElementById("appointment-info");
      appointmentInfo.innerHTML = `
                <p><b>ID:</b> ${appointment.id}</p>
                <p><b>Fecha y hora:</b> ${appointment.dateAppointment}</p>
                <p><b>Paciente:</b> ${appointment.patientDTO.name + " " + appointment.patientDTO.surname}</p>
                <p><b>Odontologo:</b> ${appointment.dentistDTO.name + " " + appointment.dentistDTO.surname}</p>
                <hr>`;
    })
    .catch((error) => {
      alert("El odontologo que buscas no se encuentra: " + error);
    });
}
