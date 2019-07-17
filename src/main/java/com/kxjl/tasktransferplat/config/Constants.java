package com.kxjl.tasktransferplat.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ResourceBundle;

/**
 * @author handsomeXiao
 * @date 2018/9/20 15:17
 */
public interface Constants {

    ResourceBundle resource = ResourceBundle.getBundle("project");

    /**
     * 货主确认运单后司机的接货期限
     */
    Long TRUCKER_RECEIVED_DEADLINE = 1 * 24 * 60 * 60 * 1000L/*20000L*/;

    /**
     * 司机确认接单或送达时货主的确认期限
     */
    Long SHIPPER_CONFIRM_DEADLINE = 1 * 24 * 60 * 60 * 1000L/*20000L*/;

    /**
     * 货主确认运单后公司确认运输方案的期限
     */
    Long COMPANY_SCHEMA_DEADLINE = 1 * 24 * 60 * 60 * 1000L/*20000L*/;

    String DELAY_ORDER_KEY_PREFIX = "delayWaybill_";

    String RESOURCE_SERVER_URL = "";

    /**
     * 节点ip和端口
     */
    String NODE_IP_PORT = resource.getString("node.ipPort") + "_";

    String CONFIRM_DISPATCH_SCHEMA = "com.fmsserviceclinet.service.WaybillService.confirmDispatchSchema";

    String TRUCKER_RECEIVED = "com.fmsserviceclinet.service.WaybillService.truckerReceived";

    String SHIPPER_RECEIVED = "com.fmsserviceclinet.service.WaybillService.shipperReceived";

    String SHIPPER_DELIVERY = "com.fmsserviceclinet.service.WaybillService.shipperDelivery";

    int HTTP_SUCCESS = 200;

}
