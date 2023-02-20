package dosa.counselor.domain.oneline;

import dosa.counselor.domain.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity(name = "ONELINE_COUNSELING_LIST")
public class Oneline extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    private Long userIdx;

    @Column(nullable = false)
    private Short category;

    @Column(nullable = false, length = 100)
    private String body;

    @Column
    private Long profileIdx;

    @Column(length = 150)
    private String replyBody;

    @Column
    private LocalDateTime repliedDate;

    @Column
    private Long thumbsup;
//
//    @Column(length = 2)
//    private String authorLastName;
//
//    @Column(length = 12)
//    private String profileNickname;
//
//    @Column(length = 50)
//    private String profilePicture;
//



}
