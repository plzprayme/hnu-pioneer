document.querySelectorAll("div[name*='admin-member-put']")
    .forEach(element => {
        element.addEventListener('click', element => {
            const url = element.target.id;

            fetch(url, { method: 'PUT' })
                .then(res => res.text())
                .then(data => {
                    document.getElementById("member-" + url.split("/")[5]).innerHTML = data;
                })
        })
    });

document.querySelectorAll("div[name*='admin-study-put']")
    .forEach(element => {
        element.addEventListener('click', element => {
            const url = element.target.id;

            fetch(url, { method: 'PUT' })
                .then(res => res.text())
                .then(data => {
                    document.getElementById("study-" + url.split("/")[5]).innerHTML = data;
                })
        })
    });

document.querySelectorAll("div[name*='admin-delete']")
    .forEach( element => {
        element.addEventListener('click', element => {
            const url = element.target.id;

            fetch(url, {method: 'DELETE'})
                .then(res => res.text())
                .then(data => {
                    if (data !== "-1") {
                        window.location.href="/admin/"
                    }
                });

        })
    });
