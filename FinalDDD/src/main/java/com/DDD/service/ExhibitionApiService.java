package com.DDD.service;

import com.DDD.dto.ExhibitionsDto;
import com.DDD.entity.Exhibitions;
import com.DDD.repository.ExhibitionsRepository;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Component
public class ExhibitionApiService {
    @Value("${api.serviceKey}")
    private String apiKey;

    @Autowired
    ExhibitionsRepository exhibitionsRepository;

    public String ExhibitionListApi() {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String body = "";

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusYears(1);


        int startDateValue = Integer.parseInt(startDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        int endDateValue = Integer.parseInt(endDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        UriComponents uri = UriComponentsBuilder
                .fromUriString("http://www.culture.go.kr")
                .path("openapi/rest/publicperformancedisplays/realm")
                .queryParam("serviceKey", apiKey)
                .queryParam("realmCode", "D000") // 분류코드 미술 : D000
                .queryParam("form", startDate) // 전시시작일
                .queryParam("to", endDate) // 전시종료일
                .queryParam("cPage", 1) // 현재페이지
                .queryParam("rows", 100) // 페이지 당 가져올 전시 수
                .queryParam("sortStdr", 1) // 정렬기준 1:등록일, 2:공연명, 3:지역
                .encode()
                .build();

        ResponseEntity<String> responseEntity = rest.exchange(uri.toUri(), HttpMethod.GET, requestEntity, String.class);
        String response = responseEntity.getBody();
        return response;
    }

    public List<ExhibitionsDto> listFromJsonObj(String result) {
        // xml 데이터를 json 데이터로 변환
        JSONObject xmlToJson = XML.toJSONObject(result);

        // JSONObject로 데이터 가져오기
        JSONObject jsonObj = xmlToJson.getJSONObject("dbs");

        // 배열형식이니 JSONArray로 가져오기
        JSONArray jsonArr = jsonObj.getJSONArray("db");

        // DTO에 List 형식으로 저장
        List<ExhibitionsDto> exhibitionsListDtoList = new ArrayList<>();
        for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject itemJson = (JSONObject) jsonArr.get(i);
                ExhibitionsDto musicalListDTO = new ExhibitionsDto(itemJson);
                exhibitionsListDtoList.add(musicalListDTO);
        }

        // DB저장
        for (int i = 0; i < jsonArr.length(); i++) {
            JSONObject item = (JSONObject) jsonArr.get(i);
            Exhibitions exhibitions = new Exhibitions(item);
            exhibitionsRepository.save(exhibitions);
        }
        return exhibitionsDtoListDtoList;
    }
}
