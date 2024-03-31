window.addEventListener("load", function () {
  const getAll = document.querySelector("#v-pills-getallPatients-tab");
  let listOk = false;

  getAll.addEventListener("click", function (e) {
    e.preventDefault();

    const url = "http://localhost:8081/patient/all";
    const settings = {
      method: "GET",
    };

    if (!listOk) {
      fetch(url, settings)
        .then((response) => response.json())
        .then((data) => {
          for (patient of data.data.Patients) {
            var table = document.getElementById("patientTable");
            var patientRow = table.insertRow();
            let tr_id = "tr_" + patient.id;
            patientRow.id = tr_id;

            let deleteButton =
              "<button" +
              " id=" +
              '"' +
              "btn_delete_" +
              patient.id +
              '"' +
              ' type="button" onclick="deleteBy(' +
              patient.id +
              ')" class="btn btn-danger btn_delete">' +
              "<i class='bi bi-trash3'></i>" +
              "</button>";

            let updateButton =
              "<button" +
              " id=" +
              '"' +
              "btn_id_" +
              patient.id +
              '"' +
              ' type="button" onclick="updateFindBy(' +
              patient.id +
              ')" class="btn btn-info btn_id">' +
              "<i class='bi bi-pencil-square'></i>" +
              "</button>";

            patientRow.innerHTML =
              '<td class="td_nombre">' +
              (patient.name ? patient.name.toUpperCase() : "") +
              "</td>" +
              '<td class="td_apellido">' +
              (patient.surname ? patient.surname.toUpperCase() : "") +
              "</td>" +
              '<td class="td_dni">' +
              (patient.dni ? patient.dni.toUpperCase() : "") +
              "</td>" +
              '<td class="td_fecha">' +
              (patient.registrationDate ? patient.registrationDate : "") +
              "</td>";

            if (patient.addressDTO) {
              patientRow.innerHTML +=
                '<td class="td_calle">' +
                (patient.addressDTO.street
                  ? patient.addressDTO.street.toUpperCase()
                  : "") +
                "</td>" +
                '<td class="td_numero">' +
                (patient.addressDTO.number ? patient.addressDTO.number : "") +
                "</td>" +
                '<td class="td_estado">' +
                (patient.addressDTO.state
                  ? patient.addressDTO.state.toUpperCase()
                  : "");
            } else {
              patientRow.innerHTML +=
                '<td class="td_calle">' +
                "</td>" +
                '<td class="td_numero">' +
                "</td>" +
                '<td class="td_estado">';
            }

            patientRow.innerHTML +=
              "</td>" +
              "<td>" +
              updateButton +
              "</td>" +
              "<td>" +
              deleteButton +
              "</td>";
          }
          listOk = true;
        })
        .catch((e) => {
          console.error("ERROR:", e);
        });
    }
  });
});
