var main = {
    init: function () {
        var _this = this;
        var doubleSubmitFlag = true;

        $('#btn-register').on('click', function() {
            if (doubleSubmitFlag) {
                doubleSubmitFlag = false;
                _this.register();
            }
        });

        $('#btn-save').on('click', function () {
            if (_this.isEmpty(['studyName', 'goal'])) {
                alert("스터디 주제와 목표를 작성해주세요!");
            } else if (doubleSubmitFlag) {
                doubleSubmitFlag = false;
                _this.save();
            }
        });

        $('#btn-signup').on('click', function () {
            if (_this.isEmpty(['email', 'password', 'name', 'studentNumber'])) {
                alert("모든 정보를 입력해주세요!");
            } else if (_this.isNumeric('studentNumber')) {
                alert('학번은 숫자만 입력할 수 있습니다.');
            } else if (doubleSubmitFlag) {
                doubleSubmitFlag = false;
                _this.signUp();
            }
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

        const data = {
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
        }).done(function (response) {

            if (response === -1) {
                alert('이미 존재하는 메일주소입니다!');
                window.location.href = "/signup"
            } else if (response === -2) {
                alert('이미 가입한 학번입니다!');
                window.location.href = "/signup"
            } else {
                alert('회원가입에 성공했습니다.');
                window.location.href = '/';
            }
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    register : function () {
        const idx = document.getElementById("id").value;

        $.ajax({
            type: 'GET',
            url: '/study/register/' + idx,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function (response) {

            if (response === -1) {
                alert("이미 신청한 스터디입니다!!");
                return;
            } else if (response === -2) {
                alert("회원가입 후 이용할 수 있습니다!!");
            }

            alert('신청완료');
            window.location.href = '/';

        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    isEmpty: function (inputNameArray) {
        return inputNameArray.some(name => document.getElementById(name).value === "");
    },
    isNumeric: function (studentNumber) {
        return !isNaN(parseInt(document.getElementById(studentNumber.value)));
    },
};

main.init();