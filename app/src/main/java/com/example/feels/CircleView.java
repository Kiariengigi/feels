// BlurryCirclesView.java
package com.example.feels;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.BlurMaskFilter;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.animation.ValueAnimator;

import java.util.Random;

public class CircleView extends View {

    private static class Circle {
        float x, y, radius;
        int color;
        float dx, dy;
        Paint paint;
    }

    private Circle[] circles;
    private Random random = new Random();

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // Accept hardware acceleration for blur
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        // For 6 circles; change quantity if you wish
        circles = new Circle[6];
        for (int i = 0; i < circles.length; i++) {
            circles[i] = makeCircle();
        }
        animateCircles();
    }

    private Circle makeCircle() {
        Circle c = new Circle();
        c.radius = random.nextInt(70) + 80;
        c.x = random.nextFloat() * 800;  // Will be adjusted onLayout
        c.y = random.nextFloat() * 1200;
        c.color = Color.argb(80, random.nextInt(256), random.nextInt(256), random.nextInt(256));
        c.paint = new Paint();
        c.paint.setColor(c.color);
        c.paint.setStyle(Paint.Style.FILL);
        c.paint.setMaskFilter(new BlurMaskFilter(c.radius / 1.7f, BlurMaskFilter.Blur.NORMAL));
        // random direction
        c.dx = random.nextFloat() * 3 - 1.5f;
        c.dy = random.nextFloat() * 3 - 1.5f;
        return c;
    }

    // This will be called when the size of the view is known
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        for (Circle c : circles) {
            c.x = random.nextFloat() * w;
            c.y = random.nextFloat() * h;
        }
    }

    private void animateCircles() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(20); // in ms -> almost continuous
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(animation -> {
            updatePositions();
            invalidate();
        });
        animator.start();
    }

    private void updatePositions() {
        float w = getWidth();
        float h = getHeight();
        for (Circle c : circles) {
            c.x += c.dx * speedMultiplier;
            c.y += c.dy * speedMultiplier;
            // Bounce off edges
            if (c.x < 0 || c.x > w) c.dx = -c.dx;
            if (c.y < 0 || c.y > h) c.dy = -c.dy;
            // Optionally add a little random movement change
            c.dx += (random.nextFloat() - 0.5f) * 0.2f;
            c.dy += (random.nextFloat() - 0.5f) * 0.2f;
            // limit speed
            if (c.dx > 2) c.dx = 2;
            if (c.dx < -2) c.dx = -2;
            if (c.dy > 2) c.dy = 2;
            if (c.dy < -2) c.dy = -2;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Circle c : circles) {
            canvas.drawCircle(c.x, c.y, c.radius, c.paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    public void setColorofAllCircles (int color){
        for (Circle c : circles) {
            c.color = color;
            c.paint.setColor(color);
        }
        invalidate();
    }

    private float speedMultiplier = 1f;

    public void setSpeedMultiplier(float multiplier){
        speedMultiplier = multiplier;
    }
}