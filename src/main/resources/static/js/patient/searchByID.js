document.addEventListener("DOMContentLoaded", function () {
  const form = document.getElementById("search-byID");

  form.addEventListener("submit", function (event) {
    event.preventDefault(); // Evitar que el formulario se envíe de forma predeterminada

    const searchInput = document.getElementById("search").value;
    findBy(searchInput);
  });
});

function findBy(id) {
  const url = "http://localhost:8081/patient/" + id;
  const settings = {
    method: "GET",
  };

  fetch(url, settings)
    .then((response) => response.json())
    .then((data) => {
      console.log("Datos recibidos:", data);
      let patient = data.data.Patient;
      // Mostrar la información del paciente en algún lugar de la página
      const patientInfo = document.getElementById("patient-info");
      patientInfo.innerHTML = `
                <p><b>ID:</b> ${patient.id}</p>
                <p><b>Nombre:</b> ${patient.name}</p>
                <p><b>Apellido:</b> ${patient.surname}</p>
                <p><b>DNI:</b> ${patient.dni}</p>
                <p><b>Fecha de registro:</b> ${patient.registrationDate}</p>
                <p><b>Dirección:</b> ${patient.addressDTO.street}, ${patient.addressDTO.number}, ${patient.addressDTO.state}</p>
                <hr>`;
    })
    .catch((error) => {
      alert("Error: " + error);
    });
}
