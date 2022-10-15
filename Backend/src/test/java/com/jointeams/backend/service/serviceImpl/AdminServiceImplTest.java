package com.jointeams.backend.service.serviceImpl;

import com.jointeams.backend.BackendApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

//TODO: implement testcases dealing with courses that are used by admin functionalities. - Yi Yang
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BackendApplication.class)
@TestPropertySource(locations="classpath:application-test.properties")
@Transactional
public class AdminServiceImplTest {
}