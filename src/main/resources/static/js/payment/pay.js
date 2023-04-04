$(document).ready(function () {
    $('input[name="paymentMethod"]').change(function () {
        var paymentMethod = $('input[name="paymentMethod"]:checked').val();
        if (paymentMethod === "point") {
            $.ajax({
                url: "/payment/pay/point",
                type: "GET",
                success: function (point) {
                    $("#paymentDetails").html("Total Point: " + point.totalPoint);
                },
                error: function () {
                    alert("Failed to get point details");
                }
            });
        } else if (paymentMethod === "bank") {
            $.ajax({
                url: "/payment/pay/bank",
                type: "GET",
                success: function (banks) {
                    var bankNames = "";
                    $.each(banks, function (index, bank) {
                        bankNames += bank.name + "<br>";
                    });
                    $("#paymentDetails").html(bankNames);
                },
                error: function () {
                    alert("Failed to get bank account details");
                }
            });
        }
    });
    $("#paymentForm").submit(function (event) {
        event.preventDefault();
        var paymentMethod = $('input[name="paymentMethod"]:checked').val();
        var orderMenuId = $("#orderMenuId").val();
        var bankAccountId = $("#bankAccountId").val();
        var price = $("#price").val();
        $.ajax({
            url: "/payment/pay/" + orderMenuId,
            type: "POST",
            data: {
                paymentCode: paymentMethod,
                bankAccountId: bankAccountId,
                price: price
            },
            success: function (payment) {
                $("#paymentDetails").html("Payment successful. Payment Id: " + payment.paymentId);
            },
            error: function () {
                alert("Payment failed");
            }
        });
    });
});