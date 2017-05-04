package tw.edu.fcu.recommendedfood.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import tw.edu.fcu.recommendedfood.R;

public class ArticleCommandActivity extends AppCompatActivity {
    private EditText edtTitle;
    private EditText edtMsg;
    private String arr[];
    String title;
    String msg = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_command);

        initView();
    }


    //TODO 之後發文的時候會使用setOnEditorActionListener
    public void initView() {
        edtTitle = (EditText) findViewById(R.id.edt_title);
        edtMsg = (EditText) findViewById(R.id.edt_msg);
//        edtMsg.addTextChangedListener(new NewTextWatcher(edtMsg));
        edtMsg.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event == null) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        // Capture soft enters in a singleLine EditText that is the last EditText
                        // This one is useful for the new list case, when there are no existing ListItems
                        edtMsg.clearFocus();
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                    } else if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        // Capture soft enters in other singleLine EditTexts
                    } else if (actionId == EditorInfo.IME_ACTION_GO) {
                    } else {
                        // Let the system handle all other null KeyEvents
                        return false;
                    }
                } else if (actionId == EditorInfo.IME_NULL) {
                    // Capture most soft enters in multi-line EditTexts and all hard enters;
                    // They supply a zero actionId and a valid keyEvent rather than
                    // a non-zero actionId and a null event like the previous cases.
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        // We capture the event when the key is first pressed.
                        StringBuffer tempMsg = new StringBuffer(v.getText().toString());
                        tempMsg.append("\n");
                        Log.v("tempMsg",tempMsg.toString());

                        arr = tempMsg.toString().split("\n");
                        for (int i = 0; i < arr.length; i++) {
                            Log.v("testing", arr.length + " " + i + " " + "" + arr[i] + "");
                        }

                        msg = tempMsg.toString();
                        v.setText(msg);
                        edtMsg.setSelection(msg.length());
                    } else {
                        // We consume the event when the key is released.
                        return true;
                    }
                } else {
                    // We let the system handle it when the listener is triggered by something that
                    // wasn't an enter.
                    return false;
                }
                return true;
            }
        });
    }

    public void imgDoneCommand(View view) {
        commitMsg();
//        finish();
    }

    public void commitMsg(){
        title = "<p>" + edtTitle.getText().toString() + "</p>";
        msg = edtMsg.getText().toString()+"\n";
        arr = msg.split("\n ");
        Log.v("testing", title);

        if (arr != null && !arr[0].equals("")) {
            for (int i = 0; i < arr.length; i++) {
                Log.v("testing", "<p>" + arr[i] + "</p><br>");
            }
        } else {
            msg = "<p>" + edtMsg.getText().toString() + "</p>";
            Log.v("testing", msg);
        }
    }

    public void imgBackAtivity(View view) {
        finish();
    }
}
