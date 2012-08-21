package model_example;

import model_mock.MockLogSink;

public class Context extends model_base.Context {
  /**
   * For this example, the Context sets up dummy sources and sinks.
   */
  public Context(int id) {
    super(id);
    
    sources().put("network", new NetworkSource());
    sources().put("density", new DensitySource());
    sinks().put("density", new DensitySink());
    sinks().put("log", new MockLogSink());
  }
}
