package com.dtstack.agent;

import com.dtstack.agent.vo.UserVo;
import com.dtstack.plat.lang.web.R;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Test
    public void contextLoads() {
    }


    public static void main(String[] args) {

        TypeReference<List<UserVo>> tr=new TypeReference<List<UserVo>>() {};
        tr.getType();


    }

}
