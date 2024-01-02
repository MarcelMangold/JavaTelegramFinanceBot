package com.mysticalducks.bots.financeBot;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Flag;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.mysticalducks.bots.financeBot.helper.PropertyManager;

public class FinanceBot extends AbilityBot  {
	
	private static final Logger logger = LoggerFactory.getLogger(App.class);
	
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
							Optional<HttpResponse> optResponse = sendRequest(upd);
							Boolean errorFound = false;
							if(optResponse.isPresent()) {
								HttpResponse response = optResponse.get();
								if(response.statusCode() == HttpStatus.SC_CREATED) {
						    	   silent.send("Categorie " + upd.getMessage().getText() + " sucessfully created", upd.getMessage().getChatId());
							    }
							    else if(response.statusCode() == HttpStatus.SC_NOT_FOUND) {
								    Optional<ApiErrorResponse> apiError = getApiErrorResponse(response);
								    if(apiError.isPresent()) {
								    	if(apiError.get().getError().getCode() == ApiErrorCodes.USER_NOT_FOUND.getCode() ) {
								    		Optional<HttpResponse> createUserResponse = createUser(upd);
									   	    System.out.println("----------------------------------------------------------asdasd");
									   	    System.out.println(createUserResponse);
								    		if(createUserResponse.isPresent()) {
								    			if(createUserResponse.get().statusCode() == HttpStatus.SC_CREATED) {
								    				System.out.println("User created");
								    			}
								    				
								    			
								    		}else {
								    			errorFound = true;
								    		}
								    		
								    	}
								    } else {
								    	errorFound = true;
								    }
							    	 
							    } else {
							    	silent.send("There was a problem when creating the category. Please try again later.", upd.getMessage().getChatId());
							    }
							} else {
								errorFound = true;
							}
							
							if(errorFound)
								silent.send("There was a problem when creating the category. Please try again later.", upd.getMessage().getChatId());
	            
	                   chatStates.remove(upd.getMessage().getChatId(), State.CREATE_CATEGORIE);
	              },
	  	              Flag.MESSAGE,
	  	              isCreateCategorieReply()
        		  )
	              .build();
	}
	
	private Optional<ApiErrorResponse> getApiErrorResponse(HttpResponse response) {
		ObjectMapper mapper = new ObjectMapper();
    	String body = (String) response.body();
		try {
			return Optional.fromNullable(mapper.readValue(body, ApiErrorResponse.class));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Optional.absent();
		}
		
	}
	
	private Optional<HttpResponse> sendRequest(Update upd) {
		HttpClient client = HttpClient.newHttpClient();
	    try {
			return Optional.fromNullable(client.send(createCategorieRequest(upd), HttpResponse.BodyHandlers.ofString()));
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return Optional.absent();			
		}
	}
	
	private Optional<HttpResponse> createUser(Update upd) {
		User user = new User(upd.getMessage().getFrom().getUserName(), upd.getMessage().getFrom().getId());
		HttpClient client = HttpClient.newHttpClient();
		
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonUser = objectMapper.writeValueAsString(user);
			System.out.println(jsonUser);
			HttpRequest request = HttpRequest.newBuilder()
   	            .uri(URI.create("http://" + URL + "/user"))
   	            .POST(HttpRequest.BodyPublishers.ofString(jsonUser))
   	         	.header("Content-Type", "application/json")  
   	            .build();

			return Optional.fromNullable(client.send(request, HttpResponse.BodyHandlers.ofString()));
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Optional.absent();	
   	  
		
	}

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