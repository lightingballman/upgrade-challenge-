package com.campresv.controller;

import com.campresv.dto.CampScheduleDto;
import com.campresv.dto.UserDto;
import com.campresv.model.CampsiteQueryReqModel;
import com.campresv.service.CampScheduleService;
import com.campresv.service.TestConcurrencyTask;
import com.campresv.service.UserService;
import com.campresv.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@RestController
public class TestController {

	@Autowired
	private UserService userService;

	@Autowired
	private CampScheduleService campScheduleService;

	@Autowired
	private ApplicationContext applicationContext;

	@RequestMapping("/create/user")
	public ResponseEntity<?> reserve(@RequestBody UserDto reqModel){
		UserDto ud = userService.createIfNoteExist(reqModel.getEmail(), reqModel.getFirstName(),reqModel.getLastName());
		return ResponseEntity.ok(ud);
	}

	@RequestMapping("/testSave")
	public ResponseEntity<?> testSave(@RequestBody CampsiteQueryReqModel reqModel){
		try {
			List<CampScheduleDto> list = campScheduleService.getCampSchedule(DateUtil.parseDateNowOnError(reqModel.startDay),
					DateUtil.parseDateNowOnError(reqModel.endDay));
			CampScheduleDto rs = list.get(0);

			ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
			List<TestConcurrencyTask> tasks = new ArrayList<>();

			for (int i = 1; i <= 15; i++) {
				TestConcurrencyTask task = applicationContext.getBean(TestConcurrencyTask.class);
				task.setData(rs);
				tasks.add(task);
			}
			for(int i = 0; i < tasks.size(); i ++ ) {
				executor.execute(tasks.get(i));
			}
			executor.shutdown();

		}catch(Exception e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok("ok");
	}
}
