window.addEventListener("load", function () {
  const form = document.querySelector("#update_dentist_form");

  form.addEventListener("submit", function (e) {
    e.preventDefault();

    let dentistId = document.querySelector("#dentist_id").value;

    const dentistData = {
      id: document.querySelector("#dentist_id").value,
      name: document.querySelector("#name_dentist_up").value,
      surname: document.querySelector("#surname_dentist_up").value,
      enrollment: document.querySelector("#enrollment_up").value,
    };

    const url = "http://localhost:8081/dentist/update";
    const settings = {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(dentistData),
    };

    fetch(url, settings)
      .then((response) => response.json())
      .then(() => {
        document.querySelector("#div_dentist_updating").style.display = "none";
      });
  });
});

function updateFindBy(id) {
  const url = "http://localhost:8081/dentist/" + id;
  const settings = {
    method: "GET",
  };

  fetch(url, settings)
    .then((response) => response.json())
    .then((data) => {
      let dentist = data.data.Dentist;
      document.querySelector("#dentist_id").value = dentist.id;
      document.querySelector("#name_dentist_up").value = dentist.name;
      document.querySelector("#surname_dentist_up").value = dentist.surname;
      document.querySelector("#enrollment_up").value = dentist.enrollment;

      //el formulario por default esta oculto y al editar se habilita
      document.querySelector("#div_dentist_updating").style.display = "block";
    })
    .catch((error) => {
      alert("Error: " + error);
    });
}
