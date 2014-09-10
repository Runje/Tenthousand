package de.runje.tenthousand.androidView;

import android.app.Activity;
import android.os.Bundle;

public class StatisticActivity extends Activity {

	public StatisticActivity() {
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(new StatisticsLayout(this));
	}
}
