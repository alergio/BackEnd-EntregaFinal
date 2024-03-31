window.addEventListener("load", function () {
  const getAll = document.querySelector("#v-pills-getallAppointment-tab");
  let listOk = false;

  getAll.addEventListener("click", function (e) {
    e.preventDefault();

    const url = "http://localhost:8081/appointment/all";
    const settings = {
      method: "GET",
    };

    if (!listOk) {
      fetch(url, settings)
        .then((response) => response.json())
        .then((data) => {
          for (appointment of data.data.Appointments) {
            var table = document.getElementById("appointmentTable");
            var appointmentRow = table.insertRow();
            let tr_id = "tr_" + appointment.id;
            appointmentRow.id = tr_id;

            console.log("ESta es la info de turno: " + appointment);

            // let updateButton =
            //   "<button" +
            //   " id=" +
            //   '"' +
            //   "btn_id_" +
            //   appointment.id +
            //   '"' +
            //   ' type="button" onclick="updateFindBy(' +
            //   appointment.id +
            //   ')" class="btn btn-info btn_id">' +
            //   "<i class='bi bi-pencil-square'></i>" +
            //   "</button>";

            appointmentRow.innerHTML =
              '<td class="td_date">' +
              appointment.dateAppointment +
              "</td>" +
              '<td class="td_patient">' +
              appointment.patientDTO.name +
              " " +
              appointment.patientDTO.surname +
              "</td>" +
              '<td class="td_dentist">' +
              appointment.dentistDTO.name +
              " " +
              appointment.dentistDTO.surname +
              "</td>";
            // "<td>" +
            // updateButton +
            // "</td>" +
            // "<td>" +
            // deleteButton +
            // "</td>";
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
