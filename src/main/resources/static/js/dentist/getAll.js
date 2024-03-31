window.addEventListener("load", function () {
  const getAll = document.querySelector("#v-pills-getallDentist-tab");
  let listOk = false;

  getAll.addEventListener("click", function (e) {
    e.preventDefault();

    const url = "http://localhost:8081/dentist/all";
    const settings = {
      method: "GET",
    };

    if (!listOk) {
      fetch(url, settings)
        .then((response) => response.json())
        .then((data) => {
          for (dentist of data.data.Dentists) {
            var table = document.getElementById("dentistTable");
            var dentistRow = table.insertRow();
            let tr_id = "tr_" + dentist.id;
            dentistRow.id = tr_id;

            let deleteButton =
              "<button" +
              " id=" +
              '"' +
              "btn_delete_" +
              dentist.id +
              '"' +
              ' type="button" onclick="deleteBy(' +
              dentist.id +
              ')" class="btn btn-danger btn_delete">' +
              "<i class='bi bi-trash3'></i>" +
              "</button>";

            let updateButton =
              "<button" +
              " id=" +
              '"' +
              "btn_id_" +
              dentist.id +
              '"' +
              ' type="button" onclick="updateFindBy(' +
              dentist.id +
              ')" class="btn btn-info btn_id">' +
              "<i class='bi bi-pencil-square'></i>" +
              "</button>";

            dentistRow.innerHTML =
              '<td class="td_nombre">' +
              dentist.name.toUpperCase() +
              "</td>" +
              '<td class="td_apellido">' +
              dentist.surname.toUpperCase() +
              "</td>" +
              '<td class="td_matricula">' +
              dentist.enrollment.toUpperCase() +
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
          console.error("errorrrrrrrr", e);
          e.alert("error al cargar la info");
        });
    }
  });
});
