package org.pdr.adatpers;

import org.pdr.entity.User;
import org.pdr.repository.UserRepository;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

public class InternalUpdate {
    private static final UserRepository USER_REPOSITORY = new UserRepository();
    private final Update update;

    public InternalUpdate(Update update) {
        this.update = update;
    }

    public long getChatId() {
        long chatId;
        if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
        } else if (update.hasMessage()) {
            chatId = update.getMessage().getChatId();
        } else {
            throw new UnsupportedOperationException("not implemented yet");
        }
        return chatId;
    }

    public boolean hasText() {
        return Optional.ofNullable(update.getMessage()).map(Message::hasText).orElse(false);
    }

    public String getMessageText() {
        return update.getMessage().getText();
    }

    public String getCallBackText() {
        return update.getCallbackQuery().getMessage().getText();
    }

    public int getMessageId() {
        return update.getMessage().getMessageId();
    }

    public Integer getCallBackMessageId() {
        return Optional.ofNullable(update.getCallbackQuery()).map(CallbackQuery::getMessage).map(Message::getMessageId).orElse(null);
    }

    public String getCallbackData() {
        return update.getCallbackQuery().getData();
    }

    public Contact getUserInfo() {
        return update.getMessage().getContact();
    }

    public boolean isReply() {
        return Optional.ofNullable(update.getMessage()).map(Message::isReply).orElse(false);
    }

    public Message getReply() {
        return update.getMessage().getReplyToMessage();
    }

    public User getUser() {
        long chatId = getChatId();
        return Optional.ofNullable(USER_REPOSITORY.getUserByChatId(chatId)).orElse(new User(chatId));
    }
}
