package com.wen.gradua.config;

import java.io.FileWriter;
import java.io.IOException;

/**
 * 支付宝支付配置类
 */
public class AlipayConfig {
    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String APP_ID = "2021000117693332";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String MERCHANT_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCHYsJtHcRlEErGJs7F6DTzzYF0YLUUmtJEN5IlgdG0YdQ+7oSxlxFEgJmn+Vbx9BG1Weu6T7J9YWr2GRMdoUOxFkMOjUvYWwiMHMNL7mUr+y7m5EQIdVOcO4jc2oso8zneZhC0gcxaM0VYUvh9tyCCvainTf0OdSGluFHuYFsxUxtfiqUYRQz+a/nB5v6qNelO8/DS9eMAhO91n3QtmsmvotnfCTP7SiELOEzigjqJxaC9JfXgoAliVOMMIdIdqK9+bsC96svLvddCvt8P6uprY8PaPyo1RPp/hLGgFstfDmm/lRMeI5MpglQfqMg2nSrLQAjAkpqXt1LD2M+2jKwxAgMBAAECggEAed4DZ9eAvvyAYofyXyGb8M5PNmQK/GY02eUv8AVAsF/Tf2aTGY0DpSchqZt/kUKDYjx0PfyZjdVQRADkPSq51UFV0VaNLE8djYoinkoU1hc/6+UT/I0oWx0YIL20wILY14IGjYW9XoCCbnQZbIiYgjeZBijknsv/Ir6dObeQvv9WIr0FE1pbKWIDFqy6qFaBDgcQLMKW5QVbuLA1lq5O0DeUjI1mHNncp5mECMK+BnNb5mPegYfLFOb5H4isOeIQw/6j1bQ1NT7wwRA6rBfK5e1fHLc6qodVHxBcpgfUSfka+xV7u2uE0zCfMnbDeMiE3QbmRqn0o+d2nOa0kKL2NQKBgQDzCtzneYmBzXOrm/Lp9HKNdKmDz8nA9Ci4LxttmguLOwyDrNPFfLq5slXVnGRG+8WqsySf5HuqJHFwYYqkIq1Lb8otlxL45h68WT/azQBHslRh7YJDIqEzdUf5sr23v6daL5DB1Sf9W80dXmtqRWnWnvBHHM/BhfH2ouKYs0XkwwKBgQCOmo6TQrYvwT6V8lThcWFvswJS/lfzTg4GDEjIGwAERgOY/BmI0RJNbgIxjegCDVc7MKh7E8Tz1ysAdQ8z1hhRyIcgsivPk0UsYgV9esJwXCIGW0xTYiXmG+xBuefNJU8g7+VkBgF/tCjIHnTmWPvlyFbXa+ko+AdRaJsCd/sL+wKBgQC3PVJtxulQjxkNoodTsrSHSBTiR1BChRMKQqH4UXMwpvV5dV05ASA7D2S9p6rHBd3zJBuR3Y8LKWzJoREJTQfA32jcVJG+TKmRsex9x5/VbnNGXe6fNUW6aUs5zHRTTFEdos0OBuqej48m/GAaAHmakbxwsR1GN/nEzOvw2wDwRwKBgADiKDv1T9ef5Nlc9PWolCfy/fKxrK3PL/sNnG3Q7QEZxe/ur+HPGcPtCIpyDJvmpUGynXiVuMSh8pxAHWX+z4vCKEQllmvkx6rsuIf8eLPlfjh33FMv6ngweBg4JJ16u7DCi7NA7kVyQkZrGeJLbtQPl6oh+wHL94+jjEJJp/59AoGBAIU/rLzRVbDYOIwtUvQZLC47maZBstGwmv58kCddpTZDNTk+S4dpJ1Ab5BPK+Ckcdnkq0Xoo+E7+7lkQVOe0BcRcPHC+orA2bno3+hHklu6JLI6zct9HWk+Xr37HQpeQJjDcpMbUKRg8EsgwLB0Hn+qO+BpdvzOZiQ0NNUqYmdFy";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApevJdSI5y9p9fX8x6WxZwQFLWdvTVF1zmo4TxJnG/0KnwOg2XtdAQwsWf2eQMpHdvd1LG+H+5ztpN3YDymt9DkzVbVkqqwEvcoS8tl/8tCknbs80t18FIMcOJe/nFBvBty/MB6wfdU2mM+e04j3d0VtLXq9h/4r/PCIEkTp3QKRuJfjLPCJUF3FLIo25IvoFxSqXrtBVh6PjA7CSsSvIHQdoUTZ4CXfHJFMUnwD0tKILHf4iy/f7e0+wzC7t6/n2K7Fc01WQv/omyQCscvaG1XXMaJ34jj8POjYApc5tYKDSII5vNx4B0PoCPGOCjtY+uvd6KryFSRg2ZX95gvUtjQIDAQAB";
    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://127.0.0.1:8081/notify";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://127.0.0.1:8081/returnUrl";

    // 签名方式
    public static String SIGN_TYPE = "RSA2";

    // 字符编码格式
    public static String CHARSET = "utf-8";

    // 支付宝网关
    public static String GATEWAYURL = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String LOG_PATH = "D:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     *
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(LOG_PATH + "alipay_log_" + System.currentTimeMillis() + ".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
