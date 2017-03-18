package org.ec.controller.utils;

import java.io.IOException;
import java.util.ArrayList;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.MappingJsonFactory;
import org.codehaus.jackson.type.TypeReference;

public class JsonParser<T>{

	/**
	 * @param json is Json Data that will be deserialized
	 * @return Java Object
	 * */
	public ArrayList<T> parse(String json) throws JsonParseException, IOException{
		
		JsonFactory factory = new MappingJsonFactory();
		org.codehaus.jackson.JsonParser parser = factory.createJsonParser(json);
		return parser.readValueAs(new TypeReference<ArrayList<T>>() {});
		
	}
	
}
