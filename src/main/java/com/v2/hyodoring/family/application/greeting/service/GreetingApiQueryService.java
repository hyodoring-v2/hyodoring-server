package com.v2.hyodoring.family.application.greeting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GreetingApiQueryService {

    //TODO: 나에게 도착한 가족 안부 목록 조회하기 구현

    //TOOD: 가족이 주고받은 안부 목록 조회하기 구현
}
