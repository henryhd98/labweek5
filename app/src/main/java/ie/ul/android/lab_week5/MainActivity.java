/* Click on the plus sign to expand this comment box (for student details)
 * Student name:
 * ID:
 */

package ie.ul.android.lab_week5;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class MainActivity extends Activity {

	TextView dbView;
	TextView price;
	TextView percentage;
	String[] dbContent;
	String[] beerNames;
	Spinner beerSpinner;
	Button filterPricesButton;
	EditText priceField;
	Button filterAlcoholButton;
	EditText alcoholField;
	
	BelgianBeersDB beerDB;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		dbView = (TextView) findViewById(R.id.dbcontent);
		beerSpinner = (Spinner) findViewById(R.id.beerspinner);
		price = (TextView) findViewById(R.id.price);
		percentage = (TextView) findViewById(R.id.percentage);
		filterPricesButton = (Button) findViewById(R.id.findPriceButton);
		priceField = (EditText) findViewById(R.id.chosenPrice);
		alcoholField = (EditText) findViewById(R.id.chosenPercentage);
		
		beerDB = new BelgianBeersDB(getApplicationContext());
		showFullDatabase();
		
		beerNames = beerDB.getBeerNames();
		ArrayAdapter<String> beerAdapter = new ArrayAdapter<String>(getBaseContext(), R.layout.list_item, beerNames);
        beerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);    
        beerSpinner.setAdapter(beerAdapter);
        
        beerSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				if (beerNames.length>position) {
					price.setText(((Float) beerDB.getPrice(beerNames[position])).toString());
					percentage.setText(((Float) beerDB.getAlcohol(beerNames[position])).toString());
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
        	
        });
        
        filterPricesButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isPriceSet()){
						showAllCheaperThan(Float.parseFloat(priceField.getText().toString()));
				}
				
			}
        	
        });
    

	}

	
	private void print(String[] content) {
		dbView.setText("");
		for (int i=0; i<content.length; i++) {
			dbView.append(content[i]+"\n");	
		}
	}
	
	private void showFullDatabase() {
		print(beerDB.getAll());
	}
	
	private void showAllCheaperThan(float price) {
		print(beerDB.getAllCheaperThan(price));
	    
	}
	
	private void showAllStrongerThan(float percentage) {
		print(beerDB.getAllStrongerThan(percentage));
	}
		
	private boolean isPriceSet()
	{
		if (priceField.getText().toString().contentEquals("")) return false;
		return true;
	}
	
	private boolean isPercentageSet()
	{
		if (alcoholField.getText().toString().contentEquals("")) return false;
		return true;
	}
	
	
}
