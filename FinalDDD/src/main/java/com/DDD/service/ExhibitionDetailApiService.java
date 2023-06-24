package com.DDD.service;


import com.DDD.dto.ExhibitionDetailDTO;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;


@Service
@Component
@Slf4j
public class ExhibitionDetailApiService {

    @Value("${api.serviceKey}")
    private String apiKey;

    public String ExhibitionDetailApi(@RequestParam Integer seq) {

        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String body = " ";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        UriComponents uri = UriComponentsBuilder
                .fromUriString("http://www.culture.go.kr")
                .path("/openapi/rest/publicperformancedisplays/d/")
                .queryParam("serviceKey", apiKey)
                .queryParam("seq", seq)
                .encode()
                .build();
        System.out.println("API URI 출력!! : " + uri.toUriString());
        ResponseEntity<String> responseEntity = rest.exchange(uri.toUri(), HttpMethod.GET, requestEntity, String.class);
        String response = responseEntity.getBody();
        return response;
    }

    public List<ExhibitionDetailDTO> detailFromJsonObj(String result) {
        List<ExhibitionDetailDTO> list = new ArrayList<>();

        try {
            // xml 데이터를 json 데이터로 변환
            JSONObject xmlToJson = XML.toJSONObject(result);

            // response 객체 가져오기
            JSONObject responseObj = xmlToJson.getJSONObject("response");

            // msgBody 객체 가져오기
            JSONObject msgBodyObj = responseObj.getJSONObject("msgBody");

            // perforList 배열 가져오기
            JSONObject item = msgBodyObj.getJSONObject("perforInfo");

            // DTO저장
            ExhibitionDetailDTO exhibitionDetailDTO = new ExhibitionDetailDTO(item);
            list.add(exhibitionDetailDTO);

            System.out.println("상세정보 불러오기 성공!! :)😍😍😍");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }
}
