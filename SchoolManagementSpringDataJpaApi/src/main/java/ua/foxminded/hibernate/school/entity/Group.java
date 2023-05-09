package ua.foxminded.hibernate.school.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "groups", schema = "school")
public class Group {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "group_id")
  private Integer id;

  @Column(name = "group_name")
  private String groupName;

  public Group() {

  }

  public Group(String groupName) {
    this.groupName = groupName;
  }

}
