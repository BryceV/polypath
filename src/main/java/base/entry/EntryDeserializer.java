package base.entry;

import base.course.Course;
import base.course.CourseService;
import base.flowchart.Flowchart;
import base.flowchart.FlowchartService;
import base.quarter.Quarter;
import base.quarter.QuarterService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class EntryDeserializer extends JsonDeserializer<Entry> {

    @Autowired
    FlowchartService flowchartService;
    @Autowired
    QuarterService quarterService;
    @Autowired
    CourseService courseService;

    @Override
    public Entry deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);

        final Long flowchartId = node.get("flowchart_id").asLong();
        final Long courseId = node.get("course_id").asLong();
        final Long quarterId = node.get("quarter_id").asLong();
        final String color = node.get("color").asText();

        Flowchart flowchart = flowchartService.getFlowchart(flowchartId);
        Course course = courseService.getCourse(courseId);
        Quarter quarter = quarterService.getQuarter(quarterId);

        return new Entry(course, flowchart, quarter, color);
    }
}
