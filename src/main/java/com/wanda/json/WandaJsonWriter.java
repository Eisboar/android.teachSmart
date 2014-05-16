package com.wanda.json;

import android.util.JsonWriter;

import com.wanda.data.MetaData;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
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
    public WandaJsonWriter(StringWriter out) throws UnsupportedEncodingException {
        writer = new JsonWriter(out);
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
     * @param metaData
     * @param password
     * @throws IOException
     */
        public void writeMeta(MetaData metaData, String password) throws IOException {

            writer.beginObject();

            //fill login-json-object
            writer.name("username").value(metaData.getUsername());
            writer.name("password").value(password);

            //close up
            writer.endObject();
        }

    /**
     * method to write the metadata object without password
     * @param metaData
     * @throws IOException
     */
        public void writeMeta(MetaData metaData) throws IOException {
            writeMeta(metaData, null);
        }

}
