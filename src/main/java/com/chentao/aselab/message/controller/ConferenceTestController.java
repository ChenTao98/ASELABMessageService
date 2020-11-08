package com.chentao.aselab.message.controller;

import com.chentao.aselab.message.controller.RequestTem.ResponseConference;
import com.chentao.aselab.message.controller.RequestTem.ResponseUser;
import com.chentao.aselab.message.controller.response.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ConferenceTestController {

    @PostMapping("/conference")
    public ResponseEntity<Object> getConference(@RequestBody ResponseConference responseConferenceReq){
        ResponseConference responseConference=new ResponseConference();
        responseConference.setFullName("test");
        responseConference.setTopics("topic");
        responseConference.setId(responseConferenceReq.getId());
        if(responseConferenceReq.getId()==1){
            return ResponseEntity.ok(new ResponseObject(0,"success",responseConference));
        }else {
            return ResponseEntity.ok(new ResponseObject(1,"no conference",responseConference));
        }
    }
    @GetMapping("/profile")
    public ResponseEntity<Object> getUser(@RequestParam Long userId){
        ResponseUser responseUser=new ResponseUser();
        responseUser.setId(userId);
        responseUser.setEmail("ca@asd");
        responseUser.setUsername("dasd");
        if(responseUser.getId()==1){
            return ResponseEntity.ok(new ResponseObject(0,"success",responseUser));
        }else {
            return ResponseEntity.ok(new ResponseObject(1,"no user",responseUser));
        }
    }
}
