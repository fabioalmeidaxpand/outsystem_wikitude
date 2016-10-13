package com.wikitude.phonegap;

//import com.wikitude.WikitudeSDK;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;


public class WikitudePlugin extends CordovaPlugin {

	//private WikitudeSDK wikitudeSDK;
	private String action;
    
	private static final String	ACTION_OPEN = "open";

	@Override
	public boolean execute( final String action, final JSONArray args, final CallbackContext callContext ) {
		this.action = action;

		/* hide architect-view -> destroy and remove from activity */
		if ( WikitudePlugin.ACTION_OPEN.equals( action ) ) {
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
		
	}
	
	
}
