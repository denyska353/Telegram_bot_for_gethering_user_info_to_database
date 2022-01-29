package com.example.botdatabased.service;

import com.example.botdatabased.models.UserCard;
import com.example.botdatabased.repo.UserCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Autowired
    private UserCardRepository userCardRepository;

    @Override
    public String getBotUsername() {
        return "Denyska352_test_bot";
    }

    @Override
    public String getBotToken() {
        return "2057909343:AAHtLhsqVuUVLwF1uTaxePnVZdpcaf3bzMY";
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage sendmassage = new SendMessage();
        Message message;
        boolean new_user = true;
        UserCard usercard ;
        if(update.hasCallbackQuery()) {
            String str = update.getCallbackQuery().getData();
            System.out.println(update.getCallbackQuery().getFrom().getId());
            System.out.println(str);
            if(str.equals("Yes"))
            {
                for (long i = 1; i <= userCardRepository.count(); ++i) {
                    if (userCardRepository.findById(i).isPresent()) {
                        if (update.getCallbackQuery().getFrom().getId().equals(userCardRepository.findById(i).get().getChatID())) {
                            long stepnumber = userCardRepository.findById(i).get().getStep_number();
                            if (stepnumber == 5) {
                                usercard = userCardRepository.findById(i).orElseThrow();
                                usercard.setStep_number(6);
                                userCardRepository.save(usercard);
                                sendmassage.setText("Вы сохранены!");
                                sendmassage.setChatId(update.getCallbackQuery().getFrom().getId().toString());
                                try {
                                    execute(sendmassage);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        }
                }
            }
            if(str.equals("No")  )
            {
                for (long i = 1; i <= userCardRepository.count(); ++i) {
                    if (userCardRepository.findById(i).isPresent()) {
                        if (update.getCallbackQuery().getFrom().getId().equals(userCardRepository.findById(i).get().getChatID())) {
                            long stepnumber = userCardRepository.findById(i).get().getStep_number();
                            if (stepnumber == 5) {
                                usercard = userCardRepository.findById(i).orElseThrow();
                                usercard.setStep_number(1);
                                userCardRepository.save(usercard);
                                sendmassage.setText("Введите ваше имя и фамилию");
                                sendmassage.setChatId(update.getCallbackQuery().getFrom().getId().toString());
                                try {
                                    execute(sendmassage);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }
        else {
            message = update.getMessage();
            for (long i = 1; i <= userCardRepository.count(); ++i) {
                System.out.println(message.getChatId());
                usercard = userCardRepository.findById(i).orElseThrow();
                System.out.println(usercard.getChatID());
                if (userCardRepository.findById(i).isPresent()) {
                    if (message.getChatId().equals(userCardRepository.findById(i).get().getChatID())) {
                        long stepnumber = userCardRepository.findById(i).get().getStep_number();
                        if (stepnumber == 1) {
                            usercard = userCardRepository.findById(i).orElseThrow();
                            usercard.setName(message.getText());
                            usercard.setStep_number(2);
                            userCardRepository.save(usercard);
                            sendmassage.setText("Введите ваш адресс");
                            sendmassage.setChatId(message.getChatId().toString());
                            try {
                                execute(sendmassage);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if (stepnumber == 2) {
                            usercard = userCardRepository.findById(i).orElseThrow();
                            usercard.setAdress(message.getText());
                            usercard.setStep_number(3);
                            userCardRepository.save(usercard);
                            sendmassage.setText("Введите ваш номер телефона");
                            sendmassage.setChatId(message.getChatId().toString());
                            try {
                                execute(sendmassage);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if (stepnumber == 3) {
                            usercard = userCardRepository.findById(i).orElseThrow();
                            usercard.setPhone(message.getText());
                            usercard.setStep_number(4);
                            userCardRepository.save(usercard);
                            sendmassage.setText("Введите ваш E-mail");
                            sendmassage.setChatId(message.getChatId().toString());
                            try {
                                execute(sendmassage);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if (stepnumber == 4) {
                            usercard = userCardRepository.findById(i).orElseThrow();
                            usercard.setMail(message.getText());
                            usercard.setStep_number(5);
                            userCardRepository.save(usercard);

                            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                            List<List<InlineKeyboardButton>> lst2 = new ArrayList<>();
                            List<InlineKeyboardButton> lst = new ArrayList<>();
                            InlineKeyboardButton button = new InlineKeyboardButton();
                            button.setText("No");
                            button.setCallbackData("No");
                            InlineKeyboardButton button2 = new InlineKeyboardButton();
                            button2.setText("Yes");
                            button2.setCallbackData("Yes");

                            lst.add(button);
                            lst.add(button2);
                            lst2.add(lst);
                            inlineKeyboardMarkup.setKeyboard(lst2);

                            sendmassage.setText("Спасибо! Все ок?)\n"+"Имя и фамилия: "+usercard.getName()+ "\nАдрес: "+usercard.getAdress()+"\nНомер телефона: "+usercard.getPhone()+"\nE-mail: "+usercard.getMail());
                            sendmassage.setChatId(message.getChatId().toString());
                            sendmassage.setReplyMarkup(inlineKeyboardMarkup);

                            try {
                                execute(sendmassage);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        new_user = false;
                    }
                }
            }

            if (new_user) {
                usercard = new UserCard();
                usercard.setStep_number(1);
                usercard.setChatID(message.getChatId());
                userCardRepository.save(usercard);
                sendmassage.setText("Введите ваше имя и фамилию");
                sendmassage.setChatId(message.getChatId().toString());
                try {
                    execute(sendmassage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }





        System.out.println("good");
    }
}
