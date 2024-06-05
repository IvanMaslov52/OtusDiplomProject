$(function (){
    $.get('@routes.ProjectController.getProjectsByUser()', function (data) {
        console.log(data)


        var arr = [];
        for (let i = 0; i < data.length; i++) {
            arr.push(
                {
                    "DT_RowId": data[i].project_id,
                    "name": data[i].name,
                    "description": data[i].description,
                    "status": data[i].status,
                    "role": data[i].role,
                    "position": data[i].position,
                    "more": '' +
                        "<a href='/getProjectInside/" + data[i].project_id + "' class='link'>Подробнее</a>"
                });
        }


        var table = $('#myTable').DataTable(
            {
                "columns": [
                    {
                        "title": "Название группы", "data": "name", "visible": true,
                    },
                    {
                        "title": "Описание группы", "data": "description", "visible": true,
                    },
                    {
                        "title": "Статус группы", "data": "status", "visible": true,
                    },
                    {
                        "title": "Роль в группе", "data": "role", "visible": true,
                    },
                    {
                        "title": "Ваш статус в группе", "data": "position", "visible": true,
                    },
                    {
                        "title": " ", "data": "more", "visible": true,
                    }
                ], "language": language(),
                data: arr,
                responsive: true
            });
    });
});