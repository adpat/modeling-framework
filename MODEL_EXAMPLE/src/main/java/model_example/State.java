package model_example;

// For simulation, it's important to use a high-quality PRNG,
// with long cycles and good distribution.
import org.uncommons.maths.random.MersenneTwisterRNG;

import java.util.*;

import edu.berkeley.path.ModelElements.*;
import model_base.LogSink;

public class State extends model_base.TimeState {
  public long dummyState = 0;

  public long seed0;
  public long seed1;
  public MersenneTwisterRNG prng;

  public NetworkSource netsrc;
  public DensitySource densrc;
  public DensitySink densnk;
  public LogSink logsnk;
  
  public Network nw;
  public DensityProfile denIn;
  public DensityProfile denOut;
  private DensityProfile.Builder dpb;
  
  public State(int id, model_base.Context ctx, long seed0, long seed1) {
    super(id, ctx);
    this.seed0 = seed0;
    this.seed1 = seed1;
    this.prng = new MersenneTwisterRNG(longsToByteArray(seed0, seed1));

    netsrc = (NetworkSource)(context().getSource("network"));
    densrc = (DensitySource)(context().getSource("density"));
    densnk = (DensitySink)(context().getSink("density"));
    logsnk = (LogSink)(context().getSink("log"));
    
    nw = netsrc.getNetwork();
    
    logsnk.debug("Initialized model_example.State", null);
  }
  
  public void update() {
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
          iter.set(den + this.prng.nextDouble() - 0.5);
        }
      }
    }

    densnk.putDensityProfile(denOut);
    
    logsnk.debug("Updated model_example.State", null);
  }

  public static final byte[] longsToByteArray(long v0, long v1) {
    return new byte[] {
      (byte)(v0 >>> 56), (byte)(v0 >>> 48),
      (byte)(v0 >>> 40), (byte)(v0 >>> 32),
      (byte)(v0 >>> 24), (byte)(v0 >>> 16),
      (byte)(v0 >>> 8),  (byte)v0,
      (byte)(v1 >>> 56), (byte)(v1 >>> 48),
      (byte)(v1 >>> 40), (byte)(v1 >>> 32),
      (byte)(v1 >>> 24), (byte)(v1 >>> 16),
      (byte)(v1 >>> 8),  (byte)v1
    };
  }
}
