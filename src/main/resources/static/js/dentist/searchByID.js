document.addEventListener("DOMContentLoaded", function () {
  const form = document.getElementById("search-byID");

  form.addEventListener("submit", function (e) {
    e.preventDefault();

    const searchInput = document.getElementById("search").value;
    findBy(searchInput);
  });
});

function findBy(id) {
  const url = "http://localhost:8081/dentist/" + id;
  const settings = {
    method: "GET",
  };

  fetch(url, settings)
    .then((response) => response.json())
    .then((data) => {
      let dentist = data.data.Dentist;

      const dentistInfo = document.getElementById("dentist-info");
      dentistInfo.innerHTML = `
                <p><b>ID:</b> ${dentist.id}</p>
                <p><b>Nombre:</b> ${dentist.name}</p>
                <p><b>Apellido:</b> ${dentist.surname}</p>
                <p><b>Matrícula:</b> ${dentist.enrollment}</p>
                <hr>`;
    })
    .catch((error) => {
      alert("El odontologo que buscas no se encuentra: " + error);
    });
}
