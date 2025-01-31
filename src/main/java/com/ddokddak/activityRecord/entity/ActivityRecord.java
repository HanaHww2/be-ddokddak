package com.ddokddak.activityRecord.entity;

import com.ddokddak.category.entity.Category;
import com.ddokddak.common.entity.BaseTimeEntity;
import com.ddokddak.member.entity.Member;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql="UPDATE activity_record SET delete_yn = 'Y', modified_at = NOW() WHERE id = ?")
@Where(clause = "delete_yn = 'N'")
@Entity
public class ActivityRecord extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CATEGORY_ID")
    private Category category;
//    @Column(nullable = false)
//    private String categoryId;

    @Column(length = 10, nullable = false)
    private String categoryName;

    @Column(length = 10, nullable = false)
    private String categoryColor;

    private LocalDateTime startedAt;

    private LocalDateTime finishedAt;

    private String timeZone;

    private String content;

    @Builder.Default
    @Column
    private Integer timeUnit = 30; // 기본 30 mins (10/30/60)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="MEMBER_ID")
    private Member member;

    @Builder.Default
    @Column(length = 1)
    private String deleteYn = "N";

    public void assignId(Long id) {
        this.id = id;
    }

}
