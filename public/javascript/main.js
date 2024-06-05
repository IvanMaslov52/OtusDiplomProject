$(function () {
    $.get("@routes.ProjectController.getProjectsByUser()",
        {},
        function (data) {
            let max = 4
            for (let i = 0; i < data.length; i++) {
                console.log(data.length);
                if (i < max) {
                    var group = data[i];
                    var groupBlock = $("#group .rowDiv").clone(true);
                    groupBlock.appendTo("#table");
                    groupBlock.find(".name").text(group.name);
                    groupBlock.find(".status").text(group.partyStatus);
                }
            }
        }
    );
});