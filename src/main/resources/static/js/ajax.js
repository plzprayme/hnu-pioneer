let _this;
let httpRequest;

function init() {

    _this = this;

    if (document.getElementById('btn-save') !== null) {
        document.getElementById('btn-save')
            .addEventListener('click', () => {
                _this.save('POST', 'http://localhost:8080/create-study/save');
            });
    }

    if (document.getElementById('btn-signup') !== null) {
        document.getElementById('btn-signup')
            .addEventListener('click', () => {
                _this.signUp('POST', 'http://localhost:8080/signup/request');
            });
    }
}

function save(method, url) {
    httpRequest = initXHR();

    const data = {
        studyName: document.getElementById("studyName").value,
        leader: document.getElementById("leader").value,
        goal: document.getElementById("goal").value,
        duration: document.getElementById("duration").value,
        time: document.getElementById("time").value,
        currentStudyMate: 0,
        status: "INCRUIT",
    };

    httpRequest.open(method, url, true);
    httpRequest.setRequestHeader('dataType', 'json');
    httpRequest.setRequestHeader('Content-Type', 'application/json; charset=utf-8');
    // httpRequest.responseType = "document";
    httpRequest.send(JSON.stringify(data));

    httpRequest.addEventListener('load', () => {
        window.location.href = "/study";
    });
}

function signUp(method, url) {
    httpRequest = initXHR();

    const data = {
        email: document.getElementById('email').value,
        password: document.getElementById('password').value,
        name: document.getElementById('name').value,
        studentNumber: document.getElementById('studentNumber').value,
    };

    httpRequest.open(method, url, true);
    httpRequest.setRequestHeader('dataType', 'json');
    httpRequest.setRequestHeader('Content-Type', 'application/json; charset=utf-8');
    // httpRequest.responseType = "document";
    httpRequest.send(JSON.stringify(data));

    httpRequest.addEventListener('load', () => {
        window.location.href = "/#page-top";
    });
}

function initXHR() {
    httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        alert('XMLHTTP 인스턴스 생성 불가');
        return false;
    }

    return httpRequest;
}