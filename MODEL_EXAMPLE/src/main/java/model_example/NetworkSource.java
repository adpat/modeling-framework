package model_example;

import model_mock.*;
import edu.berkeley.path.ModelElements.*;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;

public class NetworkSource extends MockDataSource {
  /**
   * Construct a network source with an example network ready to
   * be consumed by the example.
   */
  public NetworkSource() {
    super();
    
    Network nw;
    Network.Builder nwb = Network.newBuilder();
    
    nwb.setName("example network"); // there's no default for this
    nw = nwb.build(); // applies the defaults
    
    Schema schema = nw.getSchema();

    nw.setId("42");
    nw.setNodes(
      new GenericData.Array<Node>(2,
        schema.getField("nodes").schema())
    );
    nw.setLinks(
      new GenericData.Array<Link>(1,
        schema.getField("links").schema())
    );
    
    Node nd;
    Link ln;

    nd = new Node();
    nd.setId("1");
    nd.setName("one");
    nd.setType("hwy");
    nw.nodes.add(nd);

    nd = new Node();
    nd.setId("2");
    nd.setName("two");
    nd.setType("hwy");
    nw.nodes.add(nd);

    ln = new Link();
    ln.setId("3");
    ln.setName("two");
    ln.setType("hwy");
    ln.setLaneCount(4);
    ln.setLength(1000.0);
    
    ln.begin = new NodeRef();
    ln.begin.setNodeId("1");
    ln.end = new NodeRef();
    ln.end.setNodeId("2");
    
    nw.links.add(ln);

    insert(nw);
  }
  
  public Network getNetwork() {
    return (Network)get().x();
  }
}
