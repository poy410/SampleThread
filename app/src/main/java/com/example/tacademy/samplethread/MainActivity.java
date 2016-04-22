package com.example.tacademy.samplethread;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    ProgressBar progressBar;
    public static final int MESSAGE_PROGRESS=0;
    public static final int MESSAGE_DONE=1;
    class ProgressRunnable implements Runnable{
        int progress;
        public  ProgressRunnable(int progress){
            this.progress=progress;

        }
        @Override
        public void run() {
            textView.setText("progress : " + progress);
            progressBar.setProgress(progress);

        }
    }
    class ProgressDone implements Runnable{
        @Override
        public void run() {
            textView.setText("download completed");
            progressBar.setProgress(100);
        }

    }
    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case MESSAGE_PROGRESS:
                    int progress=msg.arg1;
                    progressBar.setProgress(progress);
                    textView.setText("progress : " +progress);
                    break;
                case MESSAGE_DONE:
                    progressBar.setProgress(100);
                    textView.setText("DONE");
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.text_message);
        progressBar=(ProgressBar)findViewById(R.id.progress_download);
        Button btn=(Button)findViewById(R.id.btn_start);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int count=0;
                        while(count<=100)
                        {
                         //   Message msg=mHandler.obtainMessage(MESSAGE_PROGRESS,count*5,0);
                        //    mHandler.sendMessage(msg);
                            mHandler.post(new ProgressRunnable(count));
                            count++;
                            try{
                                Thread.sleep(500);
                            }
                            catch (InterruptedException e){

                            }

                        }
                      //  Message msg=mHandler.obtainMessage(MESSAGE_DONE);
                     //   mHandler.sendMessage(msg);
                        mHandler.post(new ProgressDone());

                    }
                }).start();
            }
        });
    }
    class MyTask extends AsyncTask<String,Integer,Boolean>{
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Boolean doInBackground(String... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }
}

