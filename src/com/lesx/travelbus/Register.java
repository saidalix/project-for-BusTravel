package com.lesx.travelbus;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Register extends Activity {
	//private ProgressDialog pleaseWaitDialog;
	TextView status;
	Button register;
	EditText nameEdt,usernameEdt,passwordEdt;
	public static final String server="http://10.0.2.2/android/add.php";
   String name="",username="",password="",log="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		nameEdt=(EditText) findViewById(R.id.name_edt);
		usernameEdt=(EditText) findViewById(R.id.username_edit);
		passwordEdt=(EditText) findViewById(R.id.password_edit);
		status=(TextView) findViewById(R.id.status);
		register=(Button) findViewById(R.id.submit_button);
		register.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				name=nameEdt.getText().toString().trim();
				username=usernameEdt.getText().toString().trim();
				password=passwordEdt.getText().toString().trim();
				if(!name.equals("") && !username.equals("") && !password.equals("")){
				         new RegisterTask().execute();//connect to the server
				}else{
					status.setText("All fields are required");
				}
			}
		});
					
	}


private class RegisterTask extends AsyncTask<String, String, String>{
		 @Override
	      protected void onPreExecute() {
			 status.setText("Signing up...");
			
		         }
		 @Override
	 protected String doInBackground(String... params) {
		
      	     String data;      	
      	try {
      		HttpClient httpclient = new DefaultHttpClient();
    	  	HttpPost httppost = new HttpPost(server);  	    
    	    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    	    nameValuePairs.add(new BasicNameValuePair("name",name));
    	    nameValuePairs.add(new BasicNameValuePair("username",username));
      	    nameValuePairs.add(new BasicNameValuePair("password",password));
      	  	httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));	 	     
	 	      HttpResponse response = httpclient.execute(httppost);            
	 	      String jsonResponse = EntityUtils.toString(response.getEntity());	 	      
	 	       data=jsonResponse;      
	          
      }catch (Exception e) {
    	  data="error";
      }
		 return data;
	}
		 

		 @Override
	  	  protected void onPostExecute(String data) {
		  if(!data.equals("error")){
			  try {
				JSONObject obj = new JSONObject(data);
				String value= obj.getString("status");
				if(value.equals("success")){
					status.setText("Registration successful");
					Intent in=new Intent(Register.this, Login.class);
					startActivity(in);
				}else if(value.equals("exists")){
					status.setText("Username already in use");
				}else{
					 status.setText("Connection failed 1 " +data);	
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				 status.setText("Connection failed 2");	
			}
			  
		  }	else{
			  status.setText("Connection failed 3");			  
		  }
		 
		 }
		 }
}

