function confirmDelete(id) {
  return confirm("¿Estás seguro de eliminar el odontólogo " + id + "?");
}

function deleteBy(id) {
  const url = "http://localhost:8081/dentist/delete/" + id;
  const settings = {
    method: "DELETE",
  };

  if (confirmDelete(id)) {
    fetch(url, settings)
      .then((response) => {
        if (response.ok) {
          let row_id = "#tr_" + id;
          document.querySelector(row_id).remove();
          return response.json();
        } else {
          return response.json().then((data) => Promise.reject(data)); // Rechazar la promesa con el cuerpo de la respuesta como error
        }
      })
      .then((data) => {
        // Manejar la respuesta exitosa
        console.log("Respuesta del servidor:", data);
      })
      .catch((error) => {
        console.error("Error del servidor:", error);
        // Manejar el error, incluyendo el mensaje de error del servidor
      });
  }
}
