$(function () {
    $("#send").click(function () {
            let text = $("#usermsg").val();
            $.post("addingChat.php", {"receiver": $("#receiverUsername").text(), "text": text}, function () {
                $("#usermsg").val("");
                display();
            });
        }
    );
    display();
    setInterval(function () {
        $.post("getMessage.php", {"receiver": $("#receiverUsername").text()}, function (data) {
            data = JSON.parse(data);
            $("#chatbox").empty();
            for (let i = 0; i < data.length; i++) {
                if (data[i].sender == $("#senderId").text()) {
                    $("#chatbox").append("<div> you : " + data[i].message + " </div>");
                } else {
                    $("#chatbox").append("<div>" + $("#receiverUsername").text() + " : " + data[i].message + " </div>");
                }
            }

        });
    }, 3000);

});

function display() {
    $.post("getMessage.php", {"receiver": $("#receiverUsername").text()}, function (data) {
        data = JSON.parse(data);
        $("#chatbox").empty();
        for (let i = 0; i < data.length; i++) {
            if (data[i].sender == $("#senderId").text()) {
                $("#chatbox").append("<div> you : " + data[i].message + " </div>");
            } else {
                $("#chatbox").append("<div>" + $("#receiverUsername").text() + " : " + data[i].message + " </div>");
            }
        }

    });
};