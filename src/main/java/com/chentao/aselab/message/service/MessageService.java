package com.chentao.aselab.message.service;

import com.chentao.aselab.message.controller.RequestTem.ResponseConference;
import com.chentao.aselab.message.controller.RequestTem.ResponseUser;
import com.chentao.aselab.message.controller.request.MessageRequestBody;
import com.chentao.aselab.message.controller.request.SentMessage;
import com.chentao.aselab.message.entity.Message;
import com.chentao.aselab.message.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Set;

@Service
public class MessageService {
    private MessageRepository messageRepository;

    private static final String CONFERENCE_NOT_FOUND  = "conferenceNotFound";
    private static final String USER_NOT_FOUND  = "userNotFound";
    private static final String SUCCESS  = "success";
    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository=messageRepository;
    }
    public boolean init(){
        for (int i = 0; i < 10; i++) {
            Message message = new Message("type", 0, "content");
            message.setSentTime(new Timestamp(System.currentTimeMillis()));
            message.setTag("tag");
            message.setUserId((long) i);
            message.setTip("tip");
            messageRepository.save(message);
        }
        for (int i = 0; i < 10; i++) {
            Message message = new Message("type", 0, "content");
            message.setSentTime(new Timestamp(System.currentTimeMillis()));
            message.setTag("tag");
            message.setUserId((long)i);
            message.setTip("tip");
            messageRepository.save(message);
        }
        Message message = new Message("type", 0, "content");
        message.setSentTime(new Timestamp(System.currentTimeMillis()));
        message.setTag("tag");
        message.setUserId((long)2);
        message.setTip("tip");
        messageRepository.save(message);
        return true;
    }
    public Set<Message> getAllMessagesOfUser(Long userId){
        return  messageRepository.findMessagesByUserId(userId);
    }
    public Message addNewMessage(MessageRequestBody messageRequestBody){
        Message message=new Message(messageRequestBody);
        messageRepository.save(message);
        return message;
    }
    public int sendPCMemberInvitation(Long idOfUserToBeSent, ResponseConference responseConference){
        Message message=new Message();
        message.setType("PC_MEMBER_INVITATION");
        message.setStatus(0);
        message.setContent(responseConference.getFullName()+","+responseConference.getId());
        message.setUserId(idOfUserToBeSent);
        message.setSentTime(new Timestamp(System.currentTimeMillis()));
        message.setTag(responseConference.getTopics());
        messageRepository.save(message);
        return 0;
    }
    public int sendConferenceCheckedOrAbolishedMessage(Long idOfUserToBeSent, ResponseConference conference,String checkedOrAbolished){
        Message message=new Message();
        message.setType(checkedOrAbolished);
        message.setStatus(0);
        message.setContent(conference.getFullName()+","+conference.getId());
        message.setUserId(idOfUserToBeSent);
        message.setSentTime(new Timestamp(System.currentTimeMillis()));
        messageRepository.save(message);
        return 0;
    }
    public int sendPCMemberAcceptedOrRejectedMessage(Long idOfUserToBeSent, ResponseConference conference, ResponseUser user,String acceptOrReject){
        Message message=new Message();
        message.setType(acceptOrReject);
        message.setStatus(0);
        message.setContent(user.getUsername()+","+conference.getFullName()+","+conference.getId());
        message.setUserId(idOfUserToBeSent);
        message.setSentTime(new Timestamp(System.currentTimeMillis()));
        messageRepository.save(message);
        return 0;
    }
    public int sendMessage(SentMessage sentMessage){
        Message message=new Message();
        message.setType(sentMessage.getType());
        message.setStatus(0);
        message.setContent(sentMessage.getMessage());
        message.setUserId(sentMessage.getUserToSent());
        message.setSentTime(new Timestamp(System.currentTimeMillis()));
        messageRepository.save(message);
        return 0;
    }
    public String updateStatus(Long messageId){
        Message message = messageRepository.findMessageByMessageId(messageId);
        if(message == null){
            return "messageNotFound";
        }
        message.setStatus(1);
        messageRepository.save(message);
        return SUCCESS;
    }
    public Message getMessageById(Long messageId){
        return messageRepository.findMessageByMessageId(messageId);
    }
}
