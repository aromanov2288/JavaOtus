$( document ).ready(function() {
    $(function () {
        $.get('/hw13_serverspring_war/api/all_users').done(function (users) {
            users.forEach(function (user) {
                $('tbody').append(`
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.name}</td>
                        <td>${user.age}</td>
                    </tr>
                `)
            });
        })
    });
});