package com.jhmarryme.demo.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 *
 * @author JiaHao Wang
 * @date 2021/3/10 0:18
 */
@Component
public class I18nMessageUtil {

    private static MessageSource messageSource;
    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        I18nMessageUtil.messageSource = messageSource;
    }

    public static String getMsg(String msgKey, String... args) {
        try {
            return messageSource.getMessage(msgKey, args, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
            return msgKey;
        }
    }
}
