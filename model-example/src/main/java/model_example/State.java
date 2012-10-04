package model_example;

import java.util.*;

import edu.berkeley.path.model_elements.*;
import edu.berkeley.path.model_base.LogSink;

public class State extends edu.berkeley.path.model_base.State {
  public long dummyState = 0;

  public Network nw;
  public DensityProfile denIn;
  public DensityProfile denOut;
  
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
    
    denOut = new DensityProfile();
    denOut.setId(3);
    
    Map<CharSequence,List<Double>> vpmIn = denIn.getVehiclesPerMeter();
    HashMap<CharSequence,List<Double>> vpmOut = new HashMap<CharSequence,List<Double>>();
    
    for (Link link: (List<Link>)(List<?>)nw.getLinks()) {
      List<Double> cellsIn = vpmIn.get(link.id);
      
      if (cellsIn != null) {
        List<Double> cellsOut = new ArrayList<Double>(cellsIn);
        ListIterator<Double> iter = cellsOut.listIterator();
        while (iter.hasNext()) {
          double den = iter.next();
          iter.set(den + ((Run)run()).prng.nextDouble() - 0.5);
        }
        
        vpmOut.put(link.id, cellsOut);
      }
    }

    denOut.setVehiclesPerMeter(vpmOut);

    densnk.putDensityProfile(denOut);
    
    logsnk.debug("Updated model_example.State", null);
    
    return this;
  }

  public boolean valid() {
    return true;
  }
}
