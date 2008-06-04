/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.util.servlet;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

/**
 * <a href="SharedSessionServletRequest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Brian Myunghun Kim
 *
 */
public class SharedSessionServletRequest extends HttpServletRequestWrapper {

	public SharedSessionServletRequest(
		HttpServletRequest req, Map<String, Object> sharedSessionAttributes,
		boolean shared) {

		super(req);

		_sharedSessionAttributes = sharedSessionAttributes;

		_ses = new SharedSessionWrapper(
			req.getSession(), _sharedSessionAttributes);
		_shared = shared;
	}

	public HttpSession getSession() {
		if (_shared) {
			return _ses;
		}
		else {
			return new SharedSessionWrapper(
				super.getSession(), _sharedSessionAttributes);
		}
	}

	public HttpSession getSession(boolean create) {
		if (_shared) {
			return _ses;
		}
		else {
			return new SharedSessionWrapper(
				super.getSession(create), _sharedSessionAttributes);
		}
	}

	private HttpSession _ses;
	private Map<String, Object> _sharedSessionAttributes;
	private boolean _shared;

}