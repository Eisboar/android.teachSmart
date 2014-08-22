package com.wanda.json;

import android.util.JsonWriter;

import com.wanda.data.Answer;
import com.wanda.data.MetaData;
import com.wanda.data.MultipleChoiceAnswer;
import com.wanda.data.RatingAnswer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;

import static com.wanda.data.QuestionType.MULTIPLE_CHOICE;
import static com.wanda.data.QuestionType.RATING;

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

        public void writeGetSheetRequest(int id) throws IOException {

            writer.beginObject();

            writer.name("id").value(id);

            writer.endObject();
        }

        public void writeAddAnswersRequest(int questionSheetID, Map<Integer,Answer> answers) throws IOException{

            writer.beginObject();

            writer.name("question_sheet_id").value(String.valueOf(questionSheetID));

            writer.name("answers");

            writer.beginArray();

            Iterator it = answers.entrySet().iterator();

            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry)it.next();
                int questionPos = (Integer) pairs.getKey();
                Answer answer = (Answer) pairs.getValue();

                writer.beginObject();

                writer.name("position").value(String.valueOf(questionPos));
                writer.name("type").value(String.valueOf(answer.getType()).toLowerCase());
                writer.name("answer_data");

                writer.beginObject();
                if (answer.getType()==MULTIPLE_CHOICE){
                    writer.name("answer_position").
                            value(String.valueOf(((MultipleChoiceAnswer) answer).getSelectedAnswer()));
                } else if (answer.getType()==RATING){
                    writer.name("rating").
                            value(String.valueOf(((RatingAnswer) answer).getRating()));
                }

                writer.endObject();

                writer.endObject();



            }

            writer.endArray();

            writer.endObject();
        }

    /**
     * method to write the metadata object without password
     * @param metaData
     * @throws IOException
     */
        //public void writeMeta(MetaData metaData) throws IOException {
//            writeMeta(metaData, null);
//        }

}
