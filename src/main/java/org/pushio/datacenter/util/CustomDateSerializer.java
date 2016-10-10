package org.pushio.datacenter.util;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomDateSerializer extends JsonSerializer<Date> {  
	  
    @Override  
    public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {  
 
            String formattedDate = Constants.DF.format(value);  
            jgen.writeString(formattedDate);  
    }  
}  