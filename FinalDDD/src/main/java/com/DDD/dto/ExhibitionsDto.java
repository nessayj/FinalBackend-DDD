package com.DDD.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.json.JSONObject;

@Getter @Setter
@RequiredArgsConstructor
public class ExhibitionsDto {
    private Long exhibitNo;
    private String exhibitName;
    private String startDate;
    private String endDate;
    private String location;
    private String imgUrl;
    private String region;

    public ExhibitionsDto (JSONObject item) {
        this.exhibitNo = item.getLong("seq");
        this.exhibitName = item.getString("title");
        this.startDate = item.getString("startDate");
        this.endDate = item.getString("endDate");
        this.location = item.getString("place");
        this.imgUrl = item.getString("thumbnail");
        this.region = item.getString("area");
    }
}
