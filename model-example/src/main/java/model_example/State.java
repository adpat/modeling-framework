package model_example;

import java.util.*;

import edu.berkeley.path.model_elements.*;
import edu.berkeley.path.model_base.LogSink;

public class State extends edu.berkeley.path.model_base.State {
  public long dummyState = 0;

  public Network nw;
  public DensityProfile denIn;
  public DensityProfile denOut;
  private DensityProfile.Builder dpb;
  
  public DensitySource densrc;
  public NetworkSource netsrc;
  public DensitySink densnk;
  public LogSink logsnk;
  
  public State(edu.berkeley.path.model_base.Run run) {
    super(run);

    netsrc = (NetworkSource)(run.getSource("network"));
    densrc = (DensitySource)(run.getSource("density"));
    densnk = (DensitySink)(run.getSink("density"));
    logsnk = (LogSink)(run.getSink("log"));
    
    nw = netsrc.getNetwork();
    
    logsnk.debug("Initialized model_example.State", null);
  }
  
  public State update() {
    dummyState += 1;
    
    denIn = densrc.getDensityProfile();
    
    dpb = DensityProfile.newBuilder(denIn);
    dpb.setId("2out");
    denOut = dpb.build();
    
    Map<CharSequence,List<Double>> vpm = denOut.getVehiclesPerMeter();
    
    for (Link link: nw.getLinks()) {
      List<Double> cells = vpm.get(link.id);
      if (cells != null) {
        ListIterator<Double> iter = cells.listIterator();
        while (iter.hasNext()) {
          double den = iter.next();
          iter.set(den + ((Run)run()).prng.nextDouble() - 0.5);
        }
      }
    }

    densnk.putDensityProfile(denOut);
    
    logsnk.debug("Updated model_example.State", null);
    
    return this;
  }

  public boolean valid() {
    return true;
  }
}
