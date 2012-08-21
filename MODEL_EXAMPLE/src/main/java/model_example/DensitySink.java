package model_example;

import model_mock.*;
import edu.berkeley.path.ModelElements.*;

import java.util.*;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;

public class DensitySink extends MockDataSink {
  public void putDensityProfile(DensityProfile dp) {
    put(new MockDataSinkEntry(dp));
  }
}
