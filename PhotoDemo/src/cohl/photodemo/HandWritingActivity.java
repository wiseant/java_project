package cohl.photodemo;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.support.v4.app.NavUtils;

public class HandWritingActivity extends Activity {

    private HandWrite handWrite = null;  
    private Button clear = null;
    private Button btnTest = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
          
        handWrite = (HandWrite)findViewById(R.id.handwriteview);  
        clear = (Button)findViewById(R.id.clear);  
        clear.setOnClickListener(new clearListener());  
        btnTest = (Button)findViewById(R.id.btnTest);
        btnTest.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setContentView(R.layout.test);
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private class clearListener implements OnClickListener{  
  
        public void onClick(View v)  
        {  
            handWrite.clear();  
        }

    }  
    
}
