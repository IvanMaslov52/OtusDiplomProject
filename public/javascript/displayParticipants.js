$(function (){
    $("#selectedElem").css("display", "none");
    $("#member").css("display", "none");
    $("#change").css("display", "none");
    var role = $('#changeProject').length > 0 ? "lead" : "another"

      if($("#role").val() == "lead")
      {

          $("#displayParticipants").append("<table id='myTable'  class=\"table   table-hover\">\n" +
              "            <thead>\n" +
              "            <th>Никнейм участника</th>\n" +
              "            <th>Роль в группе</th>\n" +
              "            <th>Статус</th>\n" +
              "            <th>Редактирование</th>\n" +
              "            <th>Удаление</th>\n" +
              "            </thead>\n" +
              "            <tbody></tbody>\n" +
              "        </table>");
      }
      else {
          $("#specialButton").css("display", "none");
          $("#displayParticipants").append("<table id='myTable'  class=\"table  table-hover\">\n" +
              "            <thead>\n" +
              "            <th>Никнейм участника</th>\n" +
              "            <th>Роль в группе</th>\n" +
              "            <th>Статус</th>\n" +
              "            </thead>\n" +
              "            <tbody></tbody>\n" +
              "        </table>");
      }


    $('.searchInput').bind("change keyup input click", function() {
        if(this.value.length >= 2){
            var value = this.value;
            $.post("searchInGroup.php",
                {"value": value,"group": $("#group").val()},
                function(data){
                    $(".searchResult").html(data).fadeIn();
                }
            );

        }
    });
    $(".searchResult").hover(function(){
        $(".searchInput").blur();
    })
    $(".searchResult").on("click", "li", function(){
        s_user = $(this).text().trim();
        $('#groupMembers option[value='+$(this).attr('id') +']').prop('selected', true);

        if ($("#" + $(this).text().trim()).length) {
        } else {
            $('#selectedElem').append("<li id='" + $(this).text().trim() + "'> " +  $(this).text().trim() + "</li>");
        }
        $("#selectedElem").css("display", "");
        $(".searchInput").val(s_user);
        $(".searchResult").fadeOut();


    });
    $("#selectedElem").on("click", "li", function () {
        $('#groupMembers option:contains("' + $(this).text().trim() + '")').prop('selected', false);
        $(this).remove();
        if ($("#selectedElem").children('li').length == 0) {

            $("#selectedElem").css('display', 'none');
        }
    });



    $("#member").on('submit', function (e) {
        e.preventDefault();
        $("#error").text("");
    if($("#groupMembers").val()!= null){
        $.post("addUserInGroup.php",
            {"groupMembers": $("#groupMembers").val(),"group": $("#group").val()},
            function(data){
                data = JSON.parse(data);
                var table = $('#myTable').DataTable();
                table.clear().draw();
                update();
                $("#member").css("display", "none");
                $("#selectedElem").empty();
                $('#groupMembers option').prop('selected', false);
                $("#error").text("");
                $("#specialButton").css("display", "");
            }
        );}
    else
    {
        $("#error").text("У вас не выбран пользователь");
    }
    });


    $("#change").on('submit', function (e) {
        e.preventDefault();
            $.post("updateUserInGroup.php",
                {"id": $("#formId").val(),"role":$("#formRole").val(),"status":$("#formStatus").val(),"username":$("#formUsername").val(),"group": $("#group").val()},
                function(data){
                    data = JSON.parse(data);
                    var table = $('#myTable').DataTable();
                    table.clear().draw();
                   update();
                    $("#change").css("display", "none");
                    $("#specialButton").css("display", "");
                }
            );

    });



    $.post("displayParticipants.php",
        {"group": $("#group").val()},
        function(data){
            data = JSON.parse(data);
            if($("#role").val() == "lead")
            {
                var arr = [];
                for (let i = 0; i < data.length; i++) {
                    if(    data[i].role == "lead")
                    {
                        arr.push(
                            {
                                "DT_RowId": data[i].id,
                                "username": data[i].username,
                                "role": data[i].role,
                                "position": data[i].position,
                                "redac": '',
                                "delete": ''
                            });
                    }
                    else
                    {
                        arr.push(
                            {
                                "DT_RowId": data[i].id,
                                "username": data[i].username,
                                "role": data[i].role,
                                "position": data[i].position,
                                "redac": '<button type="button" onclick="change(' + data[i].id +')" class="imgButton" ><img class="icon" alt="logo_1" src="/image/recycle.png"/></button>',
                                "delete": '<button type="button" onclick="remove(' + data[i].id +')" class="imgButton" ><img class="icon" alt="logo_1" src="/image/delete.png"/></button>'
                            });
                    }

                }
                var table = $('#myTable').DataTable(
                    {
                        "columns": [
                            {
                                "title": "Никнейм участника", "data": "username", "visible": true,
                            },
                            {
                                "title": "Роль в группе", "data": "role", "visible": true,
                            },
                            {
                                "title": "Статус", "data": "position", "visible": true,
                            },
                            {
                                "title": "Редактирование", "data": "redac", "visible": true,
                            },
                            {
                                "title": "Удаление", "data": "delete", "visible": true,
                            }
                        ], "language": language(),
                        data: arr,
                        responsive: true,
                        "sDom": ''
                    });
            }
            else
            {

                var arr = [];
                for (let i = 0; i < data.length; i++) {
                    arr.push(
                        {
                            "DT_RowId": data[i].id,
                            "username": data[i].username,
                            "role": data[i].role,
                            "position": data[i].position
                        });
                }
                var table = $('#myTable').DataTable(
                    {
                        "columns": [
                            {
                                "title": "Никнейм участника", "data": "username", "visible": true,
                            },
                            {
                                "title": "Роль в группе", "data": "role", "visible": true,
                            },
                            {
                                "title": "Статус", "data": "position", "visible": true,
                            }
                        ], "language": language(),
                        data: arr,
                        responsive: true,
                        "sDom": ''

                    });
            }

        }
    );




    $.post("getAllUsers.php",
        {"group": $("#group").val()},
        function(data){
            data = JSON.parse(data);
            if($("#role").val() == "lead")
            {

                for (let i = 0; i < data.length; i++) {

                $("#groupMembers").append('<option  value="'+ data[i].id+'"> '+data[i].username+' </option>');

                }

            }
        }
    );
    $.post("displayParticipants.php",
        {"group": $("#group").val()},
        function(data){
            data = JSON.parse(data);
            if($("#role").val() == "lead")
            {
                for (let i = 0; i < data.length; i++) {
                    if(data[i].username != $("#nickname").val()){
                        $('#groupMembers option[value='+data[i].id +']').remove();
                    }
                }
            }
        }
    );
    $("#specialButton").click(function ()
        {
            $("#member").css("display", "");
            $("#change").css("display", "none");
            $("#specialButton").css("display", "none");
        }
    );
    $("#hide").click(function ()
        {
            $("#member").css("display", "none");
            $("#selectedElem").empty();
            $('#groupMembers option').prop('selected', false);
            $("#error").text("");
            $("#specialButton").css("display", "");
        }
    );
    $("#hideChange").click(function ()
        {
            $("#specialButton").css("display", "");
            $("#change").css("display", "none");
        }
    );






});
function change(id) {
    $("#specialButton").css("display", "none");
    $("#member").css("display", "none");
    $("#selectedElem").empty();
    $('#groupMembers option').prop('selected', false);
    $("#error").text("");

    $.post("getOneUser.php",
        {"id":id,"group": $("#group").val()},
        function(data){
            data = JSON.parse(data);
            $("#formId").val(id);
            $("#formLabel").text(data[0].username);
            $("#formUsername").val(data[0].username);
            $('#formRole option[value='+ data[0].role +']').prop('selected', true);
            $('#formStatus option[value='+ data[0].status +']').prop('selected', true);
            $("#change").css("display", "");
        }
    );


};
function remove(id)
{
    $.post("deleteUser.php",
        {"userId":id,"group": $("#group").val()},
        function(data){
            data = JSON.parse(data);
            var table = $('#myTable').DataTable();
            table.clear().draw();
            $("#groupMembers").append('<option  value="'+ data[0].id+'"> '+data[0].username+' </option>');
            update();
        }
    );
};


