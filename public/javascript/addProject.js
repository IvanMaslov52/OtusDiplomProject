$(function () {
    $("#addGroup").validate
    ({
        rules: {
            name: {
                required: true,
                minlength: 4,
                maxlength: 20
            },
            description: {
                required: true,
            }
        },
        messages: {
            name: {
                required: 'Название не может быть пустым',
                maxlength: 'Название не может быть больше 20 символов',
                minlength: 'Название не может быть меньше  4 символов'
            },
            description: {
                required: 'Описание не может быть пустым',
            }
        },errorPlacement: function (error, element) {
            if (element.attr("name") == "name")
            {
                $("#spanName").text(error.text());
            }
            if (element.attr("name") == "description")
            {
                $("#spanDescription").text(error.text());
            }
        }
    });
});