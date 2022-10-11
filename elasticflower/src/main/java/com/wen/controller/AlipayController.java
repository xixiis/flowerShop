package com.wen.controller;

import com.alipay.api.AlipayApiException;
import com.wen.service.AlipayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付宝
 * @data
 */
@CrossOrigin
@RestController
@RequestMapping("/alipay")
public class AlipayController {

    @Autowired
    @Qualifier("alipayService")
    private AlipayService alipayService;



    /**
     * web 订单支付
     * @param outTradeNo 订单编号
     * @param totalAmount 订单价格
     * @param subject 商品名称
     * @return
     * @throws Exception
     */
    @RequestMapping("/getPagePay")
    public Map<Object, Object> getPagePay(String outTradeNo,Integer totalAmount,String subject) throws Exception{
        System.out.println(outTradeNo+totalAmount+subject);
        String pay = alipayService.webPagePay(outTradeNo, totalAmount, subject);
        System.out.println(pay);
        Map<Object, Object> pays = new HashMap<>();
        pays.put("pay", pay);
        return pays;
    }

    /**
     * app 订单支付
     */
    /**
     *
     * @param outTradeNo 订单编号
     * @param totalAmount 订单价格
     * @param subject 商品名称
     * @throws Exception
     */
    @RequestMapping("/getAppPagePay")
    public void getAppPagePay( String outTradeNo ,Integer totalAmount,String subject) throws Exception{

        String pay = alipayService.appPagePay(outTradeNo, totalAmount, subject);
        System.out.println(pay);

    }

    /**
     * 交易查询
     * @param outTradeNo 订单编号
     * @throws Exception
     */
    @PostMapping("/aipayQuery")
    public void alipayQuery(String outTradeNo) throws Exception{

        String query = alipayService.query(outTradeNo);
        System.out.println(query);
    }


    /**
     * 退款
     * @param outTradeNo 订单编号
     * @param outRequestNo  标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传
     * @param refundAmount 订单金额
     * @param refundReason 退货原因
     * @throws AlipayApiException
     */
    @GetMapping("alipayRefund")
    public void alipayRefund(
            @RequestParam("outTradeNo")String outTradeNo,
            @RequestParam(value = "outRequestNo", required = false)String outRequestNo,
            @RequestParam(value = "refundAmount", required = false)Integer refundAmount,
            String refundReason) throws AlipayApiException {

        String refund = alipayService.refund(outTradeNo, refundReason, refundAmount, outRequestNo);
        System.out.println(refund);

        return ;
    }

    /**
     * 退款查询
     @param outTradeNo 订单编号（唯一）
     @param outRequestNo 标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传
     * @throws AlipayApiException
     */
    @PostMapping("refundQuery")
    public void refundQuery(String outTradeNo,String outRequestNo) throws AlipayApiException{
        String refund = alipayService.refundQuery(outTradeNo, outRequestNo);
        System.out.println(refund);
        return ;

    }

    /**
     * 交易关闭
     * @throws AlipayApiException
     */
    @PostMapping("alipayclose")
    public void alipayclose(String outTradeNo) throws AlipayApiException {
        String close = alipayService.close(outTradeNo);
        System.out.println(close);
        return ;
    }

    @RequestMapping("/errorOrder")
    public String errorOrder(){
        return "error";
    }
    @RequestMapping("/returnUrl")
    public String returnUrl(){
        return "return";
    }
}


