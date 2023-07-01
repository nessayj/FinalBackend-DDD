package com.DDD.dto;

import com.DDD.entity.Exhibitions;
import com.DDD.entity.Member;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Getter @Setter @ToString
@RequiredArgsConstructor
@Slf4j
public class DiaryDto {
    private Long id;
    private long memberId;
    private String regDate;
    private double rateStar;
    private String comment;
    private Exhibitions Exhibitions;


}