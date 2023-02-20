package dosa.counselor.domain.commonCode;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class CommonCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(length = 50)
    private String groupCode1;

    @Column(length = 50)
    private String groupCode2;

    @Column(length = 50)
    private String groupCode3;

    @Column(length = 50)
    private String code;

    @Column(length = 50)
    private String name;

    @Column(columnDefinition = "VALUE")
    private Long value;

    @Column(columnDefinition = "TEXT")
    private String text;

    @Column
    private Integer sort;


}
