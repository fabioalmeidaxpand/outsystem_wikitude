package com.wikitude.phonegap;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wikitude.WikitudeSDK;
import com.wikitude.WikitudeSDKStartupConfiguration;

import com.wikitude.common.camera.CameraSettings;
import com.wikitude.common.rendering.RenderExtension;
import com.wikitude.common.tracking.RecognizedTarget;
import com.wikitude.rendering.ExternalRendering;
import com.wikitude.tracker.CloudTracker;
import com.wikitude.tracker.CloudTrackerEventListener;
import com.wikitude.tracker.Tracker;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.sax2.Driver;


public class WikitudePlugin extends CordovaPlugin {

	private static final String WIKITUDE_SDK_KEY = "I1DvilsB+nIf7VfKUHv1QeuZn3+wmDAOfaSFtpDY0gvCXU54rr1Mj8i+p7a8Ewv+TfsnCxtWg87fvcNL/HdkAcEIdh8WHJBScxvHOkAdOis0FR2am9X1qY47aRpZZEm2dWHNz7/lc/RRiqPNiKWPryfLQspwVF4NyxrDSMhDjaNTYWx0ZWRfXwOBWbLfrtL3ywtzm6f/xdRqfJwWpGP80ti4eWVn6qPPQ/ZjSl9ksDCE1Lhk5hnXl5pgLJSUtFK/XHmw2F2weEl958dE19//JepyZ7QaBqxD4YjLkZX6J09RE2zHlYHNyX9Pf1Swq2s6tP/A4CqTHidd5wx/bdc8NmPfv1cOP6002cq43ZknnaI/J66qCM7+UNepZO5fN5T5vqcvLWVFCV4DxSiUT8UA8fvTs8uqiV9nHBbb7hqEn0z89uyZP38NBXPqXAyWtlmWwb6TmAjGdMpfRpcSq1UtzeaZLC3FySoqsmAgHZfDxjghQqiKx5aA9m0esw0coFeCusVVhngCyy7bkkehBTJmgNQH3EsDom+M30XgFcNRkX00jYAhXQoNsHRUEXcDRCydJlG7eocB6zcVghzH9nuRUcTTrNghJFhSGWgq5IOoEBJCIVb5VHmb9M8UOtvANBQ4iGcVAkR9vPEziCN99twU8XqkI7GltpFyPWU0/Wyc4BQ=";

	private WikitudeSDK _wikitudeSDK;
	//private CustomSurfaceView _customSurfaceView;
	private Driver _driver;
	//private GLRenderer _glRenderer;

	private String action;
    
	private static final String	ACTION_OPEN = "open";

	@Override
	public boolean execute( final String action, final JSONArray args, final CallbackContext callContext ) {
		this.action = action;

		/* hide architect-view -> destroy and remove from activity */
		if ( WikitudePlugin.ACTION_OPEN.equals( action ) ) {

			loadArchitectWorld();
		/*	if ( this.architectView != null ) {
				this.cordova.getActivity().runOnUiThread( new Runnable() {

					@Override
					public void run() {
						removeArchitectView();
					}
				} );
				callContext.success( action + ": architectView is present" );
			}
			else {
				callContext.error( action + ": architectView is not present" );
			}*/
			return true;
		}
		
		/* fall-back return value */
		callContext.sendPluginResult( new PluginResult( PluginResult.Status.ERROR, "no such action: " + action ) );
		return false;
	}

	private void loadArchitectWorld() {
		_wikitudeSDK = new WikitudeSDK(new ExternalRendering() {
			@Override
			public void onRenderExtensionCreated(RenderExtension renderExtension) {
			//	_glRenderer = new GLRenderer(renderExtension_);
			//	_customSurfaceView = new CustomSurfaceView(getApplicationContext(), _glRenderer);
			//	_driver = new Driver(_customSurfaceView, 30);

			//	FrameLayout viewHolder = new FrameLayout(getApplicationContext());
			//	setContentView(viewHolder);

			//	viewHolder.addView(_customSurfaceView);

			//	LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
			//	LinearLayout controls = (LinearLayout) inflater.inflate(R.layout.activity_continuous_cloud_tracking, null);
			//	viewHolder.addView(controls);
			}
		});
		WikitudeSDKStartupConfiguration startupConfiguration = new WikitudeSDKStartupConfiguration(WIKITUDE_SDK_KEY, CameraSettings.CameraPosition.BACK, CameraSettings.CameraFocusMode.CONTINUOUS);
		_wikitudeSDK.onCreate(this.cordova.getActivity().getApplicationContext(), this.cordova.getActivity(), startupConfiguration);
		CloudTracker tracker = _wikitudeSDK.getTrackerManager().create2dCloudTracker("cfb0aec3aeda615ed5375467dc72339f", "57fcf42eb881248a1518ecb7");
		tracker.registerTrackerEventListener(new CloudTrackerEventListener() {
			@Override
			public void onTrackerFinishedLoading(final CloudTracker cloudTracker_) {
				cloudTracker_.startContinuousRecognition(1000);
			}

			@Override
			public void onTrackerLoadingError(final CloudTracker cloudTracker_, final String errorMessage_) {
				cordova.getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						//EditText targetInformationTextField = (EditText) findViewById(R.id.continuous_tracking_info_field);
						//targetInformationTextField.setText("Tracker failed to load. Error: " + errorMessage_);
						//targetInformationTextField.setVisibility(View.VISIBLE);
					}
				});
			}

			@Override
			public void onTargetRecognized(final Tracker cloudTracker_, final String targetName_) {

			}

			@Override
			public void onTracking(final Tracker cloudTracker_, final RecognizedTarget recognizedTarget_) {
				//_glRenderer.setCurrentlyRecognizedTarget(recognizedTarget_);
			}

			@Override
			public void onTargetLost(final Tracker cloudTracker_, final String targetName_) {
				//_glRenderer.setCurrentlyRecognizedTarget(null);
			}

			@Override
			public void onExtendedTrackingQualityUpdate(final Tracker tracker_, final String targetName_, final int oldTrackingQuality_, final int newTrackingQuality_) {

			}

			@Override
			public void onRecognitionFailed(final CloudTracker cloudTracker_, final int errorCode, final String errorMessage_) {
				cordova.getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
					//	EditText targetInformationTextField = (EditText) findViewById(R.id.continuous_tracking_info_field);
					//	targetInformationTextField.setText("Recognition failed - Error code: " + errorCode + " Message: " + errorMessage_);
					//	targetInformationTextField.setVisibility(View.VISIBLE);
					}
				});
			}

			@Override
			public void onRecognitionSuccessful(final CloudTracker cloudTracker_, boolean recognized_, final JSONObject jsonObject_) {
				if (recognized_) {
					cordova.getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							if (jsonObject_.toString().length() > 2) {
							//	EditText targetInformationTextField = (EditText) findViewById(R.id.continuous_tracking_info_field);
							//	targetInformationTextField.setText(jsonObject_.toString(), TextView.BufferType.NORMAL);
							//	targetInformationTextField.setVisibility(View.VISIBLE);
							}
						}
					});
				}
			}

			@Override
			public void onRecognitionInterruption(final CloudTracker cloudTracker_, final double suggestedInterval_) {
				cloudTracker_.startContinuousRecognition(suggestedInterval_);
			}
		});
	}
	
	
}
