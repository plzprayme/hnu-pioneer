package com.hnu.pioneer.domain.jointable;

import com.hnu.pioneer.domain.Member;
import com.hnu.pioneer.domain.Study;
import lombok.*;

import javax.persistence.*;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
public class StudyMemberMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idx;

    @ManyToOne
    @JoinColumn(name = "member_idx")
    private Member participant;

    @ManyToOne
    @JoinColumn(name = "study_idx")
    private Study registeredStudy;

    @Builder
    public StudyMemberMapping(Member participant, Study registeredStudy) {
        this.participant = participant;
        this.registeredStudy = registeredStudy;
    }
}
