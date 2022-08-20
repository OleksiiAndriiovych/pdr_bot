package org.pdr.utils;

import com.liqpay.LiqPay;
import org.json.simple.JSONObject;
import org.pdr.entity.Payment;

import java.util.HashMap;
import java.util.Map;

public class LiqPayUtil extends LiqPay {

    private final String serverUrl;

    public LiqPayUtil() {
        super(MyProperties.getLiqPayPublicKey(), MyProperties.getLiqPayPrivateKey());
        this.serverUrl = MyProperties.getServerUrl();
    }

    public String createUrlForPayment(Payment payment) {
        Map<String, String> params = new HashMap<>();
        params.put("version", "3");
        params.put("action", "pay");
        params.put("amount", "1");
        params.put("currency", "USD");
        params.put("server_url", serverUrl);
        params.put("description", "Payment");
        params.put("order_id", payment.getId() + "");
        params.put("sandbox", "1"); // enable the testing environment and card will NOT charged. If not set will be used property isCnbSandbox()

        // code from LiqPay.cnb_form(Map<String, String> params)
        String data = com.liqpay.LiqPayUtil.base64_encode(JSONObject.toJSONString(this.withSandboxParam(this.withBasicApiParams(params))));
        String signature = this.createSignature(data);
        return "https://www.liqpay.ua/api/3/checkout?data=" + data + "&signature=" + signature;
    }
}
