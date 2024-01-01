package com.mysticalducks.bots.financeBot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.mysticalducks.bots.financeBot.helper.PropertyManager;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	try {
    		
    		PropertyManager prop = new PropertyManager();
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new FinanceBot(prop.getBotToken(), prop.getBotUsername()));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
