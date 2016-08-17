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

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BookSystem extends Activity {

	//private ProgressDialog pleaseWaitDialog;
		TextView status;
		Button bt_book;
		EditText et_fname,et_age,et_gender,et_nationality,et_passport,et_berth,et_phone,et_boarding,et_destination,et_date;
		public static final String server="http://10.0.2.2/android/booking.php";
	   String fname="",age="",gender="",nationality="",passport="",berth="",phone="",boarding="",destination="",date="", log="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.booksystem);
		et_fname = (EditText) findViewById(R.id.et_fname);
		et_age = (EditText) findViewById(R.id.et_age);
		et_gender = (EditText) findViewById(R.id.et_gender);
		et_nationality = (EditText) findViewById(R.id.et_nationality);
		et_passport = (EditText) findViewById(R.id.et_passport);
		et_berth = (EditText) findViewById(R.id.et_berth);
		et_phone = (EditText) findViewById(R.id.et_phone);
		et_boarding = (EditText) findViewById(R.id.et_boarding);
		et_destination = (EditText) findViewById(R.id.et_destination);
		et_date = (EditText) findViewById(R.id.et_date);
		bt_book=(Button) findViewById(R.id.bt_book);
		
		bt_book.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				fname=et_fname.getText().toString().trim();
				age=et_age.getText().toString().trim();
				gender=et_gender.getText().toString().trim();
				nationality=et_nationality.getText().toString().trim();
				passport=et_passport.getText().toString().trim();
				berth=et_berth.getText().toString().trim();
				phone=et_phone.getText().toString().trim();
				boarding=et_boarding.getText().toString().trim();
				destination=et_destination.getText().toString().trim();
				date=et_date.getText().toString().trim();
				
				if(!fname.equals("") && !age.equals("") && !gender.equals("") && !nationality.equals("") && !passport.equals("") && !berth.equals("") && !phone.equals("") && !boarding.equals("") && !destination.equals("") && !date.equals("") ){
			         new BookSystemTask().execute();//connect to the server
			}else{
				status.setText("All fields are required");
			}
			}
		});
		
	}
	
	private class BookSystemTask extends AsyncTask<String, String, String>{
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
   	    nameValuePairs.add(new BasicNameValuePair("fname",fname));
   	    nameValuePairs.add(new BasicNameValuePair("age",age));
     	nameValuePairs.add(new BasicNameValuePair("gender",gender));
     	nameValuePairs.add(new BasicNameValuePair("nationality",nationality));
     	nameValuePairs.add(new BasicNameValuePair("passport",passport));
     	nameValuePairs.add(new BasicNameValuePair("berth",berth));
     	nameValuePairs.add(new BasicNameValuePair("phone",phone));
     	nameValuePairs.add(new BasicNameValuePair("boarding",boarding));
     	nameValuePairs.add(new BasicNameValuePair("destination",destination));
     	nameValuePairs.add(new BasicNameValuePair("date",date));
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
					Intent in=new Intent(BookSystem.this, MainActivity.class);
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