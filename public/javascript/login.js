$(function () {
    console.log("something")
    $("#button").click(function () {


        console.log("something")
        let data = { username: $("#username").val(), password: $("#password").val() };
        $.ajax({
            type: "POST",
            url: "@routes.AuthorizeController.loginFormSubmit()",
            data: JSON.stringify(data),
            contentType: "application/json",
            dataType: "json",
            success: function(response) {
                console.log(response);
            },
            error: function(xhr, textStatus, errorThrown) {
                console.log('Ошибка: ' + xhr.responseText);
            }
        });

        /*$.post("@routes.AuthorizeController.loginFormSubmit()", {}, function () {
                $("#usermsg").val("");
            });*/
        }
    );

})