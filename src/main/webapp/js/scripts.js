function validate() {
    var result = true;
    var name = $('#name').val();
    var phone = $('#phone').val();
    if (name == '') {
        result = false;
        alert('Please, enter your Name');
    }
    if (phone == '') {
        result = false;
        alert('Please, enter your Phone Number');
    }
    return result;
}

function fillHall() {
    $.ajax({
        url: '/hall',
        method: 'POST',
        complete: function (response) {
            var hall = JSON.parse(response.responseText);
            var current_row = 1;
            var result = "<thead><tr><th style=\"width: 120px;\">"
                + "Ряд / Место</th><th>1</th><th>2</th><th>3</th></tr></thead><tbody><tr><th>1</th>";
            for (var i = 0; i < hall.length; i++) {
                var id = hall[i].id;
                var row = hall[i].row;
                var place = hall[i].place;
                var availability = hall[i].availability;
                if (row != current_row) {
                    result += "</tr><tr><th>" + row + "</th>";
                    current_row = row;
                }
                result += "<td><input type = \"radio\" name = \"place\" value =\"" + id + "\">"
                    + "Ряд " + row + ", Место " + place + " " + availability + "</td>";
            }
            result += "</tr></tbody>";
            $('#place').html(result);
        }
    });
}

function getPlace(id) {
    $.ajax({
        url: '/payment',
        method: 'GET',
        data: "place_id=" + id,
        complete: function (response) {
            var place = JSON.parse(response.responseText);
            var result = "Вы выбрали ряд " + place.row + " место " + place.place + ", Сумма : 500 рублей.";
            $('#place_details').html(result);
        }
    });
}