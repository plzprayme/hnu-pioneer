var main = {
    init: function () {
        var _this = this;
        var doubleSubmitFlag = true;

        $("#checkPassword").on('keyup', function () {
            if ($('#password').val() === $('#checkPassword').val()) {
                document.getElementById("isCorrect").innerHTML = "";
            } else {
                document.getElementById("isCorrect").innerHTML = "비밀번호가 일치하지 않습니다!";
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

        $("div[name*='study-delete']").click(function (e) {
            if (doubleSubmitFlag) {
                doubleSubmitFlag = false;
                _this.delete($(this));
            }
        });

        $("div[name*='study-status-']").click(function (e) {
            if (doubleSubmitFlag) {
                doubleSubmitFlag = false;
                _this.updateStatus($(this));
            }
        });

        $("div[name*='study-work']").click(function (e) {
            e.preventDefault();
            if (doubleSubmitFlag) {
                doubleSubmitFlag = false;
                _this.registerOrUnregister($(this));
            }
        });

        $("form[name='form-study']").submit(function (e) {
            e.preventDefault();
            if (doubleSubmitFlag) {
                doubleSubmitFlag = false;
                _this.register($(this));
            }
        });

        $('#btn-signup').on('click', function () {
            if (_this.isEmpty(['email', 'password', 'name', 'studentNumber'])) {
                alert("모든 정보를 입력해주세요!");
            } else if (_this.isNumeric('studentNumber')) {
                alert('학번은 숫자만 입력할 수 있습니다.');
            } else if (document.getElementById('isCorrect').innerHTML !== "") {
                alert('비밀번호가 일치하지 않습니다!');
            } else if (doubleSubmitFlag) {
                doubleSubmitFlag = false;
                _this.signUp();
            }
        });

        $("#btn-forgot-id").on('click', function () {
            if (_this.isEmpty(['studentNumber'])) {
                alert("학번을 입력해주세요!");
            } else if (doubleSubmitFlag) {
                doubleSubmitFlag = false;
                _this.forgotIdRequest();
            }
        });

        $("#btn-changePassword").on('click', function () {
            if (_this.isEmpty(['password'])) {
                alert("변경할 비밀번호를 입력해주세요!");
            } else if (document.getElementById('isCorrect').innerHTML !== "") {
                alert('비밀번호가 일치하지 않습니다!');
            } else if (doubleSubmitFlag) {
                doubleSubmitFlag = false;
                _this.changePassword();
            }
        });

        $("#btn-forgot-password").on('click', function () {
            if (_this.isEmpty(['email'])) {
                alert("학번을 입력해주세요!");
            } else if (doubleSubmitFlag) {
                doubleSubmitFlag = false;
                _this.forgotPasswordRequest();
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
            url: '/api/v1/studies',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('저장완료');
            window.location.href = '/studies';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update: function (_this) {
        const data = {
            studyName: document.getElementById("studyName").value,
            goal: document.getElementById("goal").value,
            duration: document.getElementById("duration").value,
            time: document.getElementById("time").value,
        };

        $.ajax({
            type: 'PUT',
            url: _this.attr("href"),
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('저장완료');
            window.location.href = '/study';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete: function (_this) {
        $.ajax({
            type: "DELETE",
            url: _this.attr('id'),
        }).done(function (response) {
            if (response < 0) {
                alert("삭제 실패");
                window.location.href = '/';
            }

            alert('삭제 되었습니다.');
            window.location.href = '/studies/own';
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
            url: '/api/v1/members',
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
            alert("error");
            alert(JSON.stringify(error));
        });
    },
    register: function (_this) {
        $.ajax({
            type: "PUT",
            url: _this.attr('action'),
        }).done(function (response) {
            if (response === -1) {
                alert("이미 신청한 스터디입니다!!");
                return;
            } else if (response === -2) {
                alert("회원가입 후 이용할 수 있습니다!!");
                return;
            }
            alert('신청완료');
            window.location.href = '/studies/own';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    registerOrUnregister: (_this) => {
        $.ajax({
            type: "PUT",
            url: _this.attr('id'),
        }).done(function (response) {
            if (response < 0) {
                alert("취소실패");
            }

            alert('취소완료');
            window.location.href = '/studies/own';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    forgotIdRequest: function () {
        const studentNumber = $('#studentNumber').val();
        $.ajax({
            type: "GET",
            url: "/api/v1/members/" + studentNumber + "/email"
        }).done(function (response) {
            if (response === "-1") {
                alert('가입하지 않은 학번입니다!');
                return;
            }

            alert("아이디는 " + response + "입니다.");
            window.location.href = "/signin";
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    forgotPasswordRequest: function () {
        const email = $('#email').val();
        $.ajax({
            type: "GET",
            url: "/api/v1/members/" + email
        }).done(function (response) {
            if (response === "-1") {
                alert('가입하지 않은 아이디입니다!');
                return;
            }
            window.location.href = "/changepassword/" + email;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    changePassword: function () {
        const data = {
            email: document.getElementById('email').value,
            password: document.getElementById('password').value,
        };

        $.ajax({
            type: "PUT",
            url: "/api/v1/members/"+data.email+"/password",
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function (response) {
            if (response === -1) {
                alert('변경실패');
                return;
            }

            alert("변경완료");
            window.location.href = "/signin"
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    updateStatus: function(_this) {
        $.ajax({
            type: "PUT",
            url : _this.attr("id")
        }).done((response) => {
            alert("변경완료");
            window.location.href = "/studies/own";
        }).fail((error) => {
            alert(JSON.stringify(error));
        });
    },
    isEmpty: function (inputNameArray) {
        return inputNameArray.some(name => document.getElementById(name).value === "");
    },
    isNumeric: function (studentNumber) {
        return !isNaN(parseInt(document.getElementById(studentNumber.value)));
    }
};

main.init();