package com.mysticalducks.bots.financeBot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.mysticalducks.bots.financeBot.helper.PropertyManager;

public class App 
{
	private static final Logger logger = LoggerFactory.getLogger(App.class);
	
    public static void main( String[] args )
    {
    	try {
    		PropertyManager prop = new PropertyManager();
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new FinanceBot(prop.getBotToken(), prop.getBotUsername()));
            logger.info("Bot " + prop.getBotUsername() + " sucessfully started!");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
