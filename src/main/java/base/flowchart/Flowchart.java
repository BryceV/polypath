package base.flowchart;

import base.entry.Entry;
import base.quarter.Quarter;
import base.user.User;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

@Entity
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, resolver = SimpleObjectIdResolver.class, property = "id", scope=Flowchart.class)
public class Flowchart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("flowcharts")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("userId")
    private User user;

    @JsonIgnoreProperties("flowchart")
    @OneToMany(targetEntity = Entry.class, mappedBy = "flowchart",
            cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
//    @JsonIdentityReference(alwaysAsId = true)
    private List<Entry> entries;

    @OneToOne
    private Quarter firstQuarter;

    @OneToOne
    private Quarter lastQuarter;

//    @JsonIgnore
    private boolean isOfficial = false;

    public Flowchart(){}

    public Flowchart(User user, String name, Quarter start, Quarter end) {
        this.user = user;
        this.name = name;
        this.firstQuarter = start;
        this.lastQuarter = end;
    }

    public Flowchart(Flowchart flowchart) {
        this.id = flowchart.id;
        this.name = flowchart.name;
        this.user = flowchart.user;
        this.entries = flowchart.entries;
        this.firstQuarter = flowchart.firstQuarter;
        this.lastQuarter = flowchart.lastQuarter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    public Quarter getFirstQuarter() {
      return firstQuarter;
    }

    public void setFirstQuarter(Quarter firstQuarter) {
      this.firstQuarter = firstQuarter;
    }

    public Quarter getLastQuarter() {
      return lastQuarter;
    }

    public void setLastQuarter(Quarter lastQuarter) {
      this.lastQuarter = lastQuarter;
    }

    public boolean isOfficial() {
      return isOfficial;
    }

    public void setOfficial(boolean official) {
      isOfficial = official;
    }
}
