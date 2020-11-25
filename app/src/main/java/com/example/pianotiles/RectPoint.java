package com.example.pianotiles;

import android.graphics.PointF;

public class RectPoint {
    PointF start;
    PointF end;

    public RectPoint(PointF start, PointF end){
        this.start = start;
        this.end = end;
    }

    public PointF getStart() {
        return start;
    }

    public PointF getEnd() {
        return end;
    }

    public void setStart(PointF start) {
        this.start = start;
    }

    public void setEnd(PointF end) {
        this.end = end;
    }
}
