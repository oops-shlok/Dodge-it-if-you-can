package com.example.spider_ballgame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

public class GameView extends View {

    private Context context;
    private Velocity velocity = new Velocity(13,32);
    private Handler handler;
    final long UPDATE_MILLI = 50;
    private Runnable runnable;
    private Paint textPaint = new Paint();
    private Bitmap ball,wall,wall2,triangle;
    private int ball_xcor,ball_ycor;
    private float wall1_xcor,wall1_ycor;
    private int dWidth,dHeight;
    private int points = 0;
    private float touchX,touchY;
    private float oldy,newy;
    boolean click;
    private SharedPreferences sharedPreferences;

    public GameView(Context context) {
        super(context);
        this.context=context;
        ball= BitmapFactory.decodeResource(getResources(),R.drawable.ball);
        wall = BitmapFactory.decodeResource(getResources(),R.drawable.wall);
        wall2=BitmapFactory.decodeResource(getResources(),R.drawable.wall2);
        triangle=BitmapFactory.decodeResource(getResources(),R.drawable.tr);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dWidth = size.x;
        dHeight = size.y;
        ball_xcor = 800;
        ball_ycor = (dHeight*3/5)+130;
        wall1_xcor= -200;
        wall1_ycor = (dHeight*3/5);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setTextSize(60);
        sharedPreferences=context.getSharedPreferences("hs",0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        wall1_xcor += velocity.getX();
        velocity.setX((velocity.getX()));
        if(wall1_xcor>1000){
            wall1_xcor = -200;
            points++;
        }
        if(click==false){
            if(ball_ycor<=(dHeight*3/5)+130){
                ball_ycor+=10;
            }
        }
        if((ball_xcor+ball.getWidth()-175)<= wall1_xcor && ball_ycor+(ball.getHeight())>=wall1_ycor){
            Intent intent = new Intent(context,GameOver.class);
            intent.putExtra("point",points);
            context.startActivity(intent);
            ((Activity)context).finish();
        }

        canvas .drawColor(Color.BLACK);
        canvas.drawBitmap(ball,ball_xcor,ball_ycor,null);
        canvas.drawBitmap(wall,wall1_xcor,wall1_ycor,null);
        canvas.drawText("Points: "+points,380,60,textPaint);
        handler.postDelayed(runnable,UPDATE_MILLI);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX=event.getX();
        float touchY=event.getY();
        if(touchX>ball_xcor && touchX<(ball_xcor+ball.getWidth())&&touchY >= ball_ycor && touchY<(ball_ycor+ball.getHeight())){
            int action=event.getAction();
            if(action==MotionEvent.ACTION_DOWN){
                oldy=ball_ycor;
                ball_ycor=(dHeight*3/5)-200;
            }
        }
        return true;
    }
}
