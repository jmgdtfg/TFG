package slack;

import com.github.seratch.jslack.Slack;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.webhook.*;
import com.ullink.slack.simpleslackapi.ChannelHistoryModule;
import com.ullink.slack.simpleslackapi.SlackAttachment;
import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackMessageHandle;
import com.ullink.slack.simpleslackapi.SlackPreparedMessage;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.SlackUser;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import com.ullink.slack.simpleslackapi.impl.ChannelHistoryModuleFactory;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import com.ullink.slack.simpleslackapi.replies.SlackChannelReply;



public class SlackManager {
	//Token del bot
	private String tokenBot_ = "";
	//Instancia de Slack necesaria para usar el Webhook service
	private Slack slack_ = Slack.getInstance();
	//URL ( que sirve de token ) del webhook service
	private String url_="";
	//Session del bot ( simple slack api )
	private SlackSession slackSession_ = SlackSessionFactory.createWebSocketSlackSession(tokenBot_);


	//Constructor por defecto de la clase SlackManager
	public SlackManager() throws IOException{
		slackSession_.connect();
	}

	/*Constructor parametrizado de la clase SlackManager
	 * Parametro 1: Token del bot
	 * Parametro 2: url que proporciona el webhook service
	 */
	public SlackManager(String tokenBot,String url) throws IOException{
		tokenBot_=tokenBot;
		slack_=Slack.getInstance();
		url_=url;
		slackSession_=SlackSessionFactory.createWebSocketSlackSession(tokenBot);
		slackSession_.connect();
	}



	//Función que permite obtener una lista de los canales asociados a la sesión de slack
	public List<String> getChannels() throws IOException{


		List<String> channels = slackSession_.getChannels()
				.stream()
				.map(c ->c.getName())
				.collect(Collectors.toList());
		return channels;
/*		for (String channel : channels) {
			System.out.println(channel);

		}*/
	}

	//Función que permite obtener una lista de los usuarios asociados a la sesión de slack
	public List<String> getUsers() throws IOException{

		List<String> users = slackSession_.getUsers()
				.stream()
				.map(c ->c.getUserName())
				.collect(Collectors.toList());
		return users;
/*		for (String user : users) {
			System.out.println(user);
		}*/
	}

	/*Función que permite enviar un mensaje directo a un usuario segun su correo
	 * Parametro 1: Email
	 * Parametro 2: Texto a enviar
	 * */
	public void sendDirectMessageToAUser(String email, String text)    {

		//obtiene el usuario según su mail
		SlackUser user = slackSession_.findUserByEmail(email);


		//Abrimos un nuevo canal para comunicarnos con dicho usuario
		SlackMessageHandle<SlackChannelReply> reply = slackSession_.openDirectMessageChannel(user);
		SlackChannel channel = reply.getReply().getSlackChannel();

		slackSession_.sendMessage(channel,text, null);
	}
	/*Función que permite enviar un mensaje a un canal. El bot debe haber sido invitado al canal.
	 * Parametro 1: Texto a enviar
	 * Parametro 2: Canal al que se envia
	 * Parametro 3: Nombre del usuario que lo envia
	 * */
	public void sendMessageWithBot(String text, String channelName){

		SlackChannel channel = slackSession_.findChannelByName(channelName);

		//build a message object
		SlackPreparedMessage preparedMessage = new SlackPreparedMessage.Builder()
				.withMessage(text)
				.withUnfurl(true)
				.addAttachment(new SlackAttachment())
				.addAttachment(new SlackAttachment())
				.build();

		slackSession_.sendMessage(channel, preparedMessage);
	}

	/*Función que permite enviar un mensaje a un canal. Utilizando webhook service
	 * Parametro 1: Texto a enviar
	 * Parametro 2: Canal al que se envia
	 * Parametro 3: Nombre del usuario que lo envia ??
	 * */
	public void sendMessage(String text, String channel, String name) throws IOException, SlackApiException{

		Payload payload = Payload.builder()
				.channel("#"+channel)
				.username(name)
				.iconEmoji(":smile_cat:")
				.text(text)
				.build();

		WebhookResponse response = slack_.send(url_, payload); //Servicio webhook de la api de slack

		System.out.println(response.getMessage().toString());
		// response.code, response.message, response.body
	}

	public List<SlackMessagePosted> getChannelMessages(String channelName) throws IOException{

		SlackChannel channel = slackSession_.findChannelByName(channelName);

		ChannelHistoryModule channelHistoryModule = ChannelHistoryModuleFactory
				.createChannelHistoryModule(slackSession_);
		
		List<SlackMessagePosted> messages = channelHistoryModule
				.fetchHistoryOfChannel(channel.getId());
		return messages;
		
		/*		for (SlackMessagePosted message :messages){
			System.out.println(message.getMessageContent());
		}*/

	}

}

