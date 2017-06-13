package base.flowchart;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FlowchartRepository extends CrudRepository<Flowchart, Long> {

  List<Flowchart> findByIsOfficial(boolean b);
}
