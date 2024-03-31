window.addEventListener("load", function () {
  /*-----------------------------------------------*/
  /*--                  Nodos                    --*/
  /*-----------------------------------------------*/
  const form = document.querySelector("#add_new_dentist");

  form.addEventListener("submit", function (e) {
    e.preventDefault();

    const dentistData = {
      name: document.querySelector("#name_dentist").value,
      surname: document.querySelector("#surname_dentist").value,
      enrollment: document.querySelector("#enrollment").value,
    };

    const url = "http://localhost:8081/dentist/create";
    const settings = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(dentistData),
    };

    fetch(url, settings)
      .then((response) => response.json())
      .then((data) => {
        let successAlert =
          '<div class="alert alert-success alert-dismissible">' +
          "<strong></strong> El odontólogo se agregó correctamente. </div>";

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
    document.querySelector("#name_dentist").value = "";
    document.querySelector("#surname_dentist").value = "";
    document.querySelector("#enrollment").value = "";
  }
});
