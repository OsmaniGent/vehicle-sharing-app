package com.example.vehiclefuel.services;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashSet;
import java.util.Set;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathExpression;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.io.StringReader;
import javax.xml.transform.sax.SAXSource;
import org.xml.sax.InputSource;

import com.example.vehiclefuel.models.MenuItemsResponse;
import com.example.vehiclefuel.models.MpgData;
import com.example.vehiclefuel.models.VehicleOption;
import com.example.vehiclefuel.models.VehicleOptionsRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.xml.xpath.XPathConstants;


import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.core.ParameterizedTypeReference;
import java.io.ByteArrayInputStream;

@Service
public class FuelEconomyService {

    private final RestTemplate restTemplate;
    private final String baseUrl = "https://www.fueleconomy.gov/ws/rest/vehicle/menu";
     private final ObjectMapper objectMapper;

    public FuelEconomyService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = new ObjectMapper();
    }

    public List<VehicleOption> getVehicleOptions(VehicleOptionsRequest request) {
        String url = baseUrl + "/options";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("year", request.getYear())
                .queryParam("make", request.getMake())
                .queryParam("model", request.getModel());

        VehicleOption[] options = restTemplate.getForObject(builder.toUriString(), VehicleOption[].class);
        return Arrays.asList(options);
    }

    public List<VehicleOption> getYears() {
        String url = baseUrl + "/year";
        String jsonResponse = restTemplate.getForObject(url, String.class);
        
        System.out.println("JSON Response: " + jsonResponse);

        MenuItemsResponse response = restTemplate.getForObject(url, MenuItemsResponse.class);
        return response != null ? response.getMenuItem() : Collections.emptyList();
    }
    

    public List<VehicleOption> getMakes(int year) {
        String url = baseUrl + "/make?year=" + year;
        
        MenuItemsResponse response = null;
        try {
            response = restTemplate.getForObject(url, MenuItemsResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }

        return response != null ? response.getMenuItem() : Collections.emptyList();
    }
    


    public List<VehicleOption> getModels(int year, String make) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl + "/model")
                .queryParam("year", year)
                .queryParam("make", make); // Spring automatically handles encoding
        
        String urlWithSpaces = builder.toUriString().replace("%20", " ");

        MenuItemsResponse response = restTemplate.getForObject(urlWithSpaces, MenuItemsResponse.class);
        return response != null ? response.getMenuItem() : Collections.emptyList();
    }

    public String getVehicleId(String year, String make, String model) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl + "/options")
                .queryParam("year", year)
                .queryParam("make", make)
                .queryParam("model", model);
        String url = builder.toUriString();

        String jsonResponse = restTemplate.getForObject(url, String.class);

        try {
            System.out.println("JSON Response: " + jsonResponse);
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode menuItemNode = rootNode.path("menuItem");
            if (menuItemNode.isArray() && menuItemNode.size() > 0) {
                JsonNode firstItem = menuItemNode.get(0);
                return firstItem.path("value").asText();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    public MpgData getVehicleMpg(String vehicleId) {
        String url = "https://www.fueleconomy.gov/ws/rest/ympg/shared/ympgVehicle/" + vehicleId;
        System.out.println(url);
        return restTemplate.getForObject(url, MpgData.class);
    }  
    
}
