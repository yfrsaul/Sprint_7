package util;

import pojo.Courier;
import pojo.CreateOrderRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class TestData {

    public static Courier randomCourier() {
        String s = UUID.randomUUID().toString().substring(0, 8);
        return new Courier("courier_" + s, "pass_" + s, "Saske");
    }

    public static CreateOrderRequest baseOrder(List<String> colors) {
        return new CreateOrderRequest(
                "Наруто",
                "Узумаки",
                "Коноха, кв. 555",
                "4",
                "+7 995 748 88 88",
                5,
                LocalDate.now().plusDays(1).toString(),
                "СААААСКЕЕЕЕЕ",
                colors
        );
    }
}

