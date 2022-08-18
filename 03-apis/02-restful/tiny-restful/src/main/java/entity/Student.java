package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@XmlRootElement
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "student")
public class Student implements Serializable {

    @Id
    @Column(nullable = false)
    int id;

    @Column(name = "full_name", nullable = false)
    String fullName;

    @Column(nullable = false)
    Date birthday;

    @Column(name = "class_name", nullable = false)
    String className;

}
