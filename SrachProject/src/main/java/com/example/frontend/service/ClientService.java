package com.example.frontend.service;

import com.example.frontend.pojo.BoardPojo;
import com.example.frontend.pojo.PostPojo;
import com.example.frontend.pojo.ThreadPojo;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@Service
public class ClientService {

    public static final String API_URL = "http://localhost:8080/api";
    private final RestTemplate restTemplate = new RestTemplate();

    public List<BoardPojo> getBoards(){
        String url = API_URL + "/boards";
        return Arrays.stream(Objects.requireNonNull(restTemplate.getForObject(url, BoardPojo[].class))).toList();
    }

    public BoardPojo getBoardByCodename(String codeName){
        String url = API_URL + "/boards/" +codeName;
        return restTemplate.getForObject(url, BoardPojo.class);
    }

    public List<ThreadPojo> getThreadsByBoardCodename(String codeName){
        String url = API_URL + "/boards/" +codeName+"/threads";
        return Arrays.stream(Objects.requireNonNull(restTemplate.getForObject(url, ThreadPojo[].class))).toList();
    }

    public List<PostPojo> getPostsByBoardCodenameAndTheadNumber(String codeName, Long threadNumber){
        String url = API_URL + "/boards/" + codeName + "/threads/" + threadNumber + "/posts";
        return Arrays.stream(Objects.requireNonNull(restTemplate.getForObject(url, PostPojo[].class))).toList();
    }

    public List<PostPojo> getLastPostsByBoardCodenameAndTheadNumber(String codeName, Long threadNumber){
        String url = API_URL + "/boards/" + codeName + "/threads/" + threadNumber + "/posts?last=true";
        return Arrays.stream(Objects.requireNonNull(restTemplate.getForObject(url, PostPojo[].class))).toList();
    }

    public ThreadPojo getThreadByBoardCodenameAndThreadNumber(String codeName, Long threadNumber){
        String url = API_URL + "/boards/" + codeName + "/threads/" + threadNumber;
        return restTemplate.getForObject(url, ThreadPojo.class);
    }

    public void createPostByBoardCodenameAndThreadnumber(PostPojo post,String codeName, Long threadNumber){
        String url = API_URL + "/boards/" + codeName + "/threads/" + threadNumber + "/posts";
        restTemplate.postForObject(url, post, PostPojo.class);
    }

    public void createThreadByBoardCodename(ThreadPojo thread, String codeName){
        String url = API_URL + "/boards/" + codeName + "/threads";
        restTemplate.postForObject(url, thread, ThreadPojo.class);
    }
}
