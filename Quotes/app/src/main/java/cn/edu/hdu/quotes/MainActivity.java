package cn.edu.hdu.quotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> quoteList;
    private TextView mQuoteTextView;
    private Button mNextButton;
    private Button mPreviousButton;
    private int index = 0;

    private List<String> getQuoteList() {
        List<String> allQuotes = new ArrayList<String>();
        allQuotes.add("真理往往和谬误很接近。——尼采");
        allQuotes.add("历史能给我们提供的唯一借鉴，就是我们从历史不能得到任何借鉴。——黑格尔");
        allQuotes.add("天才是1%的灵感加99%的汗水，但那1%的灵感是最重要的，甚至比 99%的汗水\n" +
                "都重要。——爱迪生");
        allQuotes.add("一切不以结婚为目的的恋爱都是耍流氓。——毛泽东");
        allQuotes.add("网上95%的名人名言都是编的。——鲁迅");
        return allQuotes;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quoteList = getQuoteList();
        mQuoteTextView = findViewById(R.id.tvQuote);
        mQuoteTextView.setText(quoteList.get(0));

        mNextButton = findViewById(R.id.btnNext);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index >= 4) {
                    index -= 5;
                }
                index++;
                mQuoteTextView.setText(quoteList.get(index));
            }
        });

        mPreviousButton = findViewById(R.id.btnPrevious);
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index < 1) {
                    index += 5;
                }
                index--;
                mQuoteTextView.setText(quoteList.get(index));
            }
        });
    }

}
