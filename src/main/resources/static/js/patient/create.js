window.addEventListener("load", function () {
  /*-----------------------------------------------*/
  /*--                  Nodos                    --*/
  /*-----------------------------------------------*/
  const form = document.querySelector("#add_new_patient");

  form.addEventListener("submit", function (e) {
    e.preventDefault();

    const patientData = {
      name: document.querySelector("#name_patient").value,
      surname: document.querySelector("#surname_patient").value,
      dni: document.querySelector("#dni").value,
      registrationDate: document.querySelector("#registration_date").value,
      addressDTO: {
        street: document.querySelector("#street").value,
        number: document.querySelector("#street_num").value,
        state: document.querySelector("#state").value,
      },
    };

    const url = "http://localhost:8081/patient/create";
    const settings = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(patientData),
    };

    fetch(url, settings)
      .then((response) => response.json())
      .then((data) => {
        let successAlert =
          '<div class="alert alert-success alert-dismissible">' +
          "<strong></strong> El paciente se agregó correctamente. </div>";

        document.querySelector("#response").innerHTML = successAlert;
        document.querySelector("#response").style.display = "block";
        resetForm();
      })
      .catch((error) => {
        let errorAlert =
          '<div class="alert alert-danger alert-dismissible">' +
          "<strong> Se ha producido un error intente nuevamente</strong> </div>";

        document.querySelector("#response").innerHTML = errorAlert;
        document.querySelector("#response").style.display = "block";
        resetForm();
      });
  });

  function resetForm() {
    document.querySelector("#name_patient").value = "";
    document.querySelector("#surname_patient").value = "";
    document.querySelector("#dni").value = "";
    document.querySelector("#registration_date").value = "";
    document.querySelector("#street").value = "";
    document.querySelector("#street_num").value = "";
    document.querySelector("#state").value = "";
  }
});
