package com.DDD.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;

@Getter @Setter
@RequiredArgsConstructor
public class ExhibitionsDTO {
    private Long exhibitNo;
    private String exhibitName;
    private int startDate;
    private int endDate;
    private String exhibitLocation;
    private String imgUrl;
    private String region;

    public ExhibitionsDTO(JSONObject item) {
        this.exhibitNo = item.getLong("seq");
        this.exhibitName = item.getString("title");
        this.startDate = item.getInt("startDate");
        this.endDate = item.getInt("endDate");
        this.exhibitLocation = item.getString("place");
        this.imgUrl = item.getString("thumbnail");
        this.region = item.getString("area");
    }
}
