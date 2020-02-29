var main = {
    init: function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });

        $('#btn-signup').on('click', function () {
            _this.signUp();
        });
    },
    save: function () {
        const data = {
            studyName: document.getElementById("studyName").value,
            leader: document.getElementById("leader").value,
            goal: document.getElementById("goal").value,
            duration: document.getElementById("duration").value,
            time: document.getElementById("time").value,
            currentStudyMate: 0,
            status: "INCRUIT",
        };

        $.ajax({
            type: 'POST',
            url: '/create-study/save',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('스터디가 생성되었습니다.');
            window.location.href = '/study';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    signUp: function () {
        let data = {
            email: document.getElementById('email').value,
            password: document.getElementById('password').value,
            name: document.getElementById('name').value,
            studentNumber: document.getElementById('studentNumber').value,
            role: "STUDENT",
        };

        $.ajax({
            type: 'POST',
            url: '/signup/request',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('회원가입에 성공했습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
};

main.init();