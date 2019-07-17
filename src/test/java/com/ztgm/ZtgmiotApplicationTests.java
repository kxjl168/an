package com.ztgm;

import com.alibaba.fastjson.JSON;
import com.kxjl.base.base.PageInfo;
import com.kxjl.tasktransferplat.controller.AppController.MobileLockCompanyOrderController;
import com.kxjl.tasktransferplat.controller.AppController.MobileLockerServerController;
import com.kxjl.tasktransferplat.controller.WebController.GradeMoneyController;
import com.kxjl.tasktransferplat.dto.request.BaseRequestDto;
import com.kxjl.tasktransferplat.dto.response.BaseResponseDto;
import com.kxjl.tasktransferplat.pojo.GradeMoney;
import com.kxjl.tasktransferplat.pojo.Orderinfo;
import com.kxjl.tasktransferplat.pojo.Userinfo;
import com.kxjl.tasktransferplat.service.MobileLockerServerService;
import com.kxjl.tasktransferplat.service.OrderInfoOperateService;
import com.kxjl.tasktransferplat.service.OrderinfoService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpRequest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZtgmiotApplicationTests {


	@Autowired
	MobileLockerServerController mobileLockerServerService;




	@Test
	public void test34(){
		HashMap<Object, Object> map = new HashMap<>();
		map.put("orderId","11c4996584ad4f2c91d8de3af05866b3");
		map.put("lockerAppointmentTime","2019-5-27 16:37");
		String s = JSON.toJSONString(map);

		BaseRequestDto baseRequestDto = new BaseRequestDto();
		baseRequestDto.setData(s);
		mobileLockerServerService.registerAppointmentTime(baseRequestDto);
	}


}
