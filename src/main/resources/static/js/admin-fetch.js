document.querySelectorAll("a[href*='/admin/member/change-role-']")
    .forEach(element => {
        element.addEventListener('click', element =>{
            element.preventDefault();
            const url = element.target.href;

            fetch(url)
                .then(res => res.text())
                .then(data => {
                    document.getElementById("member-" + url.split("/")[6]).innerHTML = data;
                });

        })
    });

document.querySelectorAll("a[href*='/admin/member/fire']")
    .forEach( element => {
        element.addEventListener('click', element => {
            element.preventDefault();
            const url = element.target.href;

            fetch(url)
                .then(res => res.text())
                .then(data => {
                    if (data !== "-1") {
                        window.location.href="/admin/"
                    }
                });

        })
    });
