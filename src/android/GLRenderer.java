package com.wikitude.phonegap;

import android.opengl.GLSurfaceView;

import com.wikitude.common.rendering.RenderExtension;
import com.wikitude.common.tracking.RecognizedTarget;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GLRenderer implements GLSurfaceView.Renderer, RenderExtension {

    private RenderExtension _wikitudeRenderExtension = null;
    protected RecognizedTarget _currentlyRecognizedTarget = null;
    private StrokedRectangle _strokedRectangle = null;
    private StrokedRectangle.Type _strokedRectangleType = StrokedRectangle.Type.STANDARD;

    public GLRenderer(RenderExtension wikitudeRenderExtension_) {
        _wikitudeRenderExtension = wikitudeRenderExtension_;
    }

    @Override
    public void onDrawFrame(final GL10 unused) {
        if (_wikitudeRenderExtension != null) {
            _wikitudeRenderExtension.onDrawFrame(unused);
        }
        if (_currentlyRecognizedTarget != null) {
            _strokedRectangle.onDrawFrame(_currentlyRecognizedTarget);
        }
    }

    @Override
    public void onSurfaceCreated(final GL10 unused, final EGLConfig config) {
        if (_wikitudeRenderExtension != null) {
            _wikitudeRenderExtension.onSurfaceCreated(unused, config);
        }
        _strokedRectangle = new StrokedRectangle(_strokedRectangleType);
    }

    @Override
    public void onSurfaceChanged(final GL10 unused, final int width, final int height) {
        if (_wikitudeRenderExtension != null) {
            _wikitudeRenderExtension.onSurfaceChanged(unused, width, height);
        }
    }

    public void onResume() {
        if (_wikitudeRenderExtension != null) {
            _wikitudeRenderExtension.onResume();
        }
    }

    public void onPause() {
        if (_wikitudeRenderExtension != null) {
            _wikitudeRenderExtension.onPause();
        }
    }

    public void setCurrentlyRecognizedTarget(final RecognizedTarget currentlyRecognizedTarget_) {
        _currentlyRecognizedTarget = currentlyRecognizedTarget_;
    }

    public void setStrokedRectangleType(StrokedRectangle.Type _strokedRectangleType) {
        this._strokedRectangleType = _strokedRectangleType;
    }
}
