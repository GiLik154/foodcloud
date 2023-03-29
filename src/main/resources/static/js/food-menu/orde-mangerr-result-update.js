var orderList = []; // 수정

window.onload = function () {
    var foodTypeSelect = document.getElementById("selectResult");

    foodTypeSelect.addEventListener("change", function () {
        var selectedType = foodTypeSelect.value;
        var restaurantId = window.location.pathname.split('/')[3]; // 수정

        if (selectedType !== "") {
            fetch(`/restaurant/check/${restaurantId}/${encodeURIComponent(selectedType)}`) // 수정
                .then(response => response.json())
                .then(data => {
                    orderList = data;
                    populateFoodKindSelect(orderList);
                })
                .catch(error => console.error(error));
        } else {
            orderList = [];
            populateFoodKindSelect(orderList);
        }
    });

    function populateFoodKindSelect(orderList) { // 수정
        var orderListSelect = document.getElementById("orderListBody");
        orderListSelect.innerHTML = "";
        var html = "";
        orderList.forEach(function (list) {
            html += "<tr><td>" + list.location + "</td><td>" + list.count + "</td><td>" + list.time + "</td><td>" + list.result + "</td><td>" + list.price + "</td><td>" + list.payment + "</td><td><button onclick=\"updateOrderStatus('" + list.id + "', 'COOKING')\">요리 시작</button></td><td><button onclick=\"updateOrderStatus('" + list.id + "', 'PREPARED')\">요리 완료</button></td><td><button onclick=\"updateOrderStatus('" + list.id + "', 'DELIVERED')\">배달 시작</button></td><td><button onclick=\"updateOrderStatus('" + list.id + "', 'COMPLETED')\">배달 완료</button></td></tr>";
        });
        orderListSelect.innerHTML = html;
    }

    function updateOrderStatus(orderId, status) {
        fetch(`/restaurant/updateStatus/${orderId}/${status}`, {
            method: 'PUT'
        })
            .then(response => {
                if (response.ok) {
                    location.reload();
                } else {
                    throw new Error('Failed to update order status.');
                }
            })
            .catch(error => {
                console.error(error);
            });
    }
}