function update()
{
    $.post("displayParticipants.php",
        {"group": $("#group").val()},
        function(data){
            data = JSON.parse(data);
            var table = $('#myTable').DataTable();
            if($("#role").val() == "lead")
            {
                var arr = [];

                for (let i = 0; i < data.length; i++) {
                    if(    data[i].role == "lead")
                    {


                        table.row.add(
                            {
                                "DT_RowId": data[i].id,
                                "username": data[i].username,
                                "role": data[i].role,
                                "position": data[i].position,
                                "redac": '',
                                "delete": ''
                            }).draw();
                    }
                    else
                    {


                        table.row.add(
                            {
                                "DT_RowId": data[i].id,
                                "username": data[i].username,
                                "role": data[i].role,
                                "position": data[i].position,
                                "redac": '<button type="button" onclick="change(' + data[i].id +')" class="imgButton" ><img class="icon" alt="logo_1" src="/image/recycle.png"/></button>',
                                "delete": '<button type="button" onclick="remove(' + data[i].id +')" class="imgButton" ><img class="icon" alt="logo_1" src="/image/delete.png"/></button>'
                            }).draw();
                    }

                }

            }
            else
            {
                var arr = [];
                for (let i = 0; i < data.length; i++) {
                    table.row.add(
                        {
                            "DT_RowId": data[i].id,
                            "username": data[i].username,
                            "role": data[i].role,
                            "position": data[i].position
                        }).draw();
                }

            }

        }
    );
}