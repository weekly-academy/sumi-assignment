package com.mentoring.assignment.membership.domain.member.application;

import com.mentoring.assignment.membership.domain.member.infrastructure.Member;
import com.mentoring.assignment.membership.domain.member.infrastructure.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
//@Transactional(readOnly = true) -> 이게 있으면 @Transactional이 안 먹힘 -> 왜지?
@Slf4j
public class MemberInitializer {


    private final MemberRepository memberRepository;

    @EventListener(ContextRefreshedEvent.class)
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("Member onApplicationEvent is called");
        event.getApplicationContext().getBean(MemberInitializer.class).setup();
    }

    @Transactional
    public void setup() {
        log.info("Member setup is called");
        Member member1 = Member.builder()
                .build();

        Member member2 = Member.builder()
                .build();

        memberRepository.save(member1);
        memberRepository.save(member2);

    }
}

