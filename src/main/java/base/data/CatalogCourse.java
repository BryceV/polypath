package base.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * This class is used to deserialize course info from catalog json.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CatalogCourse {
  String name;
  String prefix;
  String suffix;
  String title;
  String units;
  String prerequisites;
  String description;
  String termsOffered;

  public String getName() {
    return name;
  }

  public String getPrefix() {
    return prefix;
  }

  public String getSuffix() {
    return suffix;
  }

  public String getTitle() {
    return title;
  }

  public String getUnits() {
    return units;
  }

  public String getPrerequisites() {
    return prerequisites;
  }

  public String getDescription() {
    return description;
  }

  public String getTermsOffered() {
    return termsOffered;
  }
}
