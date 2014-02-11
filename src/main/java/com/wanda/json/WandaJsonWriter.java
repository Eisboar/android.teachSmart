package com.wanda.json;

import android.util.JsonWriter;

import com.wanda.data.MetaData;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

/**
 * Created by sash on 10/02/14.
 */

/**
 * Class ro translate any required data object into a defined a json string
 */

public class WandaJsonWriter {

    private JsonWriter writer;

    /**
     * Constructor, opening the JsonWriter on an given out stream
     * @param out
     * @throws UnsupportedEncodingException
     */
    public WandaJsonWriter(OutputStream out) throws UnsupportedEncodingException {
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
        writer.setIndent("  ");
    }

    /**
     * call to close the writer
     * @throws IOException
     */
    public void closeWriter() throws IOException {
        writer.close();
    }

    /**
     * method to write the metaData object into json-format
     * @param writer
     * @param metaData
     * @param password
     * @throws IOException
     */
        public void writeMeta(JsonWriter writer, MetaData metaData, String password) throws IOException {

            //begin metaData object
            writer.beginObject();
            writer.name("meta");

            //fill metaData object
            writer.beginObject();
            writer.name("username").value(metaData.getUsername());
            writer.name("password").value(password);
            writer.name("sessionID").value(metaData.getSessionID());
            writer.endObject();

            //close metadata object
            writer.endObject();
        }

    /**
     * method to write the metadata object without password
     * @param writer
     * @param metaData
     * @throws IOException
     */
        public void writeMeta(JsonWriter writer, MetaData metaData) throws IOException {
            writeMeta(writer, metaData, null);
        }

}
