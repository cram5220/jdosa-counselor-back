package dosa.counselor.domain.profile;

import dosa.counselor.domain.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "COUNSELOR_PROFILE")
public class Profile extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    private Long counselorIdx;

    @Column(nullable = false)
    private Short type;

    @Column(length = 12)
    private String nickname;

    @Column(length = 50)
    private String picture;

    @Column(length = 20)
    private String introduction;

    @Column(columnDefinition = "nvarchar(max)")
    private String description;



}
