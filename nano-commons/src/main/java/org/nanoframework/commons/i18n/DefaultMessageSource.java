/**
 * Copyright 2015-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nanoframework.commons.i18n;

import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.nanoframework.commons.loader.PropertiesLoader;
import org.nanoframework.commons.util.Charsets;
import org.nanoframework.commons.util.StringUtils;

/**
 * @author yanghe
 * @date 2016年3月5日 下午3:13:10
 */
public class DefaultMessageSource implements MessageSource {
	private static final ConcurrentMap<Locale, Properties> MESSAGE = new ConcurrentHashMap<>();
	public static final String DEFAULT_PREFIX_MESSAGE = "/messages/messages";
	public static final String PROPERITES_SUFFIX = ".properties";
	private final Object LOCK = new Object();
	private Locale locale;
	
	protected DefaultMessageSource() { }
	
	protected DefaultMessageSource(Locale locale) {
		this.locale = locale == null ? Locale.ROOT : locale;
		load(locale);
	}
	
	protected Properties load(final Locale locale) {
		Properties properties;
		if((properties = MESSAGE.get(locale)) == null) {
			synchronized (LOCK) {
				if((properties = MESSAGE.get(locale)) == null) {
					properties = PropertiesLoader.load(DEFAULT_PREFIX_MESSAGE + (StringUtils.isEmpty(locale.getLanguage()) ? "" : "_" + locale.getLanguage()) + (StringUtils.isEmpty(locale.getCountry()) ? "" : "_" + locale.getCountry()) + PROPERITES_SUFFIX);
					MESSAGE.put(locale, properties);
				}
			}
		}
		
		return properties;
	}
	
	@Override
	public String getMessage(String code, String defaultMessage) {
		try {
			return formatter(MESSAGE.get(locale).getProperty(code));
		} catch(Throwable e) {
			return defaultMessage;
		}
	}

	@Override
	public String getMessage(String code, String defaultMessage, Locale locale) {
		try {
			return formatter(load(locale).getProperty(code));
		} catch(Throwable e) {
			return defaultMessage;
		}
	}
	
	@Override
	public String getMessage(String code, Object[] args, String defaultMessage) {
		try {
			return formatter(MESSAGE.get(locale).getProperty(code), args);
		} catch(Throwable e) {
			return defaultMessage;
		}
	}

	@Override
	public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
		try {
			return formatter(load(locale).getProperty(code), args);
		} catch(Throwable e) {
			return defaultMessage;
		}
	}

	@Override
	public String getMessage(String code) throws NoSuchMessageException {
		try {
			return formatter(MESSAGE.get(locale).getProperty(code));
		} catch(Throwable e) {
			throw new NoSuchMessageException(code);
		}
	}
	
	@Override
	public String getMessage(String code, Object[] args) throws NoSuchMessageException {
		try {
			return formatter(MESSAGE.get(locale).getProperty(code), args);
		} catch(Throwable e) {
			throw new NoSuchMessageException(code);
		}
	}

	@Override
	public String getMessage(String code, Locale locale) throws NoSuchMessageException {
		try {
			return formatter(load(locale).getProperty(code));
		} catch(Throwable e) {
			throw new NoSuchMessageException(code);
		}
	}

	@Override
	public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
		try {
			return formatter(load(locale).getProperty(code), args);
		} catch(Throwable e) {
			throw new NoSuchMessageException(code);
		}
	}

	protected String formatter(final String message, Object... args) {
		String newMessage = message;
		if(args.length > 0) {
			int index = 0;
			for(Object arg : args) {
				final String segment = "{"+index+"}"; 
				if(newMessage.indexOf(segment) > -1) {
					newMessage = newMessage.replace(segment, arg.toString());
				}
				
				index ++;
			}
		}
		
		return new String(newMessage.getBytes(Charsets.ISO_8859_1), Charsets.UTF_8);
	}
}
