$(function () {
    $("#friendsForm").css("display", "none");

    $('.who').bind("change keyup input click", function () {
        if (this.value.length >= 2) {
            var value = $('#who').val();
            $.post("search.php",
                {"value": value},
                function (data) {
                    console.log(data);
                    $(".search_result").html(data).fadeIn();
                }
            );

        }
    });
    $(".search_result").hover(function () {
        $(".who").blur();
    })
    $(".search_result").on("click", "li", function () {
        s_user = $(this).text().trim();
        $(".who").val(s_user);
        $(".search_result").fadeOut();
    });

    $("#friendsButton").click(function () {
        $("#mainDiv").empty();
        $("#friendsForm").css("display", "none");
        $.post("/findFriends", {}, function (data) {
            data = JSON.parse(data);
            if (data.length == 0) {
                $("#mainDiv").append("<div class='p-4 bold'> У вас пока нет друзей</div>");
            } else {
                for (let i = 0; i < data.length; i++) {
                    $("#mainDiv").append(
                        "<div class='myRow p-4'>" +
                        "<div class='center'>" +
                        "<div class=\"avatar-container\">" +
                        "            <div class=\"avatar\">" +
                        "                <img src='image/avatar.jpg' />" +
                        "            </div>\n" +
                        "        </div>" +
                        "<div>" +
                        "<span class='spanusername'>" + data[i].username + "</span>" +
                        "<form action='convChat.php' method='post' class='p-1'>" +
                        "<input type='hidden' name='username'  id='username' value='" + data[i].username + "'    >" +
                        "<button type='submit' class='exitButton'>Написать сообщение</button>" +
                        "</form>" +
                        "</div>" +
                        "</div>" +
                        "<div class='dropdown'> " +
                        "<button class='dropbtn'>Подробнее</button>" +
                        "<div class='dropdown-content'>" +
                        "<form action='deleteFriends.php' method='post'>" +
                        "<div class='myRow'>" +
                        "<input type='hidden' name='username'  id='username' value='" + data[i].username + "'    >" +
                        "<button type='submit' class='exitButton'>Удалить друга</button>" +
                        "</div>" +
                        "</form>" +
                        "</div>" +
                        "</div>"
                    );
                }
            }
        });
    });
    $("#requestFriendsButtton").click(function () {
            $("#friendsForm").css("display", "none");
            $("#mainDiv").empty();
            $.post("findFriends.php", {},
                function (data) {
                    if (data.length == 0) {
                        $("#mainDiv").append("<div class='p-4 bold'> У вас пока нет заявок</div>");
                    } else {
                        for (let i = 0; i < data.length; i++) {
                            $("#mainDiv").append(
                                "<div class='myRow p-4'>" +
                                "<div class='center'>" +
                                "<div class=\"avatar-container\">" +
                                "            <div class=\"avatar\">" +
                                "                <img src='image/avatar.jpg' />" +
                                "            </div>\n" +
                                "        </div>" +
                                "<div>" +
                                "<span class='spanusername'>" + data[i].username + "</span>" +
                                "<div class='p-3'>Хочет добавить вас в друзья</div>" +
                                "</div>" +
                                "</div>" +
                                "<div class='dropdown'> " +
                                "<button class='dropbtn'>Подробнее</button>" +
                                "<div class='dropdown-content'>" +
                                "<form action='acceptedFriends.php' method='post'>" +
                                '<input type="hidden" value="' + data[i].username + '" id="accepted" name="accepted">' +
                                "<button type='submit' class='exitButton'>Добавить</button>" +
                                "</form>" +
                                " <form action='deniedFriends.php' method='post'>" +
                                '<input type="hidden" value="' + data[i].username + '" id="denied" name="denied">' +
                                "<button type='submit' class='exitButton'>Отказать</button>" +
                                "</form>" +
                                "</div>" +
                                "</div>"
                            );
                        }
                    }
                });
        }
    );
    $("#addFriendsButton").click(function () {
        $("#mainDiv").empty();
        $("#friendsForm").css("display", "");

    });


    $.post("/findFriends", {}, function (data) {
        if (data.length == 0) {
            $("#mainDiv").append("<div class='p-4 bold'> У вас пока нет друзей</div>");
        } else {
            for (let i = 0; i < data.length; i++) {
                $("#mainDiv").append(
                    "<div class='myRow p-4'>" +
                    "<div class='center'>" +
                    "<div class=\"avatar-container\">" +
                    "            <div class=\"avatar\">" +
                    "                <img src='image/avatar.jpg' />" +
                    "            </div>\n" +
                    "        </div>" +
                    "<div>" +
                    "<span class='spanusername'>" + data[i].username + "</span>" +
                    "<form action='convChat.php' method='post' class='p-1'>" +
                    "<input type='hidden' name='username'  id='username' value='" + data[i].username + "'    >" +
                    "<button type='submit' class='exitButton p-3'>Написать сообщение</button>" +
                    "</form>" +
                    "</div>" +
                    "</div>" +
                    "<div class='dropdown'> " +
                    "<button class='dropbtn'>Подробнее</button>" +
                    "<div class='dropdown-content'>" +
                    "<form action='deleteFriends.php' method='post'>" +
                    "<div class='myRow'>" +
                    "<input type='hidden' name='username'  id='username' value='" + data[i].username + "'    >" +
                    "<button type='submit' class='exitButton p-3'>Удалить друга</button>" +
                    "</div>" +
                    "</form>" +
                    "</div>" +
                    "</div>"
                );
            }
        }
    });
});