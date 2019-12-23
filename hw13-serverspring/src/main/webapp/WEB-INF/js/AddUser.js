$( document ).ready(function() {
    $('#addForm').submit(function (event) {
        event.preventDefault();
        $.ajax(
            '/hw13_serverspring_war/api/add_user',
            {
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({
                    name: $('#name').val(),
                    age: $('#age').val()
                }),
                success: function (result) {
                    window.location.assign('/hw13_serverspring_war/all_users');
                }
            }
        );
    });
});