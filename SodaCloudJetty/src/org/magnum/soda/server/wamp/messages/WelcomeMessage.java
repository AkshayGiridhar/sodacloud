/*****************************************************************************
 * Copyright 2013 Olivier Croquette <ocroquette@free.fr>                     *
 *                                                                           *
 *  Licensed under the Apache License, Version 2.0 (the "License");          *
 *  you may not use this file except in compliance with the License.         *
 *  You may obtain a copy of the License at                                  *
 *                                                                           *
 *      http://www.apache.org/licenses/LICENSE-2.0                           *
 *                                                                           *
 *  Unless required by applicable law or agreed to in writing, software      *
 *  distributed under the License is distributed on an "AS IS" BASIS,        *
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. *
 *  See the License for the specific language governing permissions and      *
 *  limitations under the License.                                           *
 ****************************************************************************/
package org.magnum.soda.server.wamp.messages;

import java.lang.reflect.Type;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;



public class WelcomeMessage extends Message {
	final static int PROTOCOL_VERSION = 1;
	
	public String sessionId;
	public int protocolVersion;
	public String serverIdent;

	public WelcomeMessage() {
		super(MessageType.WELCOME);
	}

	public WelcomeMessage(String sessionId, String serverIdent) {
		super(MessageType.WELCOME);
		this.sessionId = sessionId;
		this.protocolVersion = PROTOCOL_VERSION;
		this.serverIdent = serverIdent;
		
	}
	
	public boolean isValid() {
		return (type != null && type.equals(MessageType.WELCOME)
				&& protocolVersion == 1
				&& sessionId != null && sessionId.length() > 0
				&& serverIdent != null && serverIdent.length() > 0
				);
	}

	public static class Serializer implements JsonSerializer<WelcomeMessage> {
		@Override
		public JsonElement serialize(WelcomeMessage msg, Type arg1,
				JsonSerializationContext context) {
			JsonArray array = new JsonArray();
			array.add(context.serialize(msg.getType().getCode()));
			array.add(context.serialize(msg.sessionId));
			array.add(context.serialize(PROTOCOL_VERSION));
			array.add(context.serialize(msg.serverIdent));
			return array;
		}
	}

	public static class Deserializer implements JsonDeserializer<WelcomeMessage> {
		@Override
		public WelcomeMessage deserialize(JsonElement element, Type arg1,
				JsonDeserializationContext context) throws JsonParseException {

			JsonArray array = element.getAsJsonArray();
			
			if ( MessageType.fromInteger(array.get(0).getAsInt()) != MessageType.WELCOME)
				return null;
			
			WelcomeMessage msg = new WelcomeMessage();
			msg.sessionId = array.get(1).getAsString();
			msg.protocolVersion = PROTOCOL_VERSION;
			msg.serverIdent = array.get(3).getAsString();
			return msg;
		}
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
