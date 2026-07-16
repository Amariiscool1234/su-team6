fetch("http://localhost:8081/leagues")
    .then(response => response.json())
    .then(data => {
        console.log(data);
    })
    .catch(error => console.error(error));