package base.flowchart;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class FlowchartCompact {
   Long id;
   String name;

  public FlowchartCompact(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Long getId()
  {
    return this.id;
  }

}
