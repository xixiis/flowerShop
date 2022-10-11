package com.wen.config;

import java.io.FileWriter;
import java.io.IOException;

/**
 * 支付宝支付配置类
 */
public class AlipayConfig {
    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String APP_ID = "2021000117693332";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String MERCHANT_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCHObS89y6mHKVY1WpLenvlUEWhqXAb5zAFqm9LOsOUEmqsgezF05B5QDAy/I7exAiKaanu2ciikCodlBnY/B0JH90JC6MzzSihs2v2MY0wEeANLStP6bTjkCuSDLwObZxNTPEF0VBlqi4jzLSG2XJvkczI2strK8jI06tnAMUY1cS9CIe1ICmHLkd55ABwn4WDuoyQo589/Qm646KUmkcAeYfFo5EbaRZtvJlKcCuFQMWvlAOBRG1JJm5Tnfg3XJ41698+BNxQplbVr7XIxUoWnHqBffEJE5/HSuZUpcQv75SMbAa0DT3Lf6W3cEdIdSGznP+UMH9e9NizgCVrwlmzAgMBAAECggEAYcejXmSqUakzEro3Q17NCfLydsRLcZHJUr97/H0AgSiUBCtqp12lAVgdwjCHqqM7vcJMA49tGcd+/KZjzlf3MurcbLcCiumVsN9JeNs5yYGTblO0FvPaPha4usIElbZCqE6zlc5WAT1+YZqbC0tfCTf3SWGUZ+lgJX+RWEjhPGP/MJrDsazEz1JVXnCuNwVrWpdP4IdnmA5EX8UGwIHhxOlZ46JB4maj4g9h9YQftbfgX4aopJtwuAoXJmti7toy1UzguSnP/MvxOaMIQebUh9f9y3roUOaFLSOt2R/xJX3rRMJrWkdj+qytdMYGiLg1FgHRWWBC53CE2OcMfk5GOQKBgQDWiBT8lkZj7Vssgkw7kfUK7aez0tBCmOax/Ba9JDLZLflRyVPF0IMiJQvFqbEu2s3OvEciVaYqaffxyZRKDmzRRer3otm/LGAIKV79sOG1htn7a3+9B0ieetqeJ61eu3rsSlzhexSlM3zG0++niEJFEyZWTHEYIwZomCqa4PIVpQKBgQChXTlm2TRO9EFFoPWKXd8AXRw9BYEptLznPxZUXOTEV+esRR7Q3o/PlAxuEEnBPPpRsVGkr+NbkU5UsMogzUt50A//f9d6TZAxEmcbIV0hbazTPh2wC6yfjgxAWV2pncEerd13lEMz+rhen85f7+qzn6zitXuKZH+1pq6LQ6MCdwKBgEB7yOsfdGsG5lfblnR1a9VsF/N6U3UhJn6rSl5x6gLTydQmktKr2S7DOeWhGwv9Ys1YVH1ihz7KeWeo8rTL3zOMM5NZ1N+cxATQAqh4qW956D2lU1ELRm20ec/+FyOZcNpjg8WWzF7XuhK9k5Sk5V6D4gsgkQHzTTLVQkEpijqtAoGAZoK0zrO52VauiK9ijY5o9vAv2kw+6+HYCAg6BUomQt2dXgFjPEnHjpV8+Ab88/frBi7hsP/ULf23h3HwVrpU8oPNRgFPQHE54xLP0Zp2caAdJO3XKlmgRpSVD+Q17mU7pGPLyAW2U7U5n1NiZewibA5KZJPqcda6kwna5LoJOq0CgYEAkHX6ATn51uUZoeO26STyfMvX1LXzN02i3uDm2rOLJ/eTYCctowXLO9gjygxrbvPERG/hObaz1oVw/NJ8ymzhqF3voJxoY0KlIs6vLLaZfW76pZMQ4mWz10v6lJMiWxpa5kVpF26UKdAgyTyY+SU4rVjXBL3xMmagDNkor1gFmNc=";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApevJdSI5y9p9fX8x6WxZwQFLWdvTVF1zmo4TxJnG/0KnwOg2XtdAQwsWf2eQMpHdvd1LG+H+5ztpN3YDymt9DkzVbVkqqwEvcoS8tl/8tCknbs80t18FIMcOJe/nFBvBty/MB6wfdU2mM+e04j3d0VtLXq9h/4r/PCIEkTp3QKRuJfjLPCJUF3FLIo25IvoFxSqXrtBVh6PjA7CSsSvIHQdoUTZ4CXfHJFMUnwD0tKILHf4iy/f7e0+wzC7t6/n2K7Fc01WQv/omyQCscvaG1XXMaJ34jj8POjYApc5tYKDSII5vNx4B0PoCPGOCjtY+uvd6KryFSRg2ZX95gvUtjQIDAQAB";
    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://www.jiuxiangheni.com";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://localhost:63342/paymentSuccess.html";

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
