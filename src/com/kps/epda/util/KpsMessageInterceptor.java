package com.kps.epda.util;

import org.springframework.flex.core.MessageInterceptor;
import org.springframework.flex.core.MessageProcessingContext;

import flex.messaging.messages.Message;


public class KpsMessageInterceptor implements MessageInterceptor {

    /* (non-Javadoc)
     * @see org.springframework.flex.core.MessageInterceptor#postProcess(org.springframework.flex.core.MessageProcessingContext, flex.messaging.messages.Message, flex.messaging.messages.Message)
     */
    public Message postProcess(MessageProcessingContext arg0, Message arg1, Message arg2){
       
        return null;
    }

    /* (non-Javadoc)
     * @see org.springframework.flex.core.MessageInterceptor#preProcess(org.springframework.flex.core.MessageProcessingContext, flex.messaging.messages.Message)
     */
    public Message preProcess(MessageProcessingContext arg0, Message arg1){
        // TODO Auto-generated method stub
        return null;
    }

}
