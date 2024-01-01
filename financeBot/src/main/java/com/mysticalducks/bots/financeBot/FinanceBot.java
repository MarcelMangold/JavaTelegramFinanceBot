package com.mysticalducks.bots.financeBot;


import java.io.IOException;
import java.net.URI;
import java.util.Map;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.mapdb.Atomic.Long;
import org.mapdb.Atomic.String;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Flag;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.mysticalducks.bots.financeBot.helper.PropertyManager;

public class FinanceBot extends AbilityBot  {
	
	private final Map<Long, State> chatStates;
	private String URL = null;
	
	public FinanceBot(String botToken, String botUsername) {
		super(botToken, botUsername);
		PropertyManager prop = new PropertyManager();
		URL = prop.getRestApiUrl();
		chatStates = db.getMap("CHAT_STATES");
	}
	
	public Ability sayHelloWorld() {
	    return Ability
	              .builder()
	              .name("hello")
	              .info("says hello world!")
	              .locality(Locality.ALL)
	              .privacy(Privacy.PUBLIC)
	              .action(ctx -> silent.send("Hello world!", ctx.chatId()))
	              .build();
	}
	
	public Ability newCategorie() {
	    return Ability
	              .builder()
	              .name("new_categorie")
	              .info("create a new categorie")
	              .locality(Locality.ALL)
	              .privacy(Privacy.PUBLIC)
	              .action(ctx -> {
	            	  silent.send("Please type the categorie name you want to create!", ctx.chatId());
	            	  chatStates.put(ctx.chatId(), State.CREATE_CATEGORIE);
	              	}
        		  )	             
	              .reply((ctx, upd) -> {
	            	  	
						try {
							Optional<HttpResponse> optResponse = sendRequest(upd);
							
							if(optResponse.isEmpty()) {
								silent.send("There was a problem when creating the category. Please try again later.", upd.getMessage().getChatId());
							} else {
								HttpResponse response = optResponse.get();
								if(response.statusCode() == HttpStatus.SC_CREATED) {
						    	   silent.send("Categorie " + upd.getMessage().getText() + " sucessfully created", upd.getMessage().getChatId());
							    }
							    else if(response.statusCode() == HttpStatus.SC_NOT_FOUND) {
							    	ObjectMapper mapper = new ObjectMapper();
							    	 ApiErrorResponse errorResponse = mapper.readValue(response.body(), ApiErrorResponse.class);
							    	 if(errorResponse.getError().getCode() == 2) {
							    		 
							    	 }
							    } else {
							    	silent.send("There was a problem when creating the category. Please try again later.", upd.getMessage().getChatId());
							    }
							}
	            
	                   chatStates.remove(upd.getMessage().getChatId(), State.CREATE_CATEGORIE);
	              },
	  	                Flag.MESSAGE,
	  	              isCreateCategorieReply()
        		  )
	              .build();
	}
	
	private Optional<HttpResponse> sendRequest(Update upd) {
		HttpClient client = HttpClient.newHttpClient();
	    HttpResponse<String> response;
	    try {
			Optional.of(client.send(createCategorieRequest(upd), HttpResponse.BodyHandlers.ofString()));
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}
	
//	private HttpRequest createUser(Update upd) {
//		String categorieName = upd.getMessage().getText();
//		var test = upd.getMessage().getFrom();
//		User user = new User(upd.getMessage().getFrom().getUserName(), upd.getMessage().getFrom().getId());
//   	    HttpRequest request = HttpRequest.newBuilder()
//   	            .uri(URI.create("http://" + URL + "/category?userId=109&name=" + categorieName + "&iconId=0"))
//   	            .POST(HttpRequest.BodyPublishers.noBody()) 
//   	            .build();
//   	    
//   	    return request;
//		
//	}

	private HttpRequest createCategorieRequest(Update upd) {
		String categorieName = upd.getMessage().getText();
   	    HttpRequest request = HttpRequest.newBuilder()
   	            .uri(URI.create("http://" + URL + "/category?userId=109&name=" + categorieName + "&iconId=0"))
   	            .POST(HttpRequest.BodyPublishers.noBody()) 
   	            .build();
   	    
   	    return request;
		
	}
	
	private Predicate<Update> isCreateCategorieReply() {
	      return upd -> {
	    	  long chatId = upd.getMessage().getChatId();
	    	  return chatStates.get(chatId).equals(State.CREATE_CATEGORIE);
	      };
	}

	@Override
	public long creatorId() {
		return 123;
	}
	
	

}