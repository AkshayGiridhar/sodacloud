/*****************************************************************************
 * Copyright [2013] [Jules White]                                            *
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
package org.magnum.soda.svc;

import java.lang.reflect.Proxy;

import org.magnum.soda.MsgBus;
import org.magnum.soda.ObjRegistry;
import org.magnum.soda.proxy.ObjRef;
import org.magnum.soda.proxy.ProxyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.Subscribe;

public class ObjInvoker {

	private static final Logger Log = LoggerFactory.getLogger(ObjInvoker.class);

	private ObjRegistry registry_;
	
	private ProxyFactory factory_;

	private MsgBus msgBus_;

	public ObjInvoker(MsgBus bus, ObjRegistry registry, ProxyFactory factory) {
		super();
		factory_ = factory;
		msgBus_ = bus;
		registry_ = registry;

		msgBus_.subscribe(this);
	}

	@Subscribe
	public void handleInvocation(ObjInvocationMsg msg) {
		InvocationInfo inv = msg.getInvocation();
		ObjRef targetid = msg.getTargetObjectId();

		Object o = registry_.get(targetid);
		if (!Proxy.isProxyClass(o.getClass())) {
			Log.debug("Invoking method on: [{}] invocation: [{}]", o, inv);

			ObjInvocationRespMsg resp = (ObjInvocationRespMsg)msg.createReply();

			try {
				Object[] ex = inv.getParameters();
				//this method will directly update the
				//ex array in place
				factory_.createProxiesFromRefsIfNeeded(ex);
			
			
				Object rslt = inv.invoke(o);
				rslt = factory_.convertToObjectRefIfNeeded(rslt);
				
				resp.setResult(rslt);
			} catch (Exception t) {
				resp.setException(t);
			}

			msgBus_.publish(resp);
		}
	}
}