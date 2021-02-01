package co.com.astransportes.providers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import co.com.astransportes.models.Driver;

public class DriverProvider {
    DatabaseReference mDatabase;

    public DriverProvider() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers");
    }

    public Task<Void> create(Driver driver){
        Map<String, Object> map = new HashMap<>();
        map.put("name", driver.getName());
        map.put("email", driver.getEmail());
        map.put("vehicle", driver.getVehicleBrand());
        map.put("plate", driver.getPlate());
        return mDatabase.child(driver.getId()).setValue(map);
    }
}
