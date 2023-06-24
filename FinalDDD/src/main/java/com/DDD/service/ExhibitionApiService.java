package com.DDD.service;


import com.DDD.entity.Exhibitions;
import com.DDD.repository.ExhibitionsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ExhibitionApiService {
    @Value("${api.serviceKey}")
    private String apiKey;

    private final ExhibitionsRepository exhibitionsRepository;

    public String exhibitionListApi() {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String body = "";

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusYears(1);


        int startDateValue = Integer.parseInt(startDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        int endDateValue = Integer.parseInt(endDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));

        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
        UriComponents uri = UriComponentsBuilder
                .fromUriString("http://www.culture.go.kr")
                .path("/openapi/rest/publicperformancedisplays/realm")
                .queryParam("serviceKey", apiKey)
                .queryParam("sortStdr", 1) // 정렬기준 1:등록일, 2:공연명, 3:지역
                .queryParam("realmCode", "D000") // 분류코드 미술 : D000
                .queryParam("cPage", 1) // 현재페이지
                .queryParam("rows", 100) // 페이지 당 가져올 전시 수
                .queryParam("form", startDateValue) // 전시시작일
                .queryParam("to", endDateValue) // 전시종료일
                .encode()
                .build();

        System.out.println("API URI 출력!! : " + uri.toUriString());
        ResponseEntity<String> responseEntity = rest.exchange(uri.toUri(), HttpMethod.GET, requestEntity, String.class);
        String response = responseEntity.getBody();
        System.out.println("API Response: " + response);
        return response;
    }

    public boolean listFromJsonObj(String result) {
        // xml 데이터를 json 데이터로 변환
        JSONObject xmlToJson = XML.toJSONObject(result);

        // response 객체 가져오기
        JSONObject responseObj = xmlToJson.getJSONObject("response");

        // msgBody 객체 가져오기
        JSONObject msgBodyObj = responseObj.getJSONObject("msgBody");

        // perforList 배열 가져오기
        JSONArray perforListArr = msgBodyObj.getJSONArray("perforList");

        // DB에 저장
        for (int i = 0; i < perforListArr.length(); i++) {
            JSONObject item = perforListArr.getJSONObject(i);
            Exhibitions exhibitions = new Exhibitions(item);
            exhibitionsRepository.save(exhibitions);
        }
        System.out.println("DB 저장 완료 :)!!");
        return true;
    }
}
