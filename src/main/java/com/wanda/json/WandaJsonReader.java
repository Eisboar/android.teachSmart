package com.wanda.json;

import android.util.Log;

import com.wanda.data.MultipleChoiceQuestion;
import com.wanda.data.Question;
import com.wanda.data.QuestionAnswer;
import com.wanda.data.QuestionCreator;
import com.wanda.data.QuestionSheet;
import com.wanda.data.QuestionType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.Vector;

/**
 * Created by sash on 10/02/14.
 */
public class WandaJsonReader {

public Vector<QuestionSheet> parseGetSheetsResponse(String jsonInputString){

    Vector<QuestionSheet> questionSheets = new Vector<QuestionSheet>();

    try {
        JSONObject jsonObject = new JSONObject(jsonInputString);

        JSONArray questionSheetArray = jsonObject.getJSONArray("questionSheets");

        for (int i = 0; i < questionSheetArray.length(); i++) {
            QuestionSheet questionSheet = new QuestionSheet();

            JSONObject questionSheetJsonObject = questionSheetArray.getJSONObject(i);

            questionSheet.setName(questionSheetJsonObject.getString("name"));
            questionSheet.setCreate_date(Timestamp.valueOf
                    (questionSheetJsonObject.getString("create_date")));
            questionSheet.setID(Integer.valueOf(questionSheetJsonObject.getString("ID")));

            questionSheets.addElement(questionSheet);
        }
    } catch (JSONException e) {
        Log.i("SASH", "couldn't parse getSheets-response");
        e.printStackTrace();


    }

    return questionSheets;
}
    public QuestionSheet parseGetQuestionSheetResponse(String jsonInputString) {
        QuestionSheet questionSheet = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonInputString);
            String status = jsonObject.getString("status");
            if (status.equals("error"))
                return null;
            JSONObject questionSheetJsonObject = jsonObject.getJSONObject("questionSheet");
            questionSheet = new QuestionSheet();
            questionSheet.setID(Integer.valueOf(questionSheetJsonObject.getString("ID")));
            questionSheet.setName(questionSheetJsonObject.getString("name"));
            questionSheet.setCreate_date(Timestamp.valueOf(questionSheetJsonObject.getString("create_date")));
            JSONArray questionArray = questionSheetJsonObject.getJSONArray("questions");
            Vector<Question> questions = new Vector<Question>();
            for (int i = 0; i < questionArray.length(); i++) {
                JSONObject questionJsonObject = questionArray.getJSONObject(i);

                String typeString = questionJsonObject.getString("type");
                Question question = QuestionCreator.createQuestion(typeString);

                question.setPos(Integer.valueOf(questionJsonObject.getString("position")));
                question.setQuestionText(questionJsonObject.getString("questionText"));
                if (question.getType()== QuestionType.MULTIPLE_CHOICE){
                    JSONArray answersArray = questionJsonObject.getJSONArray("answers");
                    Vector<QuestionAnswer> answers = new Vector<QuestionAnswer>();
                    for (int j = 0; j < answersArray.length(); j++) {
                        JSONObject answerJsonObject = answersArray.getJSONObject(j);
                        QuestionAnswer answer = new QuestionAnswer();
                        answer.setPosition(Integer.valueOf(answerJsonObject.getString("position")));
                        answer.setAnswerText(answerJsonObject.getString("answerText"));
                        answers.addElement(answer);
                    }
                    ((MultipleChoiceQuestion) question).setAnswers(answers);
                }
                questions.addElement(question);
            }
            questionSheet.setQuestions(questions);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return questionSheet;
    }
}
