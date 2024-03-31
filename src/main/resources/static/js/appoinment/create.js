window.addEventListener('load', function(){
    const form = document.querySelector("#add_new_appointment");

    // Función para cargar los pacientes en el select del html
    function loadPatients() {
        fetch('/patient/all')
            .then(response => response.json())
            .then(data => {
                const patients = data.data.Patients;

                const patientSelect = document.getElementById('patientSelect');
                patientSelect.innerHTML = '<option selected>Selecciona un paciente</option>';
                patients.forEach(patient => {
                    const option = document.createElement('option');
                    option.value = patient.id;
                    option.textContent = patient.name + " " + patient.surname;
                    patientSelect.appendChild(option);
                });
            console.log(data);
            })
            .catch(error => console.error('Error loading patients:', error));
    }

    // Función para cargar odontólogos en el select del html
    function loadDentist() {
        fetch('/dentist/all')
            .then(response => response.json())
            .then(data => {
                const dentists = data.data.Dentists;

                const dentistSelect = document.getElementById('dentistSelect');
                dentistSelect.innerHTML = '<option selected>Selecciona un odontólogo</option>';
                dentists.forEach(dentist => {
                    const option = document.createElement('option');
                    option.value = dentist.id;
                    option.textContent = dentist.name + " " + dentist.surname;
                    dentistSelect.appendChild(option);
                });
            console.log(data);
            })
            .catch(error => console.error('Error loading dentists:', error));
    }

    // Llamar a las funciones para cargar pacientes y odontólogos cuando se carga la página
    loadPatients();
    loadDentist();

    form.addEventListener("submit", function(e){
        e.preventDefault();

        const appointmentData = {
                dateAppointment: document.querySelector("#date").value,
                patientDTO: {
                    id: document.querySelector("#patientSelect").value
                },
                dentistDTO: {
                    id: document.querySelector("#dentistSelect").value
                }
            };

        const url = '/appointment/create';
        const settings = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(appointmentData)
        };

        fetch(url, settings)
            .then(response => response.json())
            .then(data => {
                let successAlert = '<div class="alert alert-success alert-dismissible">' +
                    '<strong></strong> El turno se agregó correctamente. </div>';

                document.querySelector('#response').innerHTML = successAlert;
                document.querySelector('#response').style.display = "block";
                resetForm();
            })
            .catch(error => {
                let errorAlert = '<div class="alert alert-danger alert-dismissible">' +
                    '<strong> Se ha producido un error, inténtelo nuevamente.</strong> </div>';

                document.querySelector('#response').innerHTML = errorAlert;
                document.querySelector('#response').style.display = "block";
                resetForm();
            });
    });

    function resetForm(){
        document.querySelector('#date').value = "";
        document.querySelector('#patientSelect').selectedIndex = 0;
        document.querySelector('#dentistSelect').selectedIndex = 0;
    }
});
