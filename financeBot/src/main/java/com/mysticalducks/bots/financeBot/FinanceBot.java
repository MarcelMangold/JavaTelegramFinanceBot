package com.mysticalducks.bots.financeBot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.mysticalducks.bots.financeBot.helper.PropertyManager;

public class FinanceBot  {
	
	PropertyManager prop = new PropertyManager();
	
	public FinanceBot() {
		
	}
	
	public TelegramLongPollingBot createBot() {
		return new TelegramLongPollingBot(prop.getBotToken()) {
			
			@Override
			public String getBotUsername() {
				return prop.getBotUsername();
			}
			
			@Override
			public void onUpdateReceived(Update update) {
				if (update.hasMessage() && update.getMessage().hasText()) {
	    	        SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
	    	        message.setChatId(update.getMessage().getChatId().toString());
	    	        message.setText(update.getMessage().getText());
	    	        
	    	        try {
	    	            execute(message); // Call method to send the message
	    	        } catch (TelegramApiException e) {
	    	            e.printStackTrace();
	    	        }
	    	    }
				
			}
		};
	}
	

}