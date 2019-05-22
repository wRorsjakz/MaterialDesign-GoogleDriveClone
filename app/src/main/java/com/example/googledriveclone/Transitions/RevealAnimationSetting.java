package com.example.googledriveclone.Transitions;

public class RevealAnimationSetting {

    int CenterX, CenterY, Width, Height;

    public RevealAnimationSetting(int centerX, int centerY, int width, int height) {
        CenterX = centerX;
        CenterY = centerY;
        Width = width;
        Height = height;
    }

    public int getCenterX() {
        return CenterX;
    }

    public int getCenterY() {
        return CenterY;
    }

    public int getWidth() {
        return Width;
    }

    public int getHeight() {
        return Height;
    }

}
