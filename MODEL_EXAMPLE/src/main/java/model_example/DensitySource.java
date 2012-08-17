package model_example;

import model_mock.*;
import edu.berkeley.path.ModelElements.*;

import java.util.*;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;

public class DensitySource extends MockDataSource {
  /**
   * Construct a density source with some example density profiles ready
   * to be consumed by the example.
   */
  public DensitySource() {
    super();

    insert(makeDp(10.0));
    insert(makeDp(20.0));
  }
  
  public DensityProfile makeDp(double fudge) {
    DensityProfile dp;
    DensityProfile.Builder dpb;
    
    dpb = DensityProfile.newBuilder();
    dpb.setId("2");
    
    dp = dpb.build();
    
    Map<CharSequence,List<Double>> vpm =
      new HashMap<CharSequence,List<Double>>();
          
    dp.setVehiclesPerMeter(vpm);
    
    List<Double> row = new ArrayList();
    vpm.put("1", row); // link "1"
    row.add(1.0 + fudge); // first cell in link "1"
    row.add(2.0 + fudge);
    row.add(3.0 + fudge);
    
    return dp;
  }
  
  public DensityProfile getDensityProfile() {
    return (DensityProfile)get().x();
  }
}
