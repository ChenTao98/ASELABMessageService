package com.chentao.aselab.message.controller;

import com.chentao.aselab.message.controller.RequestTem.ResponseConference;
import com.chentao.aselab.message.controller.RequestTem.ResponseUser;
import com.chentao.aselab.message.controller.request.MessageRequestBody;
import com.chentao.aselab.message.controller.request.SentMessageRequestBody;
import com.chentao.aselab.message.controller.response.ResponseObject;
import com.chentao.aselab.message.entity.Message;
import com.chentao.aselab.message.service.MessageService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Set;

@RestController
public class MessageController {
    private MessageService messageService;
    private static final String API_STR = "/api";
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private static final String CONFERENCE_IP="http://127.0.0.1:21000/";
    private static final String USER_IP="http://47.100.106.153:21001/api/user/";

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }


    @GetMapping("/init")
    public ResponseEntity<Boolean> init() {
        messageService.init();
        return ResponseEntity.ok(Boolean.TRUE);
    }
    @PostMapping(API_STR+"/message/SendPCMemberInvitation")
    public ResponseEntity<Object> sendPCMemberInvitation(@RequestBody SentMessageRequestBody sentMessageRequestBody){
        RestTemplate restTemplate = new RestTemplate();
        ResponseObject responseObject=restTemplate.postForObject(
                CONFERENCE_IP+"/conference",
                new ResponseConference(sentMessageRequestBody.getConferenceId(),null,null),
                ResponseObject.class);
        if(responseObject.getErrorCode()!=0){
            return ResponseEntity.ok(new ResponseObject(1,responseObject.getMessage(),null));
        }else {
            ResponseConference responseConference=JSON.parseObject(JSON.toJSONString(responseObject.getData()),ResponseConference.class);
            int errorCode=messageService.sendPCMemberInvitation(sentMessageRequestBody.getiDOfUserToBeSent(),responseConference);
            String messageStr=errorCode==0?SUCCESS:FAIL;
            return ResponseEntity.ok(new ResponseObject(errorCode,messageStr,null));
        }
    }

    @PostMapping(API_STR+"/message/SendConferenceCheckedOrAbolishedMessage")
    public ResponseEntity<Object> sendConferenceCheckedOrAbolishedMessage(@RequestBody SentMessageRequestBody sentMessageRequestBody){
        RestTemplate restTemplate = new RestTemplate();
        ResponseObject responseObject=restTemplate.postForObject(
                CONFERENCE_IP+"/conference",
                new ResponseConference(sentMessageRequestBody.getConferenceId(),null,null),
                ResponseObject.class);
        if(responseObject.getErrorCode()!=0){
            return ResponseEntity.ok(new ResponseObject(1,responseObject.getMessage(),null));
        }else {
            ResponseConference responseConference=JSON.parseObject(JSON.toJSONString(responseObject.getData()),ResponseConference.class);
            int errorCode=messageService.sendConferenceCheckedOrAbolishedMessage(sentMessageRequestBody.getiDOfUserToBeSent(),responseConference,sentMessageRequestBody.getCheckedOrAbolished());
            String messageStr=errorCode==0?SUCCESS:FAIL;
            return ResponseEntity.ok(new ResponseObject(errorCode,messageStr,null));
        }
    }
    @PostMapping(API_STR+"/message/SendPCMemberAcceptedOrRejectedMessage")
    public ResponseEntity<Object> sendPCMemberAcceptedOrRejectedMessage(@RequestBody SentMessageRequestBody sentMessageRequestBody){
        RestTemplate restTemplate = new RestTemplate();
        ResponseObject responseObject=restTemplate.postForObject(
                CONFERENCE_IP+"/conference",
                new ResponseConference(sentMessageRequestBody.getConferenceId(),null,null),
                ResponseObject.class);
        if(responseObject.getErrorCode()!=0){
            return ResponseEntity.ok(new ResponseObject(1,responseObject.getMessage(),null));
        }
        ResponseConference conference=JSON.parseObject(JSON.toJSONString(responseObject.getData()),ResponseConference.class);
        restTemplate=new RestTemplate();
        String baseUrl = USER_IP+"/profile";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("userId",sentMessageRequestBody.getiDOfUserToBeSent().toString());
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl);
        URI uri = builder.queryParams(params).build().encode().toUri();
        responseObject=restTemplate.getForObject(uri,ResponseObject.class);
        if(responseObject.getErrorCode()!=0){
            return ResponseEntity.ok(new ResponseObject(1,responseObject.getMessage(),null));
        }
        ResponseUser user=JSON.parseObject(JSON.toJSONString(responseObject.getData()),ResponseUser.class);
        int errorCode=messageService.sendPCMemberAcceptedOrRejectedMessage(sentMessageRequestBody.getiDOfUserToBeSent(),conference,user,sentMessageRequestBody.getAcceptOrReject());
        String messageStr=errorCode==0?SUCCESS:FAIL;
        return ResponseEntity.ok(new ResponseObject(errorCode,messageStr,null));
    }
//    @PostMapping("/NewMessage")
//    public ResponseEntity<Message> addNewMessage(@RequestBody MessageRequestBody messageRequestBody) {
//        if (messageRequestBody.getUserId() == null) {
//            return null;
//        }
//        messageRequestBody.setStatus(0);
//        messageRequestBody.setSentTime(new Timestamp(System.currentTimeMillis()));
//        return ResponseEntity.ok(messageService.addNewMessage(messageRequestBody));
//    }

    //oring /Message
    @GetMapping(API_STR + "/message/all")
    public ResponseEntity<Object> searchAllMessagesOfUser(@RequestParam Long userId) {
        Set<Message> set = messageService.getAllMessagesOfUser(userId);
        return ResponseEntity.ok(new ResponseObject(0,SUCCESS,set));
    }

    @RequestMapping(API_STR + "/message/detail")
    public ResponseEntity<Object> messageDetail(@RequestParam Long messageId) {
        Message message = messageService.getMessageById(messageId);
        int errorCode = message == null ? 1 : 0;
        String messageStr = message == null ? FAIL : SUCCESS;
        return ResponseEntity.ok(new ResponseObject(errorCode, messageStr,message));
    }

//    @PostMapping("/message/hasMessageUnread")
//    public ResponseEntity<Boolean> updateMessage(@RequestBody MessageRequestBody messageRequestBody) {
//        Long userId = messageRequestBody.getUserId();
//        boolean hasNewMessage = false;
//        Set<Message> set = messageService.getAllMessagesOfUser(userId);
//        for (Message message : set) {
//            if (message.getStatus() == 0) {
//                hasNewMessage = true;
//                break;
//            }
//        }
//        return ResponseEntity.ok(hasNewMessage);
//    }

    @PostMapping(API_STR+"/message/UpdateMessage")
    public ResponseEntity<Object> messageAlreadyRead(@RequestBody MessageRequestBody messageRequestBody) {
        Long messageId = messageRequestBody.getMessageId();
        int errorCode = messageService.updateStatus(messageId).equals(SUCCESS)?0:1;
        String messageStr=errorCode==0?SUCCESS:FAIL;
        return ResponseEntity.ok(new ResponseObject(errorCode,messageStr,null));
    }
}
