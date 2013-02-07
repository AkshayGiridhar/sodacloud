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
package org.magnum.soda.transport;

public class MsgContainer {

	private byte[] msg_;

	private String destination_;

	public MsgContainer() {
	}

	public MsgContainer(byte[] msg) {
		super();
		msg_ = msg;
	}

	public byte[] getMsg() {
		return msg_;
	}

	public void setMsg(byte[] msg) {
		msg_ = msg;
	}

	public String getDestination() {
		return destination_;
	}

	public void setDestination(String destination) {
		destination_ = destination;
	}

